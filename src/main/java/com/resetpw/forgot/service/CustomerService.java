package com.resetpw.forgot.service;

import com.resetpw.forgot.entity.Customer;
import com.resetpw.forgot.exception.CustomerNotFoundException;
import com.resetpw.forgot.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public void updateResetPwOtp(String otp, String email) throws CustomerNotFoundException {
        Customer customer = customerRepository.findByEmail(email);
        if (customer != null) {
            customer.setOtp(otp);

            Date date = new Date();
            customer.setOtplife(date);

            customerRepository.save(customer);
        } else {
            throw new CustomerNotFoundException("could not find this email: " + email);
        }
    }

    public Customer getByResetPwOtp(String otp) {
        return customerRepository.findByResetPwOtp(otp);
    }

    public String getOTP(String email){
        return customerRepository.findByOTP(email);
    }

    public void updatePw(Customer customer, String newpassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePass = encoder.encode(newpassword);
        customer.setPass(encodePass);
        customer.setOtp(null);
        customer.setOtplife(null);
        customerRepository.save(customer);
    }

    public void updateOtpLife(Customer customer, Date date){

        customer.setOtplife(date);
    }


}
