package me.test;

import java.io.File;

import me.test.test.TestsHolder;
import me.test.user.UserHelper;

public class EntityManager {
	
	private final TestsHolder TESTS_HOLDER;
	
	private final UserHelper USER_HELPER;
	
	public EntityManager(File testsFolder, File usersFolder) {
			TESTS_HOLDER = new TestsHolder(testsFolder);
			USER_HELPER = new UserHelper(usersFolder);
	}
	
	
}
