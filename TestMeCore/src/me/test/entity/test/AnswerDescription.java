package me.test.entity.test;

public class AnswerDescription {
	
	private final int VALUE;
	private final String DESCRIPTION;
	
	public AnswerDescription(int value, String description) {
		this.VALUE = value;
		this.DESCRIPTION = description;
	}
	
	public String getDescription() {
		return DESCRIPTION;
	}
	
	public int getValue() {
		return VALUE;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((DESCRIPTION == null) ? 0 : DESCRIPTION.hashCode());
		result = prime * result + VALUE;
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
		AnswerDescription other = (AnswerDescription) obj;
		if (DESCRIPTION == null) {
			if (other.DESCRIPTION != null)
				return false;
		} else if (!DESCRIPTION.equals(other.DESCRIPTION))
			return false;
		if (VALUE != other.VALUE)
			return false;
		return true;
	}

	

}
