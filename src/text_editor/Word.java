package text_editor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Word {
	
	private String word;
	
	public Word(String word) {
		this.word = word;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	
	public int getNumOfSyllables() {
		String syllableRegex = "[bcdfghjklmnpqrstvwxzBCDFGHJKLMNPQRSTVWXZ]*[aeiouyAEIOUY]+[bcdfghjklmnpqrstvwxzBCDFGHJKLMNPQRSTVWXZ]*";
		Pattern syllablePattern = Pattern.compile(syllableRegex);
		Matcher syllableMatcher = syllablePattern.matcher(word);
		int count = 0;
		
		while(syllableMatcher.find()) {
			if(!(syllableMatcher.group().equals("e"))) {
				count++;
			}
		}
		return count;
	}
	
	public boolean isWord() {
		String wordRegex = "[a-zA-Z0-9']";
		Pattern wordPattern = Pattern.compile(wordRegex);
		Matcher wordMatcher = wordPattern.matcher(word);
		
		if(wordMatcher.find()) {
			return true;
		}
		else {
			return false;
		}
	}

}
