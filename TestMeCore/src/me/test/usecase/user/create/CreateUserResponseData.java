package me.test.usecase.user.create;

import me.test.entity.user.User;

public interface CreateUserResponseData {
	
	public enum CreateUserResponseValue {
		USER_CREATED,
		USERNAME_ALREADY_EXIST,
		ERROR;
	}
	
	CreateUserResponseValue success();
	User getUser();

}
