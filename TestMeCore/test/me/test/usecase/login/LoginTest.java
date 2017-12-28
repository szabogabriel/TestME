package me.test.usecase.login;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import me.test.entity.EntityProvider;
import me.test.entity.EntityProviderDataMock;
import me.test.usecase.UseCaseProvider;

public class LoginTest {
	
	private static LoginRequestData loginRequestData = null;
	private static EntityProvider entityProvider = null;
	private static UseCaseProvider useCaseProvider = null;
	
	@BeforeClass
	public static void init() {
		loginRequestData = new LoginRequestData() {
			@Override
			public String getUserName() {
				return "username";
			}

			@Override
			public String getPassword() {
				return "password";
			}
		};
		
		entityProvider = new EntityProvider(new EntityProviderDataMock());
		useCaseProvider = new UseCaseProvider(entityProvider);
	}
	
	@AfterClass
	public static void destroy() {
		// nothing to do at the moment
	}
	
	@Test
	public void testLogin() {
		
	}

}
