package me.test.entity.test;

public class Question {
	
	private final int ID;
	private final String TEXT;
	
	public Question(int ID, String text) {
		this.ID = ID;
		this.TEXT = text;
	}
	
	public String getText() {
		return TEXT;
	}
	
	public int getId() {
		return ID;
	}
	
}
