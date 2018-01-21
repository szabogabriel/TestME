package me.test.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class IOUtils {
	
	public static void cleanAndDeleteDir(File toClean) {
		if (toClean.isFile()) {
			toClean.delete();
		} else {
			for (File it : toClean.listFiles()) {
				cleanAndDeleteDir(it);
			}
			toClean.delete();
		}
	}
	
	public static List<String> readFilesByLine(File toRead) {
		List<String> ret = new LinkedList<>();
		
		if (toRead.exists()) {
			try (BufferedReader in = new BufferedReader(new FileReader(toRead))) {
				String line;
				while ((line = in.readLine()) != null) {
					ret.add(line);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	public static void writeFile(File toFile, List<String> content) {
		writeFile(toFile, content, false);
	}
	
	public static void writeFile(File toFile, List<String> content, boolean append) {
		writeFile(toFile, content.stream().collect(Collectors.joining(System.lineSeparator())), append);
	}
	
	public static void writeFile(File toFile, String content) {
		writeFile(toFile, content, false);
	}
	
	public static void writeFile(File toFile, String content, boolean append) {
		try (BufferedWriter out = new BufferedWriter(new FileWriter(toFile, append))) {
			if (append) {
				out.write(System.lineSeparator());
			}
			out.write(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
