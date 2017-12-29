package me.test.usecase.user.credentials;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import me.test.entity.BasicTestClass;

public class LoginTest extends BasicTestClass {
	
	private static AuthenticateUserRequestData loginRequestData = null;
	
	@BeforeClass
	public static void init() {
		prepareUsersFolder();
		
		loginRequestData = new AuthenticateUserRequestData() {
			@Override
			public String getUserName() {
				return BasicTestClass.USER1_NAME;
			}

			@Override
			public String getPassword() {
				return BasicTestClass.USER1_PSWD;
			}
		};
	}
	
	@AfterClass
	public static void destroy() {
		cleanup();
	}
	
	@Test
	public void testLogin() {
		AuthenticateUserUC uc = USE_CASE_PROVIDER.createAuthenticateUserUC();
		AuthenticateUserResponseData data = uc.isCredentialsCorrect(loginRequestData);
		
		assertTrue(data.result());
	}

}
