package me.test.entity;

import java.io.File;

public interface EntityProviderInitData {
	
	public static final EntityProviderInitData DEFAULT_OBJECT = new EntityProviderInitData() {
		
		public File getTestsFolder() {
			return new File("/tmp/tests");
		}
		
		public File getUsersFolder() {
			return new File("/tmp/users");
		}
		
	};
	
	File getTestsFolder();
	
	File getUsersFolder();

}
