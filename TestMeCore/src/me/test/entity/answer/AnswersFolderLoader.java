package me.test.entity.answer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import me.test.entity.test.AnswerDescription;
import me.test.entity.test.Question;
import me.test.entity.test.Test;
import me.test.entity.test.TestsEntity;
import me.test.tools.Gender;

public class AnswersFolderLoader implements AnswersLoader {
	
	private static final String KEY_USERNAME = "username";
	private static final String KEY_AGE = "age";
	private static final String KEY_GENDER = "gender";
	private static final String KEY_TIMESTAMP = "timestamp";
	private static final String KEY_PREFIX_ANSWER = "answer.";
	private static final String KEY_DESCRIPTION = "description";
	
	private static final Pattern KEY_SPLITTER_REGEX = Pattern.compile("answer.([0-9]+)");
	
	private final File FOLDER;
	
	public AnswersFolderLoader(File folder) {
		this.FOLDER = folder;
	}

	@Override
	public Map<String, List<Answer>> load(TestsEntity tests) {
		Map<String, List<Answer>> ret = new HashMap<>();
		
		Arrays.asList(FOLDER.listFiles()).stream()
			.filter(f -> f.isDirectory() && f.exists())
			.forEach(f -> ret.put(
					f.getName(), 
					loadAnswersForTest(
							f, 
							tests.getTestByName(
									f.getName().substring(
											0, 
											f.getName().length() - ".properties".length()
									)
							)
					)
			));
		
		return ret;
	}
	
	@Override
	public List<Answer> load(Test test) {
		return loadAnswersForTest(new File(FOLDER.getAbsoluteFile() + File.separator + test.getName()), test);
	}
	
	private List<Answer> loadAnswersForTest(File folder, Test test) {
		List<Answer> ret = new LinkedList<>();
		
		if (folder != null && folder.isDirectory()) {
			Arrays.asList(folder.listFiles()).stream()
				.filter(f -> f.isFile() && f.getName().endsWith(".properties"))
				.forEach(f -> ret.add(loadAnswer(f, test)));
		}
		
		return ret;
	}
	
	private Answer loadAnswer(File f, Test test) {
		Answer ret = new Answer();
		Properties prop = new Properties();
		
		try (InputStream in = new FileInputStream(f)) {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ret.setUser(prop.getProperty(KEY_USERNAME));
		try { ret.setAge(Integer.parseInt(prop.getProperty(KEY_AGE))); } catch (Exception e) { e.printStackTrace(); }
		try { ret.setGender(Gender.getGender(prop.getProperty(KEY_GENDER))); } catch (Exception e) { e.printStackTrace(); }
		try { ret.setTimestamp(Long.parseLong(prop.getProperty(KEY_TIMESTAMP))); } catch (Exception e) { e.printStackTrace(); }
		ret.setDescription(prop.getProperty(KEY_DESCRIPTION, ""));
		ret.setTest(test);
		
		List<String> keys = prop.keySet().stream()
			.filter(key -> key.toString().startsWith(KEY_PREFIX_ANSWER))
			.map(key -> key.toString())
			.collect(Collectors.toList()
		);
		
		for (String it : keys) {
			Question q = null;
			try { q = test.getQuestions()[getKeyNumber(it)]; } catch (Exception e) { q = null; }
			AnswerDescription ad = findAnswerDescription(test, prop.getProperty(it));
			
			if (q != null && ad != null) {
				ret.addAnswer(q, ad);
			}
		}
		
		
		return ret;
	}
	
	private AnswerDescription findAnswerDescription(Test test, String text) {
		AnswerDescription ret = Arrays.asList(test.getAnswerDescriptions()).stream()
				.filter(ad -> ad.getDescription().equals(text))
				.findAny()
				.orElse(null);
		
		return ret;
	}
	
	private int getKeyNumber(String key) {
		Matcher matcher = KEY_SPLITTER_REGEX.matcher(key);
		int ret = -1;
		
		if (matcher.matches()) {
			ret = Integer.parseInt(matcher.group(1));
		}
		
		return ret;
	}

	@Override
	public void save(Answer answer) {
		Properties prop = new Properties();
		prop.put(KEY_AGE, answer.getAge() + "");
		prop.put(KEY_GENDER, answer.getGender().toString());
		prop.put(KEY_TIMESTAMP, answer.getTimestamp() + "");
		prop.put(KEY_USERNAME, answer.getUser());
		prop.put(KEY_DESCRIPTION, answer.getDescription());
		
		Question [] questions = answer.getTest().getQuestions();
		
		for (int i = 0; i < questions.length; i++) {
			String key = KEY_PREFIX_ANSWER + i;
			AnswerDescription answerValue = answer.getAnswer(questions[i]);
			if (answerValue != null) {
				prop.put(key, answerValue.getDescription());
			}
		}
		
		File dir = new File(FOLDER.getAbsoluteFile() + File.separator + answer.getTest().getName());
		dir.mkdirs();
		File targetFile = new File(dir.getAbsolutePath() + File.separator + answer.getTimestamp() + ".properties");
		
		
		try (OutputStream out = new FileOutputStream(targetFile)){
			prop.store(out, "");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
