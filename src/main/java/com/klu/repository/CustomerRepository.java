package com.klu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.klu.Entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long>{
	@Query("SELECT c FROM Customer c WHERE c.Email = :email")
	public Customer customerExsistence(@Param("email") String email);
	@Query("SELECT c FROM Customer c WHERE c.Email = :email and c.password = :password")
	public Customer loginAuthentication(@Param("email") String email,@Param("password") String password);
}
