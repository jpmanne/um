package com.sciits.devops.poc.util;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class CreateFolder {

	public static boolean createDirectoryIfNotExist(String path) {
		File theDir = new File(path);

		// if the directory does not exist, create it
		if (!theDir.exists()) {
			boolean result = false;
			try {
				theDir.mkdirs();
				result = true;
			} catch (SecurityException se) {
				System.out.println("exception " + se.getMessage());
			}
			if (result) {
				System.out.println("DIR created");
			}
		}
		return false;
	}
	
	@SuppressWarnings("unused")
	public static String fileUpload(CommonsMultipartFile commonsMultipartFile, String path) {
		String filename = null;
		if (commonsMultipartFile != null) {
			if (!commonsMultipartFile.getOriginalFilename().equals("")) {
				try {
					boolean value = createDirectoryIfNotExist(path);
					filename = path + System.currentTimeMillis() + "_" +commonsMultipartFile.getOriginalFilename();
					commonsMultipartFile.transferTo(new File(filename));
					return filename;
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
		}
		return filename;
	}
}
