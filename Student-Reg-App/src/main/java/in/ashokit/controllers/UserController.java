package in.ashokit.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.ashokit.binding.LoginForm;
import in.ashokit.binding.SignUpForm;
import in.ashokit.binding.UnlockForm;
import in.ashokit.service.UserService;

@Controller
public class UserController { 

	@Autowired
	private UserService userService;
	
	
	
	@GetMapping("/signup")
	public String signUpPage(Model model) {
		//this is the method to load the page
		SignUpForm user=new SignUpForm();
		model.addAttribute("user", user);

		return "signup";
	}
	
	//to submit the post we need one method with postRequest
		//this method is for Signup functionality
		//@ModelAttribute (when our form is submitted again i want to send the binding object back to the user)
		
	
	@PostMapping("/signup")
	public String handleSignUp(@ModelAttribute("user")SignUpForm form, Model model) {
		
		boolean status= userService.signUp(form);
		
		if(status) {
			
			model.addAttribute("succMsg","Account Created ,Check your Email");
			
		}else {
			
			model.addAttribute("errmsg","Email already exit ,Choose Unique Email");
			
		}
		return "signUp";
		
	}
	
	 
	@GetMapping("/unlock")
	public String unlockPage(@RequestParam String email,Model model) {

		
		//UnlockForm unlockformObj = new UnlockForm();
		
		UnlockForm unlockformObj=new UnlockForm();
		
		unlockformObj.setEmail(email);

		model.addAttribute("unlock",unlockformObj);
		
		return "unlock";
	}



	@PostMapping("/unlock")
	public String unlockUserAccount(@ModelAttribute("unlock")UnlockForm unlock,Model model) {
		
		System.out.println(unlock);
		
		if (unlock.getNewPwd().equals(unlock.getConfirmPwd())) {
			
		boolean status	=userService.unlockAccount(unlock);
		
		  if (status) {
			  model.addAttribute("succMsg", "Your account unlocked successfully");
		}else {
			model.addAttribute("errMsg", "Given Temporayr password is incorect,checked your Email");
		}
			
		}else {
			
			model.addAttribute("errMsg","New Pwd and Confirm pwd should match");
		}
		
		
	
		return "unlock";
	}
	

	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("loginForm",new LoginForm());

		return "login";
	}

	
	@PostMapping("/login")
	public String login(@ModelAttribute("loginForm") LoginForm loginForm,Model model){
		String status = userService.login(loginForm);
		
		if(status.contains("success")) {
			//redirect reqest to dashboard method 
			//return "dashboard"
			return "redirect:/dashboard";
			
		}
			
		model.addAttribute("errMsg", status);
		
		model.addAttribute("loginFormObj", new LoginForm());
		return "login";
	}
	

	
	@GetMapping("/forgot")
	public String forgotPwdPage() {
    
		return "forgotpwd";
	}
	
	
	@PostMapping("/forgotPwd")
	public String forgotPwd(@RequestParam("email") String email,Model model){
		System.out.println(email);
		
		boolean status = userService.forgotPwd(email);
		
		if(status) {
			//send the success message
			model.addAttribute("succMsg", "pwd sent to your email");
		}else {
			//else error message
			model.addAttribute("errMsg", "invalid email id");

		}
		
		return "forgotPwd";
	}

}
