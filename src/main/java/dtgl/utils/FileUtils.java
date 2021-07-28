package dtgl.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {
	
	public static String loadFile(String path) {
		String fileContent = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
			fileContent = reader.lines().reduce((acc, curr)->acc+"\n"+curr).orElse(null);
			reader.close();
		} catch (IOException e) {
			System.out.println("cannot load file "+path+" !");
			System.out.println(e.getMessage());
		}
		return fileContent;
	}

}
