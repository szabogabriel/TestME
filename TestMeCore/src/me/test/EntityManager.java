package me.test;

import java.io.File;

import me.test.entity.test.TestsEntity;
import me.test.entity.user.UserEntity;

public class EntityManager {
	
	private final TestsEntity TESTS_HOLDER;
	
	private final UserEntity USER_HELPER;
	
	public EntityManager(File testsFolder, File usersFolder) {
			TESTS_HOLDER = new TestsEntity(testsFolder);
			USER_HELPER = new UserEntity(usersFolder);
	}
	
	
}
