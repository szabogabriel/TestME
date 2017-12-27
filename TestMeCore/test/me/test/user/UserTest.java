package me.test.user;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import me.test.entity.user.User;
import me.test.entity.user.UserHelper;

public class UserTest {
	
	private static final String USERNAME = "TestUser";
	private static final String SALT = "salt";
	private static final String PASSWORD = "TestPassword";
	
	private static final User user = new User(USERNAME, SALT, PASSWORD);
	
	private static final File targetTempDir = new File("./test/me/test/user/");
	
	private static final UserHelper userHelper = new UserHelper(targetTempDir);
	
	@BeforeClass
	public static void create() {
		if (targetTempDir.exists()) {
			targetTempDir.delete();
		}
	}
	
	@Test
	public void testPasswordEquals() {
		assertEquals(user.matches(PASSWORD), true);
	}
	
	@Test
	public void testUsername() {
		assertEquals(user.getUsername(), USERNAME);
	}
	
	@Test
	public void testPersist() {
		user.persist(targetTempDir);
		assertEquals(targetTempDir.exists(), true);
		
		User u2 = userHelper.getUser(user.getUsername());
		
		assertEquals(u2, user);
	}
	
	@AfterClass
	public static void clear() {
		if (targetTempDir.exists()) {
			targetTempDir.delete();
		}
	}

}
