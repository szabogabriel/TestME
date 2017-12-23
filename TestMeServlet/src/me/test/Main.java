package me.test;

import java.io.File;

import me.test.config.Config;
import me.test.test.TestsHolder;
import me.test.user.UserHelper;

public class Main {
	
	public static final Main INSTANCE = new Main();
	
	private final TestsHolder TESTS_HOLDER;
	
	private final UserHelper USER_HELPER;
	
	private Main() {
		TESTS_HOLDER = new TestsHolder(new File(Config.DIR_TESTS.toString()));
		USER_HELPER = new UserHelper(new File(Config.DIR_USERS.toString()));
	}
	
	public TestsHolder getTestsHolder() {
		return TESTS_HOLDER;
	}
	
	public UserHelper getUserHelper() {
		return USER_HELPER;
	}

}
