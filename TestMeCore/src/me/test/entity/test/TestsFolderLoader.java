package me.test.entity.test;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import me.test.tools.IOUtils;

public class TestsFolderLoader implements TestsLoader {
	
	private static final String KEY_TITLE = "title";
	private static final String KEY_INSTRUCTIONS = "instructions";
	private static final String KEY_ANSWER_NOTE = "answerNote";
	private static final String PREFIX_ANSWER = "answer.";
	private static final String PREFIX_ANSWER_TYPE_NAME = "answerType.name.";
	private static final String PREFIX_ANSWER_TYPE_QUESTIONS = "answerType.questions.";
	private static final String PREFIX_ANSWER_TYPE_DESCRIPTION = "answerType.description.";
	private static final String PREFIX_ANSWER_TYPE_LIMIT = "answerType.limit.";
	private static final String PREFIX_QUESTION = "question.";
	
	private static final Pattern PATTERN_ORDER = Pattern.compile("([0-9]+)(n?)");
	
	private final File ROOT_FOLDER;
	
	private final File ACTIVE_FILE;
	
	public TestsFolderLoader(File rootFolder) {
		ROOT_FOLDER = rootFolder;
		ACTIVE_FILE = new File(ROOT_FOLDER.getAbsolutePath() + File.separator + ".active");
	}

	@Override
	public List<Test> load() {
		List<Test> ret = new LinkedList<>();
		
		if (ROOT_FOLDER != null && ROOT_FOLDER.isDirectory()) {
			Arrays.asList(ROOT_FOLDER.listFiles()).stream()
				.filter(f -> f.isFile() && f.getName().endsWith(".properties"))
				.forEach(f -> ret.add(load(f)));
		}
		
		return ret;
	}
	
	public Test load(String name) {
		String nameToLoad = name + ".properties";
		return load(new File(ROOT_FOLDER.getAbsolutePath() + File.separator + nameToLoad));
	}
	
	private Test load(File file) {
		Test ret = new Test();
		Properties TEST_VALUES = new Properties();
		
		if (file != null && file.exists()) {
			try (FileInputStream fis = new FileInputStream(file)){
				TEST_VALUES.load(fis);
				
				ret.setName(file.getName().substring(0, file.getName().lastIndexOf(".")));
				
				ret.setTitle(TEST_VALUES.getProperty(KEY_TITLE, ""));
				
				ret.setAnswerNote(TEST_VALUES.getProperty(KEY_ANSWER_NOTE, ""));
				
				ret.setInstructions(TEST_VALUES.getProperty(KEY_INSTRUCTIONS, ""));
				
				ret.setAnswerDescriptions(getAnswerDescriptions(TEST_VALUES));
				
				ret.setQuestions(getQuestions(TEST_VALUES));
				
				ret.setAnswerTypes(getAnswerTypes(TEST_VALUES, ret.getQuestions()));
				
				ret.setActive(isActive(ret));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	private boolean isActive(Test test) {
		boolean ret = !IOUtils.readFilesByLine(ACTIVE_FILE).stream().noneMatch(l -> l.trim().equals(test.getName()));
		return ret;
	}
	
	private boolean isNegativeQuestion(Properties TEST_VALUES, int id) {
		String [] values = getIndexedValues(TEST_VALUES, PREFIX_ANSWER_TYPE_QUESTIONS);
		
		String toLookFor = id + "n";
		
		for (String it1 : values) {
			String [] tmp = it1.split(",");
			for (String it2 : tmp) {
				if (it2.equals(toLookFor)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private Question[] getQuestions(Properties TEST_VALUES) {
		String [] questions = getIndexedValues(TEST_VALUES, PREFIX_QUESTION);
		Question[] ret = new Question[questions.length];
		
		for (int i = 0; i < questions.length; i++) {
			ret[i] = new Question(i, questions[i], isNegativeQuestion(TEST_VALUES, i + 1));
		}
		
		return ret;
	}
	
	private AnswerDescription[] getAnswerDescriptions(Properties TEST_VALUES) {
		String [] values = getIndexedValues(TEST_VALUES, PREFIX_ANSWER);
		AnswerDescription [] ret = new AnswerDescription[values.length];
		
		for (int i = 0; i < ret.length; i++) {
			ret[i] = new AnswerDescription(getAnswerOrder(TEST_VALUES, values[i]), values[i]);
		}
		
		return ret;
	}
	
	private int getAnswerOrder(Properties props, String answerValue) {
		int i = 0;
		
		String key;
		
		if (answerValue == null) {
			return -1;
		}
		
		do {
			i++;
			key = PREFIX_ANSWER + i;
			if (answerValue.equals(props.getProperty(key))) {
				return i;
			}
		} while (props.containsKey(key));
		
		return -1;
	}
	
	private AnswerType[] getAnswerTypes(Properties TEST_VALUES, Question[] questions) {
		String[] names = getIndexedValues(TEST_VALUES, PREFIX_ANSWER_TYPE_NAME);
		String[] quess = getIndexedValues(TEST_VALUES, PREFIX_ANSWER_TYPE_QUESTIONS);
		String[] descs = getIndexedValues(TEST_VALUES, PREFIX_ANSWER_TYPE_DESCRIPTION);
		String[] limits = getIndexedValues(TEST_VALUES, PREFIX_ANSWER_TYPE_LIMIT);
		
		AnswerType[] ret = new AnswerType[names.length];
		
		for (int i = 0; i < ret.length; i++) {
			ret[i] = new AnswerType(names[i], getQuestions(questions, quess[i]), descs[i], Double.parseDouble(limits[i]));
		}
		
		return ret;
	}
	
	private List<Question> getQuestions(Question[] questions, String list) {
		List<Question> ret = new LinkedList<>();
		
		if (list != null) {
			String [] values = list.split(",");
			for (String it : values) {
				Matcher m = PATTERN_ORDER.matcher(it);
				if (m.matches()) {
					Question q = questions[Integer.parseInt(m.group(1)) - 1];
					ret.add(q);
				}
			}
			
		}
		
		return ret;
	}
	
	private String[] getIndexedValues(Properties TEST_VALUES, String prefix) {
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
	
	@Override
	public void persist(Test test) {
		// TODO save test
		
		List<String> activeFileContent = IOUtils.readFilesByLine(ACTIVE_FILE);
		boolean contains = false;
		for (String line : activeFileContent) { 
			if (line.trim().equals(test.getName())) {
				contains = true; 
			}
		}
		
		if (contains && !test.isActive()) {
			IOUtils.writeFile(ACTIVE_FILE, activeFileContent.stream().filter(l -> !l.trim().equals(test.getName())).collect(Collectors.toList()));
		} else
		if (!contains && test.isActive()) {
			activeFileContent.add(test.getName());
			IOUtils.writeFile(ACTIVE_FILE, activeFileContent);
		}
	}

	@Override
	public void persist(List<Test> tests) {
		tests.stream().forEach(t -> persist(t));
	}

}
