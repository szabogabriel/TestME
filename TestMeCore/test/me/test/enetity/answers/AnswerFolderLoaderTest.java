package me.test.enetity.answers;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import me.test.entity.BasicTestClass;
import me.test.entity.answer.Answer;
import me.test.entity.test.AnswerDescription;
import me.test.entity.test.AnswerType;
import me.test.entity.test.Question;

public class AnswerFolderLoaderTest extends BasicTestClass {
	
	private static final Answer ANSWER = new Answer();
	private static final me.test.entity.test.Test TEST = new me.test.entity.test.Test();
	
	@BeforeClass
	public static void prepareClass() {
		prepareAnswersFolder();
		
		Question q1 = new Question(0, "Question1", false);
		Question q2 = new Question(1, "Question2", true);
		Question q3 = new Question(2, "Question3", false);
		Question[] questions = new Question[]{q1, q2, q3};
		
		List<Question> answerType1 = new ArrayList<>();
		answerType1.add(q1);
		answerType1.add(q2);
		
		List<Question> answerType2 = new ArrayList<>();
		answerType2.add(q3);
		
		AnswerDescription ad1 = new AnswerDescription(1, "Desc1");
		AnswerDescription ad2 = new AnswerDescription(2, "Desc2");
		
		TEST.setActive(true);
		TEST.setQuestions(questions);
		TEST.setAnswerDescriptions(new AnswerDescription[]{ad1, ad2});
		TEST.setAnswerNote("Answer note");
		TEST.setAnswerTypes(new AnswerType[]{
				new AnswerType("AnswerType1", answerType1, "Answer type number One", 2.0),
				new AnswerType("AnswerType2", answerType2, "Answer type number Two", 2.5)
				});
		TEST.setInstructions("Instructions");
		TEST.setName("Test Name");
		TEST.setTitle("Test title");
		
		ANSWER.setAge(23);
		ANSWER.setTimestamp(System.currentTimeMillis());
		ANSWER.setUser("Username");
		ANSWER.setTest(TEST);
		
		ANSWER.addAnswer(q1, ad1);
		ANSWER.addAnswer(q2, ad2);
		ANSWER.addAnswer(q3, ad1);
	}
	
	@Test
	public void persistTest() {
		ENTITY_PROVIDER.getAnswersEntity().save(ANSWER);
		
		File tmp = new File(TARGET_DIR_ANSWERS.getAbsolutePath() + "/" + ANSWER.getTest().getName() + "/" + ANSWER.getTimestamp() + ".properties");
		
		assertTrue(tmp.exists());
	}
	
	@AfterClass
	public static void destroyClass() {
		cleanup();
	}

}
