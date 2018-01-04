package me.test.usecase.user.create;

import me.test.entity.EntityProvider;
import me.test.entity.user.User;
import me.test.entity.user.UserEntity;
import me.test.usecase.user.create.CreateUserResponseData.CreateUserResponseValue;

public class CreateUserUC {
	
	private final EntityProvider ENTITY_PROVIDER;
	
	public CreateUserUC(EntityProvider provider) {
		this.ENTITY_PROVIDER = provider;
	}
	
	
	
	public CreateUserResponseData createUser(CreateUserRequestData data) {
		UserEntity ue = ENTITY_PROVIDER.getUserEntity();
		
		CreateUserResponseData.CreateUserResponseValue retValue = CreateUserResponseValue.ERROR;
		User retUser = null;
		
		try {
			if (!ue.isKnownUsername(data.getUsername())) {
				ue.createUser(data.getUsername(), data.getPassword());
				retValue = CreateUserResponseValue.USER_CREATED;
			} else {
				retValue = CreateUserResponseValue.USERNAME_ALREADY_EXIST;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (retValue.equals(CreateUserResponseValue.USER_CREATED)) {
			retUser = ue.getUser(data.getUsername());
		}
		
		return new Response(retValue, retUser);
	}
	
	
	
	
	private class Response implements CreateUserResponseData {
		
		private final CreateUserResponseData.CreateUserResponseValue VALUE;
		private final User USER;
		
		public Response(CreateUserResponseData.CreateUserResponseValue value, User user) {
			this.VALUE = value;
			this.USER = user;
		}

		@Override
		public CreateUserResponseValue success() {
			return VALUE;
		}

		@Override
		public User getUser() {
			return USER;
		}
		
	}

}
