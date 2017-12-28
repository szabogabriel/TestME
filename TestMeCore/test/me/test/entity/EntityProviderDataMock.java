package me.test.entity;

import java.io.File;

public class EntityProviderDataMock implements EntityProviderInitData {

	@Override
	public File getUsersFolder() {
		return new File("./test/data/users");
	}

	@Override
	public File getTestsFolder() {
		return new File("./test/data/tests");
	}
	
}
