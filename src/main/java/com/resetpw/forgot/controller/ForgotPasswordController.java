package com.resetpw.forgot.controller;


import com.resetpw.forgot.dto.EmailDTO;
import com.resetpw.forgot.entity.Customer;
import com.resetpw.forgot.exception.CustomerNotFoundException;
import com.resetpw.forgot.repository.CustomerRepository;
import com.resetpw.forgot.service.CustomerService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Controller
public class  ForgotPasswordController {
  private final long EXPIRED_TIME = 120000;
  private static String emaill;
  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private JavaMailSender mailSender;
  @Autowired
  CustomerService service;

    @RequestMapping(value = {"/","/login"}, method = RequestMethod.GET)
    public String login(Model model){
      String message = "Login";
      model.addAttribute("age","age");
      model.addAttribute("message", message);
      return "login";
    }


    @GetMapping("/forgot-password")
    public String showForgotPasswordForm(){
        return "forgot-password";
    }

    @GetMapping("/otp-validation")
    public String showoff(HttpServletRequest request, Model model){
      model.addAttribute("vc", emaill);
      return "otp-validation";
    }

    @PostMapping("otp-validation")
    public String otpValidationExecute(HttpServletRequest request, Model model) throws InterruptedException {
      String email = emaill;
      model.addAttribute("vc", email);
      String name = request.getParameter("aa");
      model.addAttribute("name", name);


      SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
      Session session = sessionFactory.openSession();
      String hql = "FROM Customer c WHERE c.email = :email";
      Customer customer = session.createQuery(hql, Customer.class).setParameter("email", email).uniqueResult();
      String oo = customer.getOtp();


      Date current = new Date();
      Date date = customer.getOtplife();

      if (oo.equals(name) && (current.getTime() - date.getTime() > EXPIRED_TIME)){
        model.addAttribute("expired", "Mã OTP đã hết hạn");
        
        return "otp-validation";
      }

      else if(oo.equals(name) && (current.getTime() - date.getTime() <= EXPIRED_TIME)) {
        return "redirect:reset-password/?otp=" + oo;
      }
      else {

        return "otp-validation";
      }



    }

    @PostMapping(value = "/forgot-password")
    public String processForgotPassword(HttpServletRequest request, Model model){
      String email = request.getParameter("email");
      String otp = getRandomNumberString();
      model.addAttribute("email", email);
      SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
      Session session = sessionFactory.openSession();
      String hql = "FROM Customer c WHERE c.email = :email";
      Customer customer = session.createQuery(hql, Customer.class).setParameter("email", email).uniqueResult();
      if (customer == null) {
        model.addAttribute("err","email doesn't exist");
        return "forgot-password";
      }
      emaill = email;
      try {
        service.updateResetPwOtp(otp, email);
        sendEmail(email, otp);
        model.addAttribute("message", "We have sent a OTP to your email. Please check");

      } catch (CustomerNotFoundException ex){
        model.addAttribute("error", ex.getMessage());
      } catch (MessagingException | UnsupportedEncodingException e){
        model.addAttribute("error", "Cannot send email");
      }

        return "redirect:otp-validation";
    }

    @GetMapping("/message")
    public String ok(){
      return "message";
    }
    @GetMapping("/reset-password")
    public String showResetPasswordForm(@Param(value = "otp") String otp, Model model){
      Customer customer = service.getByResetPwOtp(otp);
      model.addAttribute("otp" , otp);

      if (customer == null){
        model.addAttribute("message", "Invalid OTP");
        return "message";
      }

      return "reset-password";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(HttpServletRequest request, Model model) {
      String otp = request.getParameter("otp");
      String password = request.getParameter("password");

      Customer customer = service.getByResetPwOtp(otp);
      model.addAttribute("title", "Reset your password");


        service.updatePw(customer, password);

        model.addAttribute("message", "You have successfully changed your password.");


      return "message";
    }

  public static String getRandomNumberString() {
    // It will generate 6 digit random Number.
    // from 0 to 999999
    Random rnd = new Random();
    int number = rnd.nextInt(999999);

    // this will convert any number sequence into 6 character.
    return String.format("%06d", number);
  }



  public void sendEmail(String recipientEmail, String otp)
          throws MessagingException, UnsupportedEncodingException {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message);

    helper.setFrom("thanhtrung2500@gmail.com", "ITSol Support");
    helper.setTo(recipientEmail);

    String subject = "Here's the OTP to reset your password";

    String content = "<p>Xin chào</p>"
            + "<p>Đây là mã OTP của bạn</p>"
            + "<p>Hãy trở về trang chủ và điền mã này vào:</p>"
            + "<p>" + otp + "</p>"
            + "<br>"
            + "<p>Đây là tin nhắn tự động, "
            + "abcxyz.</p>";

    helper.setSubject(subject);

    helper.setText(content, true);

    mailSender.send(message);
  }
}
