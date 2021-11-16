package com.resetpw.forgot.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Customer")
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "email", nullable = false, length = 30, unique = true)
    private String email;

    @Column(name = "pass", nullable = false, length = 200)
    private String pass;

    @Column(name = "otp", nullable = true)
    private String otp;

    @Column(name = "otplife", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date otplife;


    public Customer(int id, String email, String pass) {
        this.id = id;
        this.email = email;
        this.pass = pass;
        this.otp = null;
        this.otplife = null;
    }

    public Customer() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Date getOtplife() {
        return otplife;
    }

    public void setOtplife(Date otplife) {
        this.otplife = otplife;
    }
}
