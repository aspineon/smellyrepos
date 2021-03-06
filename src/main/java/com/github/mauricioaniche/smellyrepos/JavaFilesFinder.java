package com.github.mauricioaniche.smellyrepos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class JavaFilesFinder {

	private final String path;
	private Pattern pattern;

	public JavaFilesFinder(String path, String regex) {
		this.path = path;
		this.pattern = Pattern.compile(regex);
	}

	private void getAllDaoFilesIn(File aStartingDir, ArrayList<File> result) {
		try {
			File[] filesAndDirs = aStartingDir.listFiles();
			for (File file : filesAndDirs) {
				if (file.isDirectory()) {
					getAllDaoFilesIn(file, result);
				} else if (file.isFile() && isDao(file)) {
					result.add(file);
				}
			}
		} catch (Throwable e) {
			System.err.println("getAllDaoFiles error in " + aStartingDir.getPath());
		}
	}
	private void getAllProductionFilesIn(File aStartingDir, ArrayList<File> result) {
		try {
			File[] filesAndDirs = aStartingDir.listFiles();
			for (File file : filesAndDirs) {
				if (file.isDirectory()) {
					getAllProductionFilesIn(file, result);
				} else if (file.isFile() && !isDao(file) && isJava(file)) {
					result.add(file);
				}
			}
		} catch (Throwable e) {
			System.err.println("getAllProductionFiles error in " + aStartingDir.getPath());
		}
	}
	
	private boolean isJava(File file) {
		return file.getName().toLowerCase().endsWith("java");
	}

	private boolean isDao(File file) {
		return pattern.matcher(file.getName().toLowerCase()).matches();
	}

	public List<File> getAllDaoFiles() {
		ArrayList<File> result = new ArrayList<File>();
		getAllDaoFilesIn(new File(path), result);
		return result;
	}

	public List<File> getAllProductionFiles() {
		ArrayList<File> result = new ArrayList<File>();
		getAllProductionFilesIn(new File(path), result);
		return result;
	}

}
