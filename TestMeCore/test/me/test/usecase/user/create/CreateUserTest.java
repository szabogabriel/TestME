package me.test.usecase.user.create;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import me.test.entity.BasicTestClass;
import me.test.usecase.user.create.CreateUserResponseData.CreateUserResponseValue;

public class CreateUserTest extends BasicTestClass {
	
	private static CreateUserRequestData createUserRequestData_notExistingUser = null;
	
	private static CreateUserRequestData createUserRequestData_existingUser_correctPassword = null;
	
	@BeforeClass
	public static void init() {
		prepareUsersFolder(false);
		
		createUserRequestData_notExistingUser = new CreateUserRequestData() {
			@Override public String getUsername() { return "notExistingUser"; }
			@Override public String getPassword() { return "notExistingUserPassword"; }
		};
		
		createUserRequestData_existingUser_correctPassword = new CreateUserRequestData() {
			@Override public String getUsername() { return "existingUserCorrect"; }
			@Override public String getPassword() { return "existingUserCorrectPassword"; }
		};
		
	}
	
	@AfterClass
	public static void destroy() {
		cleanup();
	}
	
	@Test
	public void testCreateUserNotPreviouslyExisting() {
		CreateUserResponseData respData = USE_CASE_PROVIDER.createCreateUserUC().createUser(createUserRequestData_notExistingUser);
		
		assertNotNull(respData);
		assertNotNull(respData.getUser());
		
		assertEquals(CreateUserResponseData.CreateUserResponseValue.USER_CREATED, respData.success());
		
		assertTrue(respData.getUser().getUsername().equals(createUserRequestData_notExistingUser.getUsername()));
	}
	
	@Test
	public void testCreateUserPreviouslyExistingSamePassword() {
		CreateUserResponseData user1 = USE_CASE_PROVIDER.createCreateUserUC()
				.createUser(createUserRequestData_existingUser_correctPassword);
		
		CreateUserResponseData user2 = USE_CASE_PROVIDER.createCreateUserUC()
				.createUser(createUserRequestData_existingUser_correctPassword);
		
		assertEquals(CreateUserResponseValue.USER_CREATED, user1.success());
		assertEquals(CreateUserResponseValue.USERNAME_ALREADY_EXIST, user2.success());
		
		assertNotNull(user1.getUser());
		assertNull(user2.getUser());
	}
	
}
