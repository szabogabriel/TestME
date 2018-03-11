package me.test.entity.test;

public class Question {
	
	private final int ID;
	private final String TEXT;
	private final boolean NEGATIVE;
	
	public Question(int ID, String text, boolean isNegative) {
		this.ID = ID;
		this.TEXT = text;
		this.NEGATIVE = isNegative;
	}
	
	public String getText() {
		return TEXT;
	}
	
	public int getId() {
		return ID;
	}
	
	public boolean isNegative() {
		return NEGATIVE;
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
