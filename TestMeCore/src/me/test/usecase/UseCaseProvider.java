package me.test.usecase;

import me.test.entity.EntityProvider;
import me.test.usecase.login.LoginUC;

public class UseCaseProvider {
	
	private final EntityProvider ENTITY_PROVIDER;
	
	public UseCaseProvider(EntityProvider entityProvider) {
		this.ENTITY_PROVIDER = entityProvider;
	}
	
	public LoginUC createLoginRequest() {
		return new LoginUC(ENTITY_PROVIDER);
	}

}
