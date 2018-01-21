
package me.test.entity.test;

import java.util.Arrays;

public class Test {
	
	public static final Test NULL_OBJECT = new Test();
	
	private String name = null;
	
	private String title = null;
	private String instructions = null;
	private String answerNote = null;
	
	private Question[] questions = null;
	private AnswerType[] answerTypes = null;
	private AnswerDescription[] answerDescriptions = null;
	
	private boolean isActive = false;
	
	public Test() {
		name = "";
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getInstructions() {
		return instructions;
	}
	
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	
	public AnswerType[] getAnswerTypes() {
		return answerTypes;
	}
	
	public AnswerType getAnswerTypeByName(String name) {
		AnswerType at = Arrays.asList(getAnswerTypes()).stream().filter(a -> a.getName().equals(name)).findAny().orElse(null);
		return at;
	}
	
	public void setAnswerTypes(AnswerType[] answerTypes) {
		this.answerTypes = answerTypes; 
	}
	
	public AnswerDescription[] getAnswerDescriptions() {
		return answerDescriptions;
	}
	
	public void setAnswerDescriptions(AnswerDescription[] descriptions) {
		this.answerDescriptions = descriptions;
	}
	
	public String getAnswerNote() {
		return answerNote;
	}
	
	public void setAnswerNote(String note) {
		this.answerNote = note;
	}
	
	public Question[] getQuestions() {
		return questions;
	}
	
	public Question[] getQuestions(String answerType) {
		Question[] ret = getAnswerTypeByName(answerType).getQuestions().stream().toArray(Question[]::new);
		
		return ret;
	}
	
	public void setQuestions(Question[] questions) {
		this.questions = questions;
	}
	
	public void setActive(boolean value) {
		this.isActive = value;
	}
	
	public boolean isActive() {
		return isActive;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Test other = (Test) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
