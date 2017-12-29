package me.test.entity.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserEntityTest {
	
	private static final String USERNAME = "admin";
	private static final String SALT = "Keps4";
	private static final String PASSWORD = "admin";
	
	private static final String CHECK_USERNAME = "admin";
	private static final String CHECK_SALT = "Keps4";
	private static final String CHECK_PASSWORD_HASH = "2ec58c88ad5b267c6a00175161cb6c4b";
	
	private static final File targetDir = new File("./test/me/test/entity/user");
	private static final File targetFile = new File(targetDir.getAbsolutePath() + "/" + USERNAME + ".properties");
	
	@BeforeClass
	public static void prepare() throws FileNotFoundException, IOException {
		Properties swp = new Properties();
		
		swp.put("username", CHECK_USERNAME);
		swp.put("salt", CHECK_SALT);
		swp.put("passwordHash", CHECK_PASSWORD_HASH);
		
		swp.store(new FileOutputStream(targetFile), "");
	}
	
	@Test
	public void testGetUser() {
		UserEntity ue = new UserEntity(targetDir);
		
		User u1 = ue.getUser(USERNAME);
		User u2 = new User(USERNAME, SALT, PASSWORD);
		
		assertNotNull(u1);
		
		assertEquals(u1, u2);
	}
	
	@Test
	public void testCredentials() {
		UserEntity ue = new UserEntity(targetDir);
		
		assertEquals(ue.isCredentialCorrect(USERNAME, PASSWORD), true);
	}
	
	@Test
	public void testCreateUser() {
		UserEntity ue = new UserEntity(targetDir);
		
		ue.createUser("user", "user");
		
		assertEquals(Arrays.asList(targetDir.listFiles()).stream().filter(f -> f.getName().endsWith(".properties")).count(), 2);
	}
	
	@AfterClass
	public static void cleanup() {
		targetFile.delete();
		File tmp = new File(targetDir.getAbsolutePath() + "/user.properties");
		tmp.delete();
	}
}
