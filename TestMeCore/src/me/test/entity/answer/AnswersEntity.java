package me.test.entity.answer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import me.test.entity.test.Test;

public class AnswersEntity {
	
	private final AnswersLoader LOADER;
	
	private final Map<Test, Set<Answer>> ANSWERS = new HashMap<>(); 
	
	public AnswersEntity(AnswersLoader loader) {
		LOADER = loader;
		
	}
	
}
