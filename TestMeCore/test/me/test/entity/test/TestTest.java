package me.test.entity.test;

import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestTest {
	
	private static me.test.entity.test.Test test;
	
	private static File folder = new File("./test/me/test/entity/test");
	
	private static String testName = "probatest2";
	private static File file = new File(folder.getAbsolutePath() + "/" + testName +  ".properties");
	
	@BeforeClass
	public static void init() throws FileNotFoundException, IOException {
		try (BufferedWriter out = new BufferedWriter(new FileWriter(file))) {
			StringBuilder sb = new StringBuilder();
			sb
				.append("title=Title of the test\n")

				.append("instructions=Test Instructions\n")

				.append("answer.1=Answer1\n")
				.append("answer.2=Answer2\n")
				.append("answer.3=Answer3\n")

				.append("answerNote=Answer note\n")

				.append("question.1=Question 1\n")
				.append("question.2=Question 2\n")
				.append("question.3=Question 3\n")
				.append("question.4=Question 4\n")
				.append("question.5=Question 5\n")

				.append("answerType.name.1=One\n")
				.append("answerType.name.2=Two\n")
				
				.append("answerType.questions.1=2n,5\n")
				.append("answerType.questions.2=1,3,4\n")

				.append("answerType.description.1=DescriptionOne\n")
				.append("answerType.description.2=DescriptionTwo\n")
				;
			
			out.write(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TestsFolderLoader loader = new TestsFolderLoader(folder);
		
		test = loader.load(testName);
	}
	
	@Test
	public void testTitle() {
		String swp = test.getTitle();
		
		assertEquals("Title of the test", swp);
	}
	
	@Test
	public void testInstructions() {
		String swp = test.getInstructions();
		
		assertEquals("Test Instructions", swp);
	}
	
	@Test
	public void testAnswerTypes() {
		AnswerType [] answers = test.getAnswerTypes();
		
		assertEquals(answers.length, 2);
		
		assertEquals(answers[0].getName(), "One");
		assertEquals(answers[1].getName(), "Two");
		
		assertEquals(answers[0].getDescription(), "DescriptionOne");
		assertEquals(answers[1].getDescription(), "DescriptionTwo");
	}
	
	@Test
	public void testAnswerValues() {
		AnswerDescription [] answerValues = test.getAnswerDescriptions();
		
		assertEquals(answerValues.length, 3);
		
		assertEquals(answerValues[0].getDescription(), "Answer1");
		assertEquals(answerValues[1].getDescription(), "Answer2");
		assertEquals(answerValues[2].getDescription(), "Answer3");
	}
	
	@Test
	public void testAnswerNote() {
		String swp = test.getAnswerNote();
		
		assertEquals(swp, "Answer note");
	}
	
	@Test
	public void testQuestions() {
		Question [] swp = test.getQuestions();
		
		assertEquals(swp.length, 5);
		
		assertEquals(swp[0].getText(), "Question 1");
		assertEquals(swp[1].getText(), "Question 2");
		assertEquals(swp[2].getText(), "Question 3");
		assertEquals(swp[3].getText(), "Question 4");
		assertEquals(swp[4].getText(), "Question 5");
	}
	
	@Test
	public void testSpecificQuestions() {
		Question [] swp = test.getQuestions("One");
		
		assertEquals(swp.length, 2);
		assertEquals(swp[0].getText(), "Question 2");
		assertEquals(swp[1].getText(), "Question 5");
		
		swp = test.getQuestions("Two");
		
		assertEquals(swp.length, 3);
		assertEquals(swp[0].getText(), "Question 1");
		assertEquals(swp[1].getText(), "Question 3");
		assertEquals(swp[2].getText(), "Question 4");
	}
	
	@AfterClass
	public static void destroy() {
		file.delete();
	}
}
