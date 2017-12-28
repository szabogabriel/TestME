package me.test.usecase.logout;

import me.test.entity.EntityProvider;

public class LogoutUC {
	
	private final EntityProvider ENTITY_PROVIDER;
	
	public LogoutUC(EntityProvider entityProvider) {
		ENTITY_PROVIDER = entityProvider;
	}
	
	public LogoutResponseData logout(LogoutRequestData data) {
		return null;
	}

}
