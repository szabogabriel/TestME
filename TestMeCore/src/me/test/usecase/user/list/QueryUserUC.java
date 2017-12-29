package me.test.usecase.user.list;

import me.test.entity.EntityProvider;

public class QueryUserUC {
	
	private final EntityProvider ENTITIY_PROVIDER;
	
	public QueryUserUC(EntityProvider manager) {
		this.ENTITIY_PROVIDER = manager;
	}
	
	public QueryUserResponseData getUser(QueryUserRequestData data) {
		return () -> ENTITIY_PROVIDER.getUserEntity().getUser(data.getUserName());
	}

}
