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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
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
		Question other = (Question) obj;
		if (ID != other.ID)
			return false;
		return true;
	}
	
	
	
}
