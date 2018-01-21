package me.test.entity;

import java.io.File;

import me.test.entity.answer.AnswersFolderLoader;
import me.test.entity.answer.AnswersLoader;
import me.test.entity.test.TestsFolderLoader;
import me.test.entity.test.TestsLoader;

public interface EntityProviderInitData {
	
	public static final EntityProviderInitData DEFAULT_OBJECT = new EntityProviderInitData() {
		
		public TestsLoader getTestsLoader() {
			return new TestsFolderLoader(new File("/tmp/tests"));
		}
		
		public File getUsersFolder() {
			return new File("/tmp/users");
		}
		
		public AnswersLoader getAnswersLoader() {
			return new AnswersFolderLoader(new File("/tmp/answers"));
		}
		
	};
	
	TestsLoader getTestsLoader();
	
	File getUsersFolder();
	
	AnswersLoader getAnswersLoader();

}
