package me.test.usecase.user.changepassword;

import me.test.entity.EntityProvider;
import me.test.entity.user.User;

public class ChangePasswordUC {
	
	private final EntityProvider ENTITIY_PROVIDER;
	
	public ChangePasswordUC(EntityProvider provider) {
		this.ENTITIY_PROVIDER = provider;
	}
	
	public ChangePasswordResponseData changePassword(ChangePasswordRequestData data) {
		User user = ENTITIY_PROVIDER.getUserEntity().getUser(data.getUsername());
		
		if (user != null && user.matchesPassword(data.getOldPassword())) {
			ENTITIY_PROVIDER.getUserEntity().deleteUser(user);
			ENTITIY_PROVIDER.getUserEntity().createUser(data.getUsername(), data.getNewPassword());
			return () -> true;
		}
		
		return () -> false;
	}

}
