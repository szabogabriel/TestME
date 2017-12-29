package me.test.servlet.viewmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.test.entity.test.Test;

public class TestVM implements ViewModel {
	
	private Test test = null;
	
	public void setTest(Test test) {
		this.test = test;
	}

	@Override
	public Map<String, Object> provideData() {
		return getTestData();
	}
	
	private Map<String, Object> getTestData() {
		Map<String, Object> ret = new HashMap<>();
		
		ret.put("test_title", test.getTitle());
		ret.put("instructions", test.getInstructions());
		ret.put("answer_note", test.getAnswerNote());
		ret.put("answers", getAnswers());
		ret.put("answer_types", getAnswerTypes());
		ret.put("questions", getQuestions());
		
		return ret;
	}
	
	private List<Map<String, Object>> getAnswers() {
		List<Map<String, Object>> ret = new ArrayList<>();
		
		Arrays.asList(test.getAnswerValues()).stream().forEach(t -> {
			Map<String, Object> tmp = new HashMap<>();
			tmp.put("answer_value", t);
			ret.add(tmp);
		});
		
		return ret;
	}
	
	private List<Map<String, Object>> getAnswerTypes() {
		List<Map<String, Object>> ret = new ArrayList<>();
		
		Arrays.asList(test.getAnswerTypeNames()).stream().forEach(t -> {
			Map<String, Object> tmp = new HashMap<>();
			tmp.put("answer_type", t);
			ret.add(tmp);
		});
		
		return ret;
	}
	
	private List<Map<String, Object>> getQuestions() {
		List<Map<String, Object>> ret = new ArrayList<>();
		
		Arrays.asList(test.getQuestions()).stream().forEach(q -> {
			Map<String, Object> tmp = new HashMap<>();
			tmp.put("question", q);
			ret.add(tmp);
		});
		
		return ret;
	}

}
