package text_editor;

import java.io.IOException;
import java.util.Hashtable;

public class Dictionary {
	
	private Hashtable<Integer, String> hashTable;
	private FileReader fileReader;
	
	public Dictionary(FileReader fileReader) throws IOException {
		this.fileReader = fileReader;
		hashTable = new Hashtable<Integer, String>();
		populateHashTable();
	}
	
	public void populateHashTable() throws IOException {
		String[] dictionaryWords = fileReader.wordSeparator();
		for(int i = 0; i < dictionaryWords.length; i++) {
			hashTable.put(dictionaryWords[i].hashCode(), dictionaryWords[i]);
		}
	}
	
	public String checkSpelling(String[] words) {
		StringBuffer listOfMisspelledWords = new StringBuffer();
		for(int i = 0; i < words.length; i++) {
			if(!(hashTable.containsKey(words[i].hashCode())) && !(words[i].equals(""))) {
				listOfMisspelledWords.append(words[i] + " is misspelled at position " + i + ".\n");
			}
		}
		return listOfMisspelledWords.toString();
	}

	public Hashtable<Integer, String> getHashTable() {
		return hashTable;
	}

	public void setHashTable(Hashtable<Integer, String> hashTable) {
		this.hashTable = hashTable;
	}

	public FileReader getFileReader() {
		return fileReader;
	}

	public void setFileReader(FileReader fileReader) {
		this.fileReader = fileReader;
	}

}
