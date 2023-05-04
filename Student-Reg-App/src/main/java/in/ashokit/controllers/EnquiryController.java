package in.ashokit.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.ashokit.binding.DashboardResponse;
import in.ashokit.binding.EnquiryForm;
import in.ashokit.binding.EnquirySearchCriteria;
import in.ashokit.entity.StudentEnqEntity;
import in.ashokit.service.EnquiryService;

@Controller
public class EnquiryController {

	@Autowired
	private EnquiryService enqService;

	@Autowired
	private HttpSession session;

	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}

	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {

		Integer userId = (Integer) session.getAttribute("userId");

		DashboardResponse dashboardData = enqService.getDashboardData(userId);

		model.addAttribute("dashboardData", dashboardData);

		System.out.println("dashboard method called:::");
		return "dashboard";
	}

	@PostMapping("/addEnq")
	public String addEnquiry(@ModelAttribute("formObj") EnquiryForm formObj, Model model) {
		System.out.println(formObj);// to checked whether the data is comming to the controller or not
		// TODO:save the data
		boolean status = enqService.saveEnquriry(formObj);
		if (status) {
			model.addAttribute("succMsg", "Enquiry Added");
		} else {
			model.addAttribute("errMsg", "Problem Occured");
		}

		return "add-enquiry";
	}

	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {

		// get courses for drop down
		List<String> courses = enqService.getCourses();
		// get enq status for drop down
		List<String> enqStatuses = enqService.getCourses();
		// create binding class obj
	EnquiryForm formObj = new EnquiryForm();

		// set the data in model object

		model.addAttribute("courseNames", courses);
		model.addAttribute("statusNames", enqStatuses);
		model.addAttribute("formObj", formObj);

		return "add-enquiry";
	}
	
	private void initForm(Model model) {
		//get course from drop down
		List<String> courses = enqService.getCourses();
		//get enquiries status for drop down
		List<String> enqStatuses = enqService.getEnqStatuses();
		//created binding class obj
		EnquiryForm formObj=new EnquiryForm();
		//set data in model object
		model.addAttribute("courseNames", courses);
		model.addAttribute("statusNames", enqStatuses);
		model.addAttribute("formObj", formObj);

		
		
	}

	@GetMapping("/enquiries")
	public String viewEnquiresPage(Model model) {
		initForm(model);
		model.addAttribute("searchForm",new EnquirySearchCriteria());
		List<StudentEnqEntity> enquiries= enqService.getEnquries();
		model.addAttribute("enquiries",enquiries);
		
		return "view-enquiries";
	}

	
	@GetMapping("/filter-enquiries")
	public String getFilteredEnqs(@RequestParam String cname,
			                      @RequestParam String status,
			                      @RequestParam  String mode,
			                      Model model) {
		
		
		EnquirySearchCriteria criteria=new EnquirySearchCriteria();
		criteria.setCourseName(cname);
		criteria.setClassMode(mode);
		criteria.setEnqStatus(status);
	
		Integer userId = (Integer) session.getAttribute("userId");
		
	List<StudentEnqEntity> filteredEnqs	= enqService.getFilteredEnqs(criteria, userId);
		
	model.addAttribute("enquiries" ,filteredEnqs);
	
		return "filter-enquiries";
	}

}
