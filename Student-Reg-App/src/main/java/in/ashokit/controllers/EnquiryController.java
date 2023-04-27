package in.ashokit.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EnquiryController {
	
	@Autowired
	private HttpSession session;
	
	
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}
	
	@GetMapping("/dashboard")
	public String dashboardPage() {
		//TODO: logic to fetch data for dashboard
				System.out.println("dashboard method called:::");
		return "dashboard";
	}
	
	
	@GetMapping("/enquiry")
	public String addEnquiryPage() {
		return "add-enquiry";
	}
	
	
	@GetMapping("/enquires")
	public String viewEnquiresPage() {
		return "view-enquires";
	}
	

}
 