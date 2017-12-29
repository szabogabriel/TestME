package me.test.usecase.user.credentials;

import me.test.entity.EntityProvider;

public class AuthenticateUserUC {
	
	private final EntityProvider ENTITY_PROVIDER; 
	
	public AuthenticateUserUC(EntityProvider entityProvider) {
		this.ENTITY_PROVIDER = entityProvider;
	}
	
	public AuthenticateUserResponseData isCredentialsCorrect(AuthenticateUserRequestData requestData) {
		return () -> ENTITY_PROVIDER.getUserEntity().isCredentialCorrect(requestData.getUserName(), requestData.getPassword());
	}

}
