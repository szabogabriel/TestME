package me.test.entity.answer;

import java.io.File;

public class AnswersEntity {
	
	private final File ROOT;
	
	public AnswersEntity(File rootFolder) {
		ROOT = rootFolder;
		reload();
	}

}
