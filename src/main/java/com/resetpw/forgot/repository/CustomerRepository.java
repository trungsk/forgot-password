package com.resetpw.forgot.repository;

import com.resetpw.forgot.entity.Customer;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;



public interface CustomerRepository extends JpaRepository<Customer, Long>, CrudRepository<Customer, Long> {
    @Query("select c from Customer c where c.email = ?1")
    Customer findByEmail(String email);

    @Query("select c from Customer c where c.otp = ?1")
    public Customer findByResetPwOtp(String otp);

    @Query("select c.otp from Customer c where c.email = ?1")
    String findByOTP(String email);

    @Query("from Customer")
    Customer allAttributes();
}
