package in.saranshit.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.saranshit.entity.StudentEnquiry;
import in.saranshit.request.DashboardResponse;
import in.saranshit.request.EnquiryForm;
import in.saranshit.request.EnquirySearchCriteria;
import in.saranshit.service.EnquiryService;

@Controller
public class EnquiryController {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private EnquiryService service;

		@GetMapping("/logout")
		public String logoutPage() {
			session.invalidate();
			return"index";
		}
		
		@GetMapping("/dashboard")
		public String dashboardPage(Model model) {
			Integer userId = (Integer)session.getAttribute("userId");			
			DashboardResponse dashboardData = service.getDashboardData(userId);			
			model.addAttribute("dashboardData", dashboardData);
			return "dashboard";
		}
		
		
		@GetMapping("/addEnq")
		public String addEnquiry(Model model) {
			EnquiryForm enquiryForm = new EnquiryForm();
			
			initForm(model);
			if(null!=session.getAttribute("editEnq")) {
				StudentEnquiry editEntity = (StudentEnquiry)session.getAttribute("editEnq");
				BeanUtils.copyProperties(editEntity, enquiryForm);
				session.removeAttribute("editEnq");
			}
				model.addAttribute("enqForm", enquiryForm);
			    return"add-enquiry";
		}
		
		
		@PostMapping("/addEnq")
		public String addEnqPage(@ModelAttribute("enqForm") EnquiryForm form,Model model) {
		   if(null!=session.getAttribute("stuId")) {
				boolean updateEnquiry = service.updateEnquiry(form);
				if(updateEnquiry) {
					model.addAttribute("succMsg", "Updated Successfully");
			    }else {
				    model.addAttribute("errMsg", "Something Went Wrong");
				    
				}
		  }else {
			boolean status = service.saveEnquiry(form);
			if(status) {
				model.addAttribute("succMsg", "Enquiry submitted successfully");
			}else {
				model.addAttribute("errMsg", "please try again");
			      }
			    }
			return "add-enquiry";
		}
		
		public void initForm(Model model) {
			List<String> courses = service.getCourses();
			//System.out.println(courses);
			List<String> status = service.getEnqStatus();
			model.addAttribute("courseName", courses);
			model.addAttribute("status", status);
		}
		
		@GetMapping("/enquires")
		public String viewEnquiryPage(Model model) {
			initForm(model);
			List<StudentEnquiry> enquiries = service.getEnquiry();
			model.addAttribute("enquiries", enquiries);
			return "view-enquiries";
		}
		
		@GetMapping("/filterData")
		public String getFilteredData(@RequestParam String courseName,@RequestParam String mode,@RequestParam String status,Model model) {
			EnquirySearchCriteria criteria=new EnquirySearchCriteria();
			criteria.setCourse(courseName);
			criteria.setMode(mode);
			criteria.setEnquiryStatus(status);
			//System.out.println(criteria);
			Integer userId = (Integer)session.getAttribute("userId");
			List<StudentEnquiry> filterEnquiries = service.getFilterData(criteria, userId);
			model.addAttribute("filterData", filterEnquiries);
			return "filtered-data";
		}
		
		@GetMapping("/edit/{id}")
		public String editPage(@PathVariable("id") Integer id) {
			//System.out.println(id);
			StudentEnquiry editEnq = service.editEnq(id);
			session.setAttribute("editEnq", editEnq);
			session.setAttribute("stuId", id);
			return "redirect:/addEnq";
		}
}
