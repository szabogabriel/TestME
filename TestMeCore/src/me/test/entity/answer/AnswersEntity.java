package me.test.entity.answer;

import java.util.Set;

import me.test.entity.test.Test;

public class AnswersEntity {
	
	private final AnswersLoader LOADER;
	
	public AnswersEntity(AnswersLoader loader) {
		LOADER = loader;
		
	}
	
	public void save(Answer answer) {
		LOADER.save(answer);
	}
	
	public Set<Answer> load(Test test) {
		return LOADER.load(test);
	}
	
}
