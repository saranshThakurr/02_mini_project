package in.saranshit.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.saranshit.entity.UserDetails;
import in.saranshit.repo.UserDetailsRepo;
import in.saranshit.request.LoginForm;
import in.saranshit.request.SignUpForm;
import in.saranshit.request.UnlockForm;
import in.saranshit.utils.EmailUtils;
import in.saranshit.utils.PwdUtils;

@Service
public class UserServiceImpl implements UserService{
	
	    @Autowired
		private UserDetailsRepo userRepo;
	    
	    @Autowired
	    private EmailUtils emailUtils;
	    
	    @Autowired
	    private HttpSession session;
	    
	    
	    @Override
	    public boolean forgotPwd(String email) {
	    
	    	UserDetails entity = userRepo.findByEmail(email);
	    	
	    	if(entity==null) {
	    		return false;	    	
	    		}
	    	String subject="Recover Password";
	    	String body="your Password is :"+entity.getPassword();
	    	
	    	emailUtils.sendEmail(email, subject, body);
	    	
	    	return true;
	    
	    }
	    
	    @Override
	    public String login(LoginForm login) {
			
			  UserDetails entity = userRepo.findByEmailAndPassword(login.getEmail(),login.getPassword()); 
			  
			  if(entity==null) { 
				  return "invalid credentials"; 
			  }
			  if(entity.getAccStatus().equals("LOCKED")) {
				  return "your account is locked";
			  }
			 
			  session.setAttribute("userId", entity.getUserId());
	    	
	    	return "success";
	    }
	    
	    @Override
	    public boolean unlock(UnlockForm unlock) {

	    	UserDetails entity=userRepo.findByEmail(unlock.getEmail());
	    	if(entity.getPassword().equals(unlock.getTempPwd())) {
	    		entity.setPassword(unlock.getNewPwd());
	    		entity.setAccStatus("UNLOCKED");
	    			userRepo.save(entity);
	    	}else {
	    		return false;
	    	}
	    	
	    	return true;
	    }
	    
	    
@Override
public boolean signUp(SignUpForm signup) {
	
	UserDetails user = userRepo.findByEmail(signup.getEmail());
	if(user!=null) {
		return false;
	}
	
	//TODO:copy data from binding obj to entity obj
       UserDetails entity = new UserDetails();
	   BeanUtils.copyProperties(signup, entity);
	
	//TODO:generate rndm pwd and set to entity obj
	String tempPwd = PwdUtils.generateRandomPwd();
	 entity.setPassword(tempPwd);
	 
	//TODO:set acc status as locked
	 entity.setAccStatus("LOCKED");

	//TODO:insert records
	 userRepo.save(entity);
	 
	//TODO:send email to unlock the account
	 String to=signup.getEmail();
	 String subject="Unlock your Account";
	 StringBuffer body=new StringBuffer("");
	 body.append("<h1>use below temp pwd to unlock your account<h1>");
	 body.append("Temporary Password : "+tempPwd);
	 body.append("<br>");
	 body.append("<a href=\"http://localhost:9001/unlock?email="+to+"\">Click Here To UnLock Your Account</a>");
	 
	 emailUtils.sendEmail(to, subject, body.toString());
     return true;
}
}
