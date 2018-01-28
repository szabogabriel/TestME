package me.test.entity.answer;

import java.util.HashMap;
import java.util.Map;

import me.test.entity.test.AnswerDescription;
import me.test.entity.test.Question;
import me.test.entity.test.Test;

public class Answer {
	
	private Test test;
	
	private Map<Question, AnswerDescription> answers = new HashMap<>();
	
	private String userID;
	
	private int age;
	
	private long timestamp;
	
	public void setTest(Test test) {
		this.test = test;
	}
	
	public Test getTest() {
		return test;
	}
	
	public void addAnswer(Question question, AnswerDescription answerDescription) {
		answers.put(question, answerDescription);
	}
	
	public AnswerDescription getAnswer(Question question) {
		return answers.get(question);
	}
	
	public void setUser(String user) {
		this.userID = user;
	}
	
	public String getUser() {
		return userID;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
}
