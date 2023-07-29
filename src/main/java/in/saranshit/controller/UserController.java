package in.saranshit.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.saranshit.request.LoginForm;
import in.saranshit.request.SignUpForm;
import in.saranshit.request.UnlockForm;
import in.saranshit.service.UserService;

@Controller
public class UserController {
	
@Autowired
 private UserService service;


	@PostMapping("/signup")
	public String handleSignUp(@ModelAttribute("user") SignUpForm form,Model model) {
		boolean status = service.signUp(form);
		if(status){
			model.addAttribute("succMsg", "Check Your Email");
		}
		else{
			model.addAttribute("errMsg", "Problem Occured,Choose Unique Email");
		}
		return "signup";
		
	}
	
	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("user", new SignUpForm());
		return "signup";
	}
	
	@GetMapping("/unlock")
	public String unlockPage(@RequestParam String email,Model model) {
		UnlockForm unlockFormObj = new UnlockForm();
		unlockFormObj.setEmail(email);
		model.addAttribute("unlock", unlockFormObj);
		return "unlock";
	}
	
	@PostMapping("/unlock")
	public String unlockUserAcc(@ModelAttribute("unlock") UnlockForm unlock,Model model) {
		if(unlock.getNewPwd().equals(unlock.getConfirmPwd())) {
			boolean status = service.unlock(unlock);
			if(status) {
				model.addAttribute("succMsg","your account is unlocked");
			}else {
				model.addAttribute("errMsg", "temporary password is not correct");
			}
		}else {
		model.addAttribute("errMsg", "new password and confirm password must me same");
		}
		return "unlock";
	}
	
	
	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		return "login";
	}
	@PostMapping("/login")
	public String login(@ModelAttribute("loginForm") LoginForm form,Model model) {
        
        	 String status = service.login(form);
        	 
        if(status.contains("success")) {
        	
        	
        	return "redirect:/dashboard";
        }	 
        model.addAttribute("errMsg", status);
         
		return "login";
	}
	
	
	@GetMapping("/forgot")
	public String forgotPwd() {
		return "forgotPwd";
	}
	
	@PostMapping("/forgotPwd")
	public String forgotPwdPage(@RequestParam("email") String email,Model model) {
		boolean status = service.forgotPwd(email);
		if(status) {
			model.addAttribute("succMsg", "Password sent succussfullly to your email id");
		}else {
			model.addAttribute("errMsg", "Invalid Email Id");
		}
		return "forgotPwd";
	}
	
}
