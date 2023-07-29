package in.saranshit.service;

import in.saranshit.request.LoginForm;
import in.saranshit.request.SignUpForm;
import in.saranshit.request.UnlockForm;

public interface UserService {
public String login(LoginForm login);
public boolean signUp(SignUpForm signup);
public boolean unlock(UnlockForm unlock); 
public boolean forgotPwd(String email);
 
}
