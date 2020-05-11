package text_editor;

import java.io.Serializable;

public class BabyLink implements Serializable {
	
	private String nextWord;
	
	public BabyLink(String nextWord) {
		this.nextWord = nextWord;
	}

	public String getNextWord() {
		return nextWord;
	}

	public void setNextWord(String nextWord) {
		this.nextWord = nextWord;
	}
}
