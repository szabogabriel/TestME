package me.test.entity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Properties;

import me.test.tools.IOUtils;
import me.test.usecase.UseCaseProvider;

public class BasicTestClass implements EntityProviderInitData {
	
	public static final File TARGET_DIR_USERS = new File("./test/me/test/entity/users");
	public static final File TARGET_DIR_TESTS = new File("./test/me/test/entity/tests");
	
	public static final String USER1_NAME = "admin";
	public static final String USER1_SALT = "Keps4";
	public static final String USER1_HASH = "2ec58c88ad5b267c6a00175161cb6c4b";
	public static final String USER1_PSWD = "admin";
	
	public static final EntityProvider ENTITY_PROVIDER = new EntityProvider(new BasicTestClass());;
	public static final UseCaseProvider USE_CASE_PROVIDER = new UseCaseProvider(ENTITY_PROVIDER);
	
	protected static void prepareUsersFolder() {
		if (!TARGET_DIR_USERS.exists()) {
			TARGET_DIR_USERS.mkdirs();
		}
		
		Properties p = new Properties();
		p.put("username", USER1_NAME);
		p.put("salt", USER1_SALT);
		p.put("passwordHash", USER1_HASH);
		try (FileOutputStream fos = new FileOutputStream(new File(TARGET_DIR_USERS.getAbsolutePath() + "/admin.properties"))) {
			p.store(fos, "");
		} catch (Exception e) {
			
		}
	}
	
	protected static void prepareTestsFolder() {
		if (!TARGET_DIR_TESTS.exists()) {
			TARGET_DIR_TESTS.mkdirs();
		}
		try (BufferedWriter out = new BufferedWriter(new FileWriter(new File(TARGET_DIR_TESTS.getAbsolutePath() + "/test1.properties")))) {
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
	
	protected static void cleanup() {
		if (TARGET_DIR_USERS.exists()) {
			IOUtils.cleanAndDeleteDir(TARGET_DIR_USERS);
		}
		if (TARGET_DIR_TESTS.exists()) {
			IOUtils.cleanAndDeleteDir(TARGET_DIR_TESTS);
		}
	}

	@Override
	public File getTestsFolder() {
		return TARGET_DIR_TESTS;
	}

	@Override
	public File getUsersFolder() {
		return TARGET_DIR_USERS;
	}
	
	

}
