package me.test.usecase.login;

import me.test.entity.EntityProvider;

public class LoginUC {
	
	private final EntityProvider ENTITY_PROVIDER; 
	
	public LoginUC(EntityProvider entityProvider) {
		this.ENTITY_PROVIDER = entityProvider;
	}
	
	public LoginResponseData loginUser(LoginRequestData requestData) {
		return null;
	}

}
