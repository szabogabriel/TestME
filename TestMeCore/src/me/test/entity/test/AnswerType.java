package me.test.entity.test;

import java.util.List;

public class AnswerType {
	
	private final String NAME;
	
	private final List<Question> QUESTION;
	
	private final String DESCRIPTION;
	
	private final double LIMIT;
	
	public AnswerType(String name, List<Question> questions, String description, double limit) {
		this.NAME = name;
		this.QUESTION = questions;
		this.DESCRIPTION = description;
		this.LIMIT = limit;
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
	
	public double getLimit() {
		return LIMIT;
	}

}
