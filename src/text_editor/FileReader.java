package text_editor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileReader {
	
	File file;
	
	public FileReader(File file) {
		this.file = file;
	}
	
	public String[] wordSeparator() throws IOException {
		StringBuffer wordBuffer = new StringBuffer();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		while(bufferedReader.ready()) {
			wordBuffer.append(bufferedReader.readLine() + " ");
		}
		String words = wordBuffer.toString();
		bufferedReader.close();
		String[] wordsSeparated = words.split("\\s+");
		return wordsSeparated;
	}
	
	public String readFile() throws IOException {
		StringBuffer wordBuffer = new StringBuffer();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		while(bufferedReader.ready()) {
			wordBuffer.append(bufferedReader.readLine() + "\n");
		}
		String words = wordBuffer.toString();
		bufferedReader.close();
		return words;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
}