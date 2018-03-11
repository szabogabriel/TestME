package me.test.entity.answer;

import java.util.HashMap;
import java.util.Map;

import me.test.entity.test.AnswerDescription;
import me.test.entity.test.AnswerType;
import me.test.entity.test.Question;
import me.test.entity.test.Test;
import me.test.tools.Gender;

public class Answer {
	
	private Test test;
	
	private Map<Question, AnswerDescription> answers = new HashMap<>();
	
	private String userID;
	
	private int age;
	
	private Gender gender;
	
	private String description;
	
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
	
	public int getAnswerValue(Question question) {
		int ret = getAnswer(question).getValue();
		if (question.isNegative()) {
			ret = test.getMaxAnswerValue() - ret + 1; 
		}
		return ret;
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
	
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public Gender getGender() {
		return gender;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public AnswerType[] getAnswerDescriptionsByAnswers() {
		final Map<AnswerType, Double> tmp = new HashMap<>();
		
		AnswerType[] answerTypes = getTest().getAnswerTypes();
		
		for (AnswerType it : answerTypes) {
			Question[] questions = getTest().getQuestions(it.getName());
			double avg = 0;
			for (Question it2 : questions) {
				avg += (double)getAnswerValue(it2);
			}
			avg /= (double)questions.length;
			if (avg > it.getLimit()) {
				tmp.put(it, avg - it.getLimit());
			}
		}
		
		AnswerType[] ret = tmp.keySet().stream().sorted((k1, k2) -> (tmp.get(k1) < tmp.get(k2) ? -1 : 1)).toArray(AnswerType[]::new);
		
		return ret;
	}
	
	public boolean isAnswerCorrect() {
		Question[] questions = test.getQuestions();
		
		for (Question it : questions) {
			if (answers.get(it) == null) {
				return false;
			}
		}
		
		return true;
	}
	
}
