package me.test.entity.test;

import java.util.List;

public class AnswerType {
	
	private final String NAME;
	
	private final List<Question> QUESTION;
	
	private final String DESCRIPTION;
	
	public AnswerType(String name, List<Question> questions, String description) {
		this.NAME = name;
		this.QUESTION = questions;
		this.DESCRIPTION = description;
	}
	
	public String getName() {
		return NAME;
	}
	
	public List<Question> getQuestions() {
		return QUESTION;
	}
	
	public String getDescription() {
		return DESCRIPTION;
	}

}
