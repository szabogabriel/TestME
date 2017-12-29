package me.test.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {
	
	private static MessageDigest messageDigest = null;
	
	public static String md5(String data) {
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
	
	private static MessageDigest getMessageDigest() {
		if (messageDigest == null) {
			try {
				messageDigest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}

		return messageDigest;
	}


}
