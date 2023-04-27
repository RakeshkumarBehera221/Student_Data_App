package in.ashokit.service;

import in.ashokit.binding.LoginForm;
import in.ashokit.binding.SignUpForm;
import in.ashokit.binding.UnlockForm;

public interface UserService {
	
	public String login(LoginForm form);
	
	public boolean signUp(SignUpForm form);
	
//	public String emailCheck(String email);
	
	public boolean unlockAccount(UnlockForm form);
	
	public boolean forgotPwd(String email);

}
 