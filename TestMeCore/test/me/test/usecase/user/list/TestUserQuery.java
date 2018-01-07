package me.test.usecase.user.list;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import me.test.entity.BasicTestClass;
import me.test.entity.user.User;

public class TestUserQuery extends BasicTestClass {
	
	@BeforeClass
	public static void init() {
		prepareUsersFolder();
	}
	
	@AfterClass
	public static void destroy() {
		cleanup();
	}
	
	@Test
	public void testQueryUsers() {
		QueryUserResponseData respData = USE_CASE_PROVIDER.createQueryUserUC().getUser(() -> USER1_NAME);
		
		User u = respData.getUser().get(0);
		
		assertEquals(u.getUsername(), USER1_NAME);
	}
	
	@Test
	public void testQueryAllUsers() {
		QueryUserResponseData respData = USE_CASE_PROVIDER.createQueryUserUC().getUser(() -> null);
		
		User u = respData.getUser().get(0);
		
		assertEquals(u.getUsername(), USER1_NAME);
	}

}
