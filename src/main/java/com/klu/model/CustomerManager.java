package com.klu.model;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.klu.Entity.Booking;
import com.klu.Entity.Customer;
import com.klu.Entity.Hotel;
import com.klu.Entity.HotelInfo;
import com.klu.Entity.Room;
import com.klu.Entity.RoomApproval;
import com.klu.repository.BookingRepository;
import com.klu.repository.CustomerRepository;
import com.klu.repository.HotelTypeRepository;
import com.klu.repository.RoomRepository;

@Service
public class CustomerManager {
	private static jakarta.servlet.http.HttpSession session;
	private static int otp=0;
	private static String session_email;
	private Customer st;
	@Autowired
	CustomerRepository cr;
	@Autowired
	JavaMailSender jms;
	@Autowired
	HotelTypeRepository htp;
	@Autowired
	RoomRepository rr;
//-----------------------------------------------------Registration and Login---------------------------------------------------------------------//
	//Register and OTP Validation
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
		return sendMail(s.getEmail(),otp,"Hello!!Welcome to ReservalInnovate","You requested for otp.Your OTP is");
	}
	public String otpVerification_register(int x)
	{
		if(x==otp)
		{
			cr.save(st);
			return "Registration Done SucessFully";
		}
		return "OTP Verification Failed";
	}
    public String sendMail(String email,int otp,String subject,String text) {
    	try {
    	SimpleMailMessage smm=new SimpleMailMessage();
    	smm.setFrom("dwarampudibalajireddy14@gmail.com");
    	smm.setTo(email);
    	smm.setSubject(subject);
    	text=text+otp;
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
	
	
    
    
	
	//Login & Logout Details
	public String login(String email,String password)
	{
		Customer c=cr.loginAuthentication(email, password);
		if(c!=null)
		{
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			session = attr.getRequest().getSession();
			session.setAttribute("id",c.getCustomerID());
			session.setAttribute("role",c.getRole());
			return ""+session.getAttribute("role");
		}
			return "Invalid Credentials";
	}
	public String logout()
    {
    	session.invalidate();
		return "Logout";
    }
	
	
	//ForgotPassword
	public String forgot_password_Main(String email)
	{
		otp=randomNo();
		session_email=email;
		if(cr.customerExsistence(email)!=null)
		return sendMail(email, otp,"Request for Forgotten Password","You are requested for password reset.so you have to write this otp:");
		return "Mail Not Found in our Database!!Kindly Register";
	}
	public String forgot_password_otp(int x)
	{
		if(x==otp)
		{
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			session = attr.getRequest().getSession();
			session.setAttribute("email",session_email);
			return "Verification SucessFully";
		}
		return "OTP Verification Failed";
	}
	public String updatePassword(String pass,String conpass)
	{
		System.out.println(pass+" "+conpass+" "+session.getAttribute("email"));
		if(pass.equals(conpass))
		{
			Customer c=cr.customerExsistence((String)session.getAttribute("email"));
			c.setPassword(pass);
			cr.save(c);
			session.invalidate();
			return "Password Updated Successfully!!Login with your new Password!!";
		}
		return "Both passwords are not match";
	}
	
	
	
	
	
	//Fetch Hotel Details By Location
	public List<HotelInfo> getHotelInfo(String location) throws IOException, InterruptedException {
		 String apiKey = "d9cb4e93bbmshc175ff5f57f6d6ap1d65e5jsn22a6f3b7f42f";
		    List<HotelInfo> hotelInfoList = new ArrayList<>();

		    try {
		        String encodedLocation = URLEncoder.encode(location, StandardCharsets.UTF_8.toString());
		        HttpRequest request = HttpRequest.newBuilder()
		            .uri(URI.create("https://hotels4.p.rapidapi.com/locations/v3/search?q=" + encodedLocation + "&locale=en_US&langid=1033&siteid=300000001"))
		            .header("X-RapidAPI-Key", apiKey)
		            .header("X-RapidAPI-Host", "hotels4.p.rapidapi.com")
		            .method("GET", HttpRequest.BodyPublishers.noBody())
		            .build();

		        HttpClient client = HttpClient.newHttpClient();

		        try {
		            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		            JSONObject responseJson = new JSONObject(response.body());
		            if (responseJson.has("sr") && responseJson.get("sr") instanceof JSONArray) {
		                JSONArray hotelResults = responseJson.getJSONArray("sr");
		                for (int i = 0; i < hotelResults.length(); i++) {
		                    JSONObject hotelData = hotelResults.getJSONObject(i);
		                    if (hotelData.has("regionNames") && hotelData.getJSONObject("regionNames").has("fullName")) {
		                        String hotelName = hotelData.getJSONObject("regionNames").getString("primaryDisplayName");
		                        String latitude = hotelData.getJSONObject("coordinates").getString("lat");
		                        String longitude = hotelData.getJSONObject("coordinates").getString("long");
		                        String description = "";
		                        if (hotelData.has("description")) {
		                            description = hotelData.getString("description");
		                        }
		                        if (hotelData.has("hotelAddress")) {
		                            JSONObject hotelAddress = hotelData.getJSONObject("hotelAddress");
		                            if (hotelAddress.has("street")) {
		                                description = description +""+hotelAddress.getString("street")+",";
		                            }
		                            if (hotelAddress.has("city")) {
		                            	description = description +""+hotelAddress.getString("city")+",";
		                            }
		                            if (hotelAddress.has("province")) {
		                            	description = description +""+ hotelAddress.getString("province");
		                            }
		                        }

		                        HotelInfo hotelInfo = new HotelInfo(hotelName, latitude, longitude, description);
		                        hotelInfoList.add(hotelInfo);
		                    }
		                }
		            }
		        } finally {
		           
		        }
		    } catch (IOException | InterruptedException e) {
		        e.printStackTrace();
		        throw e;
		    }

		    return hotelInfoList;
    }
    public String JsontoString(Object obj)
    {
    	GsonBuilder gs=new GsonBuilder();
		Gson g=gs.create();
		return g.toJson(obj);
    }
    
  //---------------------------------------------------------MANAGER VIEW-----------------------------------------------------------------------//  
  //RoomTypeLogic
    public String addRoomType(Hotel h)
    {
    	try
		{
		String ss=""+session.getAttribute("id");
		Customer cust=cr.findById(Long.parseLong(ss)).get();
		System.out.println(cust.getRole());
		if(cust.getRole().equals("manager"))
		{
		try
		{
			Hotel ht=htp.getRoomType(h.getRoomType());
			if(ht==null)
			{
			htp.save(h);
			}
			else
			{
				return "Already Exists";
			}
		}
		catch(Exception e)
		{
			return "Already exists";
		}
		
		return "Account Saved by "+cust.getName();
	    }
		else
		{
			throw new Exception("Session Timeout");
		}
		}
		catch(Exception e)
		{
			return "Session Timeout";
		}
    }
    //GetRoomType
    public List<Hotel> getoomType()
    {
    	return htp.findAll();
    }
    public String insertRoom(Room r)
    {
    	try
    	{
    		String ss=""+session.getAttribute("id");
    		Customer cust=cr.findById(Long.parseLong(ss)).get();
    		System.out.println(cust.getRole());
    		if(cust.getRole().equals("manager"))
    		{
	    		rr.save(r);
	    		Hotel h=htp.getRoomType(r.getRoomType());
	    		h.setTotalCapacity(h.getTotalCapacity()+1);
	    		h.setAvaliability(h.getAvaliability()+1);
	    		htp.save(h);
	    		return "Room Inserted";
    		}
    		else
    		{
    			System.out.println("hh");
    			throw new Exception("Session Timeout");
    		}
    	}
    	catch(Exception e)
    	{
    		System.out.println("ht");
    		return "Session Timeout";
    	}
    }
    public String approval(RoomApproval ra)
    {
    	try
    	{
    		String ss=""+session.getAttribute("id");
    		Customer cust=cr.findById(Long.parseLong(ss)).get();
    		System.out.println(cust.getRole());
    		if(cust.getRole().equals("manager"))
    		{
    			if(ra.getStatus().equals("accepted"))
    			{
    		    Long bookingID=ra.getRoomID();
    		    Booking bk=br.getBookingDetailsByBookingId(bookingID);
    		    bk.setStatus(ra.getStatus());
    		    bk.setRoomNo(ra.getRoomNumber());
    		    br.save(bk);
    		    String roomType=bk.getRoomType();
    		    Hotel h=htp.getRoomType(roomType);
    		    h.setAvaliability(h.getAvaliability()-1);
    		    htp.save(h);
    		    Room rd=rr.getBookingDetails(ra.getRoomNumber());
    		    rd.setAvailabilty("Booked");
    		    rr.save(rd);
    		    return "Approved Sucessfully";
    			}
    			return  "Rejected/Cancelled your booking.Contact Manager";
    		}
    		else
    		{
    			throw new Exception("Session Timeout");
    		}
    		}
    		catch(Exception e)
    		{
    		return e.getMessage();
    		}
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //Components
    public String testSession()
	{
		try
		{
		return ""+session.getAttribute("id");
		}
		catch(Exception e)
		{
			return "Session Timeout";
		}
	}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
//-------------------------------------------------CUSTOMER--------------------------------------------------------------------------//
    //BookingDetails
    @Autowired
    BookingRepository br;
    Booking b;
    public String BookingDetails(Booking b)
    {
    	try
		{
		String ss=""+session.getAttribute("id");
		Customer cust=cr.findById(Long.parseLong(ss)).get();
		if(cust.getRole().equals("customer"))
		{
		b.setCustomerID(Long.parseLong(ss));
		session.setAttribute("price",b.getTotalCost());
		this.b=b;
		return  "Proceed to Payment Portal";
		}
		else
		{
			throw new Exception("Session Timeout");
		}
        }
    	catch(Exception e)
    	{
    		return e.getMessage();
    	}
    }
    //BookingDetails(Payment)
    public String PaymentStatus(String Pstatus,String price,String refId)
    {
    	try
    	{
    	int rr=Integer.parseInt(""+session.getAttribute("price"));
    	int or=Integer.parseInt(""+price);
    	System.out.println(rr+" "+or);
    	System.out.println(Pstatus);
    	if(Pstatus.equals("Yes"))
    	{
    	
    	if(or>=rr){
    		b.setPaidStatus("Fully Paid");
    		b.setStatus("Review pending");
    		b.setPaidAmount(""+or);
    		List<String> dd;
    		if(b.getReferalIds()==null)
    		{
    			dd=new ArrayList<>();
    			dd.add(refId);
    		}
    		else
    		{
    		  dd=b.getReferalIds();
    		  dd.add(refId);
    		}
    		b.setReferalIds(dd);
    		br.save(b);
    		return "Room Booking Successfully.Please contact manager for Room Details";
    	}
    	else if(or>=0.1*rr)
    	{
    		b.setPaidStatus("Partially Paid");
    		b.setStatus("Review pending");
    		b.setPaidAmount(""+or);
    		br.save(b);
    		return "Room Booking Successfully.Please contact manager for Room Details";
    	}
    	else
    	{
    		return "NotPaid";
    	}
    	}
    	else
    	{
    		return "Booking Failed";
    	}
    	}
    	catch(Exception e)
    	{
    		return e.getMessage();
    	}
    }
    //BookingHistory
    public List<Booking> getBookingHistory()
    {
    	try
    	{
    		String ss=""+session.getAttribute("id");
    		Customer cust=cr.findById(Long.parseLong(ss)).get();
    		if(cust.getRole().equals("customer"))
    		{
    			List<Booking> ls=br.getBookingDetailsById(ss);
    			return ls;
    		}
    		else
    		{
    			throw new Exception("Session Timeout");
    		}
  
    	}
    	catch(Exception e)
    	{
    		return null;
    	}
    }

}
