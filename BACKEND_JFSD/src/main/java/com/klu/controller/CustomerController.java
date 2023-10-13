package com.klu.controller;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.klu.Entity.Customer;
import com.klu.model.CustomerManager;
@RestController
@RequestMapping("/user")
public class CustomerController {
	@Autowired
	CustomerManager cm;
	@PostMapping("/register")
	public String register(@RequestBody Customer S)
	{
		return cm.register(S);
	}
	@PostMapping("/otp")
	public String otpverify(@RequestBody Integer s)
	{
		return cm.otpVerification(s);
	}
	@PostMapping("/login")
	public String login(@RequestBody Map<String, String> requestBody)
	{
		return cm.login(requestBody.get("email"),requestBody.get("password"));
	}
	@GetMapping("/home")
	public String homePage()
	{
		return cm.testSession();
	}
}
