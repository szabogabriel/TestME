package me.test.entity.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import me.test.tools.IOUtils;

public class TestEntityTest {
	
	private static final String TEST_NAME = "probatest";
	
	private static File targetDir = new File("./test/me/test/entity/test");
	private static File targetFile = new File(targetDir.getAbsolutePath() + "/" + TEST_NAME + ".properties");
	private static File activeFile = new File(targetDir.getAbsolutePath() + "/.active");
	
	@BeforeClass
	public static void init() throws FileNotFoundException, IOException {
		try (BufferedWriter out = new BufferedWriter(new FileWriter(targetFile))) {
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
	}
	
	@Test
	public void testTestsEntityLoad() {
		TestsEntity te = new TestsEntity(targetDir);
		
		assertEquals(te.getTests().length, 1);
		assertTrue(te.isKnownTest(TEST_NAME));
		assertFalse(te.isActiveTest(TEST_NAME));
	}
	
	@Test
	public void testTestsEntityActivateDeactivate() {
		TestsEntity te = new TestsEntity(targetDir);
		
		te.activate(te.getTestByName(TEST_NAME));
		
		assertTrue(activeFile.exists());
		assertNotEquals(Long.valueOf(0L), Long.valueOf(activeFile.length()));
		assertTrue(te.isActiveTest(TEST_NAME));
		assertEquals(te.getActiveTests().length, 1);
		
		te.deactivate(te.getTestByName(TEST_NAME));
		
		assertFalse(te.isActiveTest(TEST_NAME));
		assertNull(IOUtils.readFilesByLine(activeFile).stream().map(f -> te.getTestByFileName(f)).filter(t -> t.getName().equals(TEST_NAME)).findAny().orElse(null));
	}
	
	@AfterClass
	public static void cleanup() {
		targetFile.delete();
	}

}
