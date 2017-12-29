package me.test.tools;

import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

public class TestIOUtils {
	
	public static final File TARGET_DIR = new File("./test/me/test/tools");
	
	@Test
	public void testCleanAndDeleteDir() throws IOException {
		File d1 = new File(TARGET_DIR.getAbsolutePath() + "/d1");
		File d11 = new File(d1.getAbsolutePath() + "/d11");
		File d12 = new File(d1.getAbsolutePath() + "/d12");
		File d121 = new File(d12.getAbsolutePath() + "/d121");
		
		d1.mkdir();
		d11.mkdir();
		d12.mkdir();
		d121.mkdir();
		
		String dataToBeWritten = "the quick brown fox jumps over the lazy dog";
		
		File f121 = new File(d121.getAbsolutePath() + "/f121.txt");
		File f11 = new File(d11.getAbsolutePath() + "/f11.txt");
		File f1 = new File(d1.getAbsolutePath() + "/f1.txt");
		
		FileOutputStream out = new FileOutputStream(f121);
		out.write(dataToBeWritten.getBytes());
		out.close();
		
		out = new FileOutputStream(f11);
		out.write(dataToBeWritten.getBytes());
		out.close();
		
		out = new FileOutputStream(f1);
		out.write(dataToBeWritten.getBytes());
		out.close();
		
		IOUtils.cleanAndDeleteDir(d1);
		
		assertFalse(d1.exists());
	}

}
