package me.test.entity.answer;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AnswersFolderLoader implements AnswersLoader {
	
	private final File FOLDER;
	
	public AnswersFolderLoader(File folder) {
		this.FOLDER = folder;
	}

	@Override
	public Map<String, Set<Answer>> load() {
		Map<String, Set<Answer>> ret = new HashMap<>();
		
		Arrays.asList(FOLDER.listFiles()).stream()
			.filter(f -> f.isDirectory() && f.exists())
			.forEach(f -> ret.put(f.getName(), loadAnswersForTest(f)));
		
		return ret;
	}
	
	@Override
	public Set<Answer> load(String test) {
		return loadAnswersForTest(new File(FOLDER.getAbsoluteFile() + File.separator + test));
	}
	
	private Set<Answer> loadAnswersForTest(File folder) {
		Set<Answer> ret = new HashSet<>();
		
		if (folder != null) {
			Arrays.asList(folder.listFiles()).stream()
				.filter(f -> f.isFile() && f.getName().endsWith(".properties"))
				.forEach(f -> ret.add(loadAnswer(f)));
		}
		
		return ret;
	}
	
	private Answer loadAnswer(File f) {
		Answer ret = new Answer();
		
		//TODO
		
		
		return ret;
	}

	@Override
	public void save(String test, Answer answer) {
		// TODO Auto-generated method stub
		
	}

}
