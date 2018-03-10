package me.test.servlet.viewmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.test.entity.test.Test;
import me.test.tools.Gender;

public class TestVM implements ViewModel {
	
	private Test test = null;
	
	private boolean loggedIn = false;
	private String username = null;
	
	private int resp_age = -1;
	private String resp_name = null;
	private Gender resp_gender = null;
	
	public void setTest(Test test) {
		this.test = test;
	}
	
	public void setLoggedIn(boolean value) {
		this.loggedIn = value;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setResponserAge(int age) {
		this.resp_age = age;
	}
	
	public void setRespName(String name) {
		this.resp_name = name;
	}
	
	public void setRespGender(Gender gender) {
		this.resp_gender = gender;
	}
	
	@Override
	public Map<String, Object> provideData() {
		return getTestData();
	}
	
	private Map<String, Object> getTestData() {
		Map<String, Object> ret = new HashMap<>();
		
		if (loggedIn) {
			ret.put("isLoggedIn", loggedIn);
			ret.put("loggedIn-username", username);
		}
		
		if (resp_age != -1) ret.put("responder_age", new Integer(resp_age));
		if (resp_name != null) ret.put("responder_name", resp_name);
		if (resp_gender != null) ret.put("responder_gender", resp_gender.toString());
		
		if (test != null) {
			ret.put("test_set", Boolean.TRUE);
			ret.put("test_title", test.getTitle());
			ret.put("instructions", test.getInstructions());
			ret.put("test_name", test.getName());
			ret.put("answer_note_present", test.getAnswerNote() != null && test.getAnswerNote().length() > 0);
			ret.put("answer_note", test.getAnswerNote());
			ret.put("answers", getAnswers());
			ret.put("answer_types", getAnswerTypes());
			ret.put("questions", getQuestions());
		} else {
			ret.put("test_set", Boolean.FALSE);
		}
		
		return ret;
	}
	
	private List<Map<String, Object>> getAnswers() {
		List<Map<String, Object>> ret = new ArrayList<>();
		
		Arrays.asList(test.getAnswerDescriptions()).stream().forEach(t -> {
			Map<String, Object> tmp = new HashMap<>();
			tmp.put("answer_value", t.getDescription());
			ret.add(tmp);
		});
		
		return ret;
	}
	
	private List<Map<String, Object>> getAnswerTypes() {
		List<Map<String, Object>> ret = new ArrayList<>();
		
		Arrays.asList(test.getAnswerTypes()).stream().forEach(t -> {
			Map<String, Object> tmp = new HashMap<>();
			tmp.put("answer_type", t.getName());
			ret.add(tmp);
		});
		
		return ret;
	}
	
	private List<Map<String, Object>> getQuestions() {
		List<Map<String, Object>> ret = new ArrayList<>();
		
		Arrays.asList(test.getQuestions()).stream().forEach(q -> {
			Map<String, Object> tmp = new HashMap<>();
			tmp.put("question", q.getText());
			tmp.put("question-id", q.getId() + "");
			ret.add(tmp);
		});
		
		return ret;
	}

}
