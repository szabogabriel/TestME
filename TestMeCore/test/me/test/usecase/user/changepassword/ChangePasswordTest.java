package me.test.usecase.user.changepassword;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import me.test.entity.BasicTestClass;
import me.test.entity.user.User;

public class ChangePasswordTest extends BasicTestClass {
	
	private static ChangePasswordRequestData requestChangePassword = null;
	
	@BeforeClass
	public static void init() {
		prepareUsersFolder();
		
		requestChangePassword = new ChangePasswordRequestData() {
			@Override public String getUsername() {	return USER1_NAME; }
			@Override public String getOldPassword() { return USER1_PSWD; }
			@Override public String getNewPassword() { return USER1_NEWPSSWD; }
		};
	}
	
	@AfterClass
	public static void destroy() {
		cleanup();
	}
	
	@Test
	public void testChangePassword() {
		User user = ENTITY_PROVIDER.getUserEntity().getUser(USER1_NAME);
		assertTrue(user.matchesPassword(USER1_PSWD));
		
		ChangePasswordResponseData response = USE_CASE_PROVIDER.createChangePasswordUC().changePassword(requestChangePassword);
		
		assertTrue(response.isChanged());

		user = ENTITY_PROVIDER.getUserEntity().getUser(USER1_NAME);
		
		assertNotNull(user);
		assertFalse(user.matchesPassword(USER1_PSWD));
		assertTrue(user.matchesPassword(USER1_NEWPSSWD));
	}

}
