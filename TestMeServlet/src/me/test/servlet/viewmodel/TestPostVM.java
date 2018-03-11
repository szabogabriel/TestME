package me.test.servlet.viewmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.test.entity.test.AnswerType;

public class TestPostVM implements ViewModel {
	
	private List<AnswerType> answerTypes = new ArrayList<>();
	
	private boolean loggedIn = false;
	
	private String username = null;
	
	private String message = null;
	
	private boolean isDanger = false;
	private boolean isSuccess = true;
	
	public void addAnswerType(AnswerType answer) {
		answerTypes.add(answer);
	}
	
	public void addAnswerTypes(AnswerType[] answers) {
		for (AnswerType it : answers) { 
			addAnswerType(it);
		}
	}
	
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	public void setUsername(String name) {
		username = name;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setDanger() {
		this.isDanger = true;
		this.isSuccess = false;
	}
	
	public void setSuccess() {
		this.isSuccess = true;
		this.isDanger = false;
	}

	@Override
	public Map<String, Object> provideData() {
		Map<String, Object> ret = new HashMap<>();
		List<Map<String, Object>> list = new ArrayList<>(answerTypes.size());
		
		answerTypes.stream().forEach(t -> list.add(getDescriptionData(t)));
		
		ret.put("studentType", list);
		
		ret.put("isLoggedIn", loggedIn);
		if (loggedIn) {
			ret.put("loggedIn-username", username);
		}
		
		if (message != null) {
			ret.put("isMessage", Boolean.TRUE);
			ret.put("message", message);
			if (isSuccess) ret.put("isSuccess", isSuccess);
			if (isDanger) ret.put("isDanger", isDanger);
		}
		
		return ret;
	}
	
	private Map<String, Object> getDescriptionData(AnswerType type) {
		Map<String, Object> ret = new HashMap<>();
		
		ret.put("typeName", type.getName());
		ret.put("typeDesc", type.getDescription());
		
		return ret;
	}

}
