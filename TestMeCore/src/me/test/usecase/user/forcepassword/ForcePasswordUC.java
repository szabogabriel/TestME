package me.test.usecase.user.forcepassword;

import me.test.entity.EntityProvider;
import me.test.entity.user.User;

public class ForcePasswordUC {
	
	private final EntityProvider ENTITY_PROVIDER;
	
	public ForcePasswordUC(EntityProvider provider) {
		this.ENTITY_PROVIDER = provider;
	}
	
	public ForcePasswordResponseData setPassword(ForcePasswordRequestData data) {
		if (data.getUsername() != null && data.getPassword() != null) {
			User user = ENTITY_PROVIDER.getUserEntity().getUser(data.getUsername());
			
			if (user != null) {
				ENTITY_PROVIDER.getUserEntity().deleteUser(user);
				ENTITY_PROVIDER.getUserEntity().createUser(data.getUsername(), data.getPassword());
				return () -> true;
			}
		}
		
		return () -> false;
	}

}
