package me.test.entity.user;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserTest {
	
	private static final String USERNAME = "admin";
	private static final String SALT = "Keps4";
	private static final String PASSWORD = "admin";
	
	private static final String CHECK_USERNAME = "admin";
	private static final String CHECK_SALT = "Keps4";
	private static final String CHECK_PASSWORD_HASH = "2ec58c88ad5b267c6a00175161cb6c4b";
	
	private static final User user = new User(USERNAME, SALT, PASSWORD);
	
	private static final File targetTempDir = new File("./test/me/test/entity/user/");
	private static final File targetTempFile = new File(targetTempDir.getAbsolutePath() + "/" + USERNAME + ".properties");
	
	@BeforeClass
	public static void create() {
		if (targetTempFile.exists()) {
			targetTempFile.delete();
		}
	}
	
	@Test
	public void testPasswordEquals() {
		assertEquals(user.matchesPassword(PASSWORD), true);
	}
	
	@Test
	public void testUsername() {
		assertEquals(user.getUsername(), CHECK_USERNAME);
	}
	
	@Test
	public void testPersist() throws FileNotFoundException, IOException {
		user.persist(targetTempDir);
		
		assertEquals(targetTempFile.exists(), true);
		
		Properties props = new Properties();
		props.load(new FileInputStream(targetTempFile));
		
		assertEquals(props.containsKey("username"), true);
		assertEquals(props.containsKey("salt"), true);
		assertEquals(props.containsKey("passwordHash"), true);
		
		assertEquals(props.getProperty("username"), CHECK_USERNAME);
		assertEquals(props.getProperty("salt"), CHECK_SALT);
		assertEquals(props.getProperty("passwordHash"), CHECK_PASSWORD_HASH);
	}
	
	@AfterClass
	public static void clear() {
		if (targetTempFile.exists()) {
			targetTempFile.delete();
		}
	}

}
