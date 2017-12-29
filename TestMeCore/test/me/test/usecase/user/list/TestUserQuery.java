package me.test.usecase.user.list;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import me.test.entity.BasicTestClass;
import me.test.entity.user.User;

public class TestUserQuery extends BasicTestClass {
	
	private static QueryUserRequestData qurd;
	
	@BeforeClass
	public static void init() {
		prepareUsersFolder();
		
		qurd = new QueryUserRequestData() {
			@Override
			public String getUserName() {
				return USER1_NAME;
			}
		};
	}
	
	@AfterClass
	public static void destroy() {
		cleanup();
	}
	
	@Test
	public void testQueryUsers() {
		QueryUserResponseData respData = USE_CASE_PROVIDER.createQueryUserUC().getUser(qurd);
		
		User u = respData.getUser();
		
		assertEquals(u.getUsername(), USER1_NAME);
	}

}
