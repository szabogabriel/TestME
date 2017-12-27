package me.test.entity.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

public class User {

	private final String USERNAME;
	private final String SALT;
	private final String PASSWORD_HASH;
	private MessageDigest messageDigest = null;

	public User(String username, String salt, String password) {
		if (username == null || salt == null || password == null) {
			throw new IllegalArgumentException("Argument cannot be null!");
		}

		this.USERNAME = username;
		this.SALT = salt;
		this.PASSWORD_HASH = md5(password + salt);
	}

	public User(File file) {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		USERNAME = props.getProperty("username", null);
		SALT = props.getProperty("salt", null);
		PASSWORD_HASH = props.getProperty("passwordHash", null);

		if (USERNAME == null || SALT == null || PASSWORD_HASH == null) {
			throw new IllegalArgumentException(
					"Password file " + file.getAbsolutePath() + " doesn't contain every element.");
		}
	}

	public String getUsername() {
		return USERNAME;
	}

	public void persist(File rootFolder) {
		Properties p = new Properties();

		p.put("username", USERNAME);
		p.put("salt", SALT);
		p.put("passwordHash", PASSWORD_HASH);

		try {
			p.store(new FileOutputStream(rootFolder.getAbsolutePath() + "/" + USERNAME + ".properties"), "");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean matches(String password) {
		boolean ret = false;
		if (password != null)
			ret = md5(password + SALT).equals(PASSWORD_HASH);
		return ret;
	}

	private String md5(String data) {
		byte[] retArray = getMessageDigest().digest(data.getBytes());

		StringBuilder ret = new StringBuilder();

		for (int i = 0; i < retArray.length; i++) {
			String hex = Integer.toHexString(0xff & retArray[i]);
			if (hex.length() == 1) {
				ret.append('0');
			}
			ret.append(hex);
		}

		return ret.toString();
	}

	private MessageDigest getMessageDigest() {
		if (messageDigest == null) {
			try {
				messageDigest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}

		return messageDigest;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((PASSWORD_HASH == null) ? 0 : PASSWORD_HASH.hashCode());
		result = prime * result + ((SALT == null) ? 0 : SALT.hashCode());
		result = prime * result + ((USERNAME == null) ? 0 : USERNAME.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (PASSWORD_HASH == null) {
			if (other.PASSWORD_HASH != null)
				return false;
		} else if (!PASSWORD_HASH.equals(other.PASSWORD_HASH))
			return false;
		if (SALT == null) {
			if (other.SALT != null)
				return false;
		} else if (!SALT.equals(other.SALT))
			return false;
		if (USERNAME == null) {
			if (other.USERNAME != null)
				return false;
		} else if (!USERNAME.equals(other.USERNAME))
			return false;
		return true;
	}
	
	

}
