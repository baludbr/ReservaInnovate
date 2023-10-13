package com.klu.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;

import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.klu.Entity.Customer;
import com.klu.repository.CustomerRepository;

@Service
public class CustomerManager {
	static jakarta.servlet.http.HttpSession session;
	static int otp=0;
	private Customer st;
	@Autowired
	CustomerRepository cr;
	@Autowired
	JavaMailSender jms;
	public String register(Customer s)
	{
		System.out.println(cr.customerExsistence(s.getEmail()));
		Customer cu=cr.customerExsistence(s.getEmail());
		if(cu!=null)
		{
			return "Email Exists";
		}
		otp=randomNo();
		st=s;
		System.out.println(otp);
		return sendMail(s.getEmail(),otp);
	}
	public String login(String email,String password)
	{
		Customer c=cr.loginAuthentication(email, password);
		if(c!=null)
		{
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			session = attr.getRequest().getSession();
			session.setAttribute("id",c.getCustomerID());
			return ""+session.getAttribute("id");
		}
			return "Invalid Credentials";
	}
	public String otpVerification(int x)
	{
		if(x==otp)
		{
			cr.save(st);
			return "Registration Done SucessFully";
		}
		return "OTP Verification Failed";
	}
    public String sendMail(String email,int otp) {
    	try {
    	SimpleMailMessage smm=new SimpleMailMessage();
    	smm.setFrom("dwarampudibalajireddy14@gmail.com");
    	smm.setTo(email);
    	smm.setSubject("Hello!!Welcome to ReservalInnovate");
    	
    	String text="You requested for otp.Your OTP is"+otp;
    	smm.setText(text);
    	jms.send(smm);
    	return "Mail Sent Successfully";
    	}
    	catch(Exception e) {
    		return e.getMessage();
    	}
    }
    public int randomNo()
    {
    	return new Random().nextInt(90000) + 10000;
    }
    public String JsontoString(Object obj)
    {
    	GsonBuilder gs=new GsonBuilder();
		Gson g=gs.create();
		return g.toJson(obj);
    }
    public String testSession()
	{
		if(session==null)
		{
			return "Session TimeOut";
		}
		return ""+session.getAttribute("id");
	}
}
