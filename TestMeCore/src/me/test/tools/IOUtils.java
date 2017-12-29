package me.test.tools;

import java.io.File;

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

}
