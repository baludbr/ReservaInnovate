package com.klu.controller;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.klu.Entity.Customer;
import com.klu.Entity.Hotel;
import com.klu.Entity.HotelInfo;
import com.klu.model.CustomerManager;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/user")
public class CustomerController {
	@Autowired
	CustomerManager cm;
	
	//Registration and OTP MicroServices
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody Customer S)
	{
		try
		{
		String res=cm.register(S);
		if(res=="Email Exists")
		{
			return new ResponseEntity<>(res,HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<>(res,HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(e.getMessage(),HttpStatus.GATEWAY_TIMEOUT);
		}
	}
	@PostMapping("/otp")
	public ResponseEntity<String> otpverify(@RequestBody Integer s)
	{
		String k=cm.otpVerification_register(s);
		if(k!="OTP Verification Failed")
		{
			return new ResponseEntity<>(k,HttpStatus.OK);
		}
		return new ResponseEntity<>(k,HttpStatus.NOT_ACCEPTABLE);
	}
	
	
	
	
	//Login and Logout MicroServices
	@PostMapping("/login")
	public  ResponseEntity<String> login(@RequestBody Map<String, String> requestBody)
	{
		String res=cm.login(requestBody.get("email"),requestBody.get("password"));
		if(res!="Invalid Credentials")
		{
			return new ResponseEntity<>(res,HttpStatus.OK);
		}
		return new ResponseEntity<>(res,HttpStatus.NOT_ACCEPTABLE);
	}
	@GetMapping("/logout")
	public ResponseEntity<String> logout() 
	{
		return new ResponseEntity<>(cm.logout(),HttpStatus.NOT_FOUND);
	}
	
	
	//ForgotPassword MicroServices
	@PostMapping("/forgotpassword_email")
	public ResponseEntity<String> forgot_password(@RequestBody Map<String,String> requestBody)
	{
		String res=cm.forgot_password_Main(requestBody.get("email"));
		if(res=="Mail Sent Successfully")
		{
			return new ResponseEntity<>(res,HttpStatus.OK);
		}
		return new ResponseEntity<>(res,HttpStatus.NOT_FOUND);
	}
	@PostMapping("/forgotpassword_otp")
	public ResponseEntity<String> forgotpassword_otp(@RequestBody Map<String,String> requestBody)
	{
		String res=cm.forgot_password_otp(Integer.parseInt(requestBody.get("otp")));
		if(res=="Verification SucessFully")
		{
			return new ResponseEntity<>(res,HttpStatus.OK);
		}
		return new ResponseEntity<>(res,HttpStatus.FORBIDDEN);
	}
	@PostMapping("/forgotpassword_update")
	public ResponseEntity<String> forgotpassword_email(@RequestBody Map<String,String> requestBody)
	{
		String res=cm.updatePassword(requestBody.get("password"),requestBody.get("conpassword"));
		if(res=="Password Updated Successfully!!Login with your new Password!!")
		{
			return new ResponseEntity<>(res,HttpStatus.OK);
		}
		return new ResponseEntity<>(res,HttpStatus.FORBIDDEN);
	}
	
	
	
	
	
	
	
	

	
	//HotelDetails By Location Microservice
	@PostMapping("/location")
	public ResponseEntity<List<HotelInfo>> getLocation(@RequestBody String s) throws IOException, InterruptedException
	{
		return new ResponseEntity<>(cm.getHotelInfo(s),HttpStatus.OK);
	}
	
	
	
	//Components Microservices
	@GetMapping("/home")
	public ResponseEntity<String> homePage()
	{
		String res=cm.testSession();
		if(res=="Session TimeOut")
		{
			return new ResponseEntity<>(res,HttpStatus.GATEWAY_TIMEOUT);
		}
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	//HotelRoomTypeInsertion Microservices
	@PostMapping("/addRoomType")
	public  ResponseEntity<String> addRoomType(@RequestBody Hotel h)
	{
		String res=cm.addRoomType(h);
		if(res=="Session TimeOut")
		{
			return new ResponseEntity<>(res,HttpStatus.GATEWAY_TIMEOUT);
		}
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
}
