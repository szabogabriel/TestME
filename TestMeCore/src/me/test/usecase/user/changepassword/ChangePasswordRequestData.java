package me.test.usecase.user.changepassword;

public interface ChangePasswordRequestData {
	
	String getUsername();
	String getOldPassword();
	String getNewPassword();

}
