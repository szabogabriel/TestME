package me.test.usecase.user.list;

import java.util.Arrays;

import me.test.entity.EntityProvider;

public class QueryUserUC {
	
	private final EntityProvider ENTITY_PROVIDER;
	
	public QueryUserUC(EntityProvider manager) {
		this.ENTITY_PROVIDER = manager;
	}
	
	public QueryUserResponseData getUser(QueryUserRequestData data) {
		String username = data.getUserName();
		if (username == null) {
			return () -> ENTITY_PROVIDER.getUserEntity().getUsers();
		} else {
			return () -> Arrays.asList(ENTITY_PROVIDER.getUserEntity().getUser(username));
		}
		
	}

}
