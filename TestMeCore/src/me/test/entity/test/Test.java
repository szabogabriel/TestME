
package me.test.entity.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Test {
	
	private static final String KEY_TITLE = "title";
	private static final String KEY_INSTRUCTIONS = "instructions";
	private static final String KEY_ANSWER_NOTE = "answerNote";
	private static final String PREFIX_ANSWER = "answer.";
	private static final String PREFIX_ANSWER_TYPE_NAME = "answerType.name.";
	private static final String PREFIX_ANSWER_TYPE_QUESTIONS = "answerType.questions.";
	private static final String PREFIX_ANSWER_TYPE_DESCRIPTION = "answerType.description.";
	private static final String PREFIX_QUESTION = "question.";
	
	private final Properties TEST_VALUES = new Properties();
	
	private final String fileName;
	
	public Test(File file) throws FileNotFoundException, IOException {
		TEST_VALUES.load(new FileInputStream(file));
		fileName = file.getName().substring(0, file.getName().indexOf("."));
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public String getTitle() {
		return TEST_VALUES.getProperty(KEY_TITLE, "");
	}
	
	public String getInstructions() {
		return TEST_VALUES.getProperty(KEY_INSTRUCTIONS, "");
	}
	
	public String[] getAnswerValues() {
		return getIndexedValues(PREFIX_ANSWER);
	}
	
	public String getAnswerNote() {
		return TEST_VALUES.getProperty(KEY_ANSWER_NOTE, "");
	}
	
	public String[] getQuestions() {
		return getIndexedValues(PREFIX_QUESTION);
	}
	
	public String[] getQuestions(String answerTypeName) {
		String key = PREFIX_ANSWER_TYPE_NAME + answerTypeName;
		String [] values = TEST_VALUES.getProperty(key, "").split(",");
		List<String> ret = new ArrayList<>();
		
		for (String it : values) {
			String swp = it;
			if (it.endsWith("n") || it.endsWith("N")) {
				swp = it.substring(0, it.length() - 1);
			}
			ret.add(TEST_VALUES.getProperty(PREFIX_QUESTION + swp));
		}
		
		return ret.stream().toArray(String[]::new);
	}
	
	public String[] getAnswerTypeNames(){
		List<String> values = new LinkedList<>();
		
		Enumeration<Object> enums = TEST_VALUES.keys();
		while (enums.hasMoreElements()) {
			String tmp = enums.nextElement().toString();
			if (tmp.startsWith(PREFIX_ANSWER_TYPE_NAME)) {
				values.add(tmp.substring(PREFIX_ANSWER_TYPE_NAME.length()));
			}
		}
		
		String [] ret = values.stream().sorted((s1, s2) -> s1.compareTo(s2)).toArray(String[]::new);
		return ret;
	}
	
	private int findAnswerTypeIndex(String name) {
		int i = 1;
		int ret = -1;
		
		while (ret == -1 && TEST_VALUES.containsKey(PREFIX_ANSWER_TYPE_NAME + i)) {
			if (TEST_VALUES.getProperty(PREFIX_ANSWER_TYPE_NAME + i).equals(name)) {
				ret = i;
			}
			i++;
		}
		
		return ret;
	}
	
	private String[] getIndexedValues(String prefix) {
		Map<String, String> ret_map = new HashMap<>();
		
		Enumeration<Object> enums = TEST_VALUES.keys();
		while (enums.hasMoreElements()) {
			String tmp = enums.nextElement().toString();
			if (tmp.startsWith(prefix)) {
				ret_map.put(tmp, TEST_VALUES.getProperty(tmp));
			}
		}
		
		String [] ret = new String[ret_map.size()];
		
		for (int i = 1; i <= ret.length; i++) {
			ret[i - 1] = ret_map.get(prefix + i);
		}
		
		return ret;
	}

}
