package me.test.entity.user;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import me.test.tools.RandomUtility;

public class UserHelper {
	
	private final Map<String, User> USERS = new HashMap<>();
	private final File ROOT;
	
	public UserHelper(File rootFolderForUsers) {
		if (rootFolderForUsers.exists() && rootFolderForUsers.isDirectory()) {
			ROOT = rootFolderForUsers;
			loadUsers();
		} else {
			throw new IllegalArgumentException("Root folder " + rootFolderForUsers.getAbsolutePath() + " doesn't exist.");
		}
	}
	
	public void createUser(String username, String password) {
		String salt = RandomUtility.generateSalt();
		User user = new User(username, salt, password);
		user.persist(ROOT);
	}
	
	public boolean isCredentialCorrect(String username, String password) {
		return getUser(username).matches(password);
	}
	
	public User getUser(String username) {
		if (username != null && !USERS.containsKey(username)) {
			loadUser(username);
		}
		return USERS.get(username);
	}
	
	private void loadUsers() {
		for (File it : ROOT.listFiles()) {
			User tmp = new User(it);
			USERS.put(tmp.getUsername(), tmp);
		}
	}
	
	private void loadUser(String username) {
		File f = new File(ROOT.getAbsolutePath() + "/" + username + ".properties");
		
		if (f.exists() && f.isFile()) {
			User u = new User(f);
			USERS.put(username, u);
		}
	}

}
