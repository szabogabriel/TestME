package me.test.tools;

public enum Gender {
	
	MALE("Male"),
	FEMALE("Female"),
	UNKNOWN("Unknown");

	private String desc;
	
	private Gender(String description) {
		this.desc = description;
	}
	
	public static Gender getGender(String name) {
		Gender ret = Gender.UNKNOWN;
		for (Gender it : values()) {
			if (it.desc.equals(name)) {
				ret = it;
			}
		}
		return ret;
	}
	
	public String toString() {
		return desc;
	}
}
