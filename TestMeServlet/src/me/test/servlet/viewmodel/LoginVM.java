package me.test.servlet.viewmodel;

import java.util.HashMap;
import java.util.Map;

public class LoginVM implements ViewModel {
	
	private String username;
	
	private boolean successful = false;
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

	@Override
	public Map<String, Object> provideData() {
		Map<String, Object> ret = new HashMap<>();
		
		if (username != null) ret.put("username", username);
		
		ret.put("active-login", !successful);
		ret.put("active-logout", Boolean.FALSE);
		ret.put("active-manage-test", Boolean.FALSE);
		ret.put("isLoggedIn", successful);
		
		return ret;
	}

}
