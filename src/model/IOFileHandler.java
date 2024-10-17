package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class IOFileHandler {
	public static boolean saveIntoFile(String directory, String categoryName, String content) {
		
		String fileName = findAvailableFilename(directory, categoryName);
		String filePath = directory+"/"+fileName;
		
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);  
            System.out.println("Saved into " + filePath);
            return true;
        } catch (IOException e) {
            e.printStackTrace(); 
            return false;
        }
		
	}
	
	private static String findAvailableFilename(String directory, String categoryName) {
		int i = 1;
		String fileName = categoryName+"-"+i+".txt";
		
		while(fileExists(fileName, directory)) {
			i++;
			fileName = categoryName+"-"+i+".txt";
		}
		
		return fileName;
	}
	
	
	public static boolean fileExists(String name, String directory) {
		String filePath = directory+"/"+name;  // Change this to the file you want to check
        File file = new File(filePath);
        return (file.exists());
	}
}
