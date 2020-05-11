package text_editor;

import java.util.LinkedList;
import java.util.ListIterator;

public class ParagraphFactory {
	
	private int numOfWords;
	private String firstWord;
	private LinkedList<MasterLink> masterLinkedList;
	
	public ParagraphFactory(int numOfWords, String firstWord, LinkedList<MasterLink> masterLinkedList) {
		this.numOfWords = numOfWords;
		this.firstWord = firstWord;
		this.masterLinkedList = masterLinkedList;
	}
	
	public String generateParagraph() {
		StringBuilder stringBuilder = new StringBuilder();
		String currentWord = firstWord;
		int randomBabyLink;
		for(int i = 0; i < numOfWords; i++) {
			MasterLink masterLink = null;
			ListIterator<MasterLink> masterListIterator = masterLinkedList.listIterator();
			while(masterListIterator.hasNext()) {
				masterLink = masterListIterator.next();
				if(masterLink.getKeyword().equalsIgnoreCase(currentWord)) {
					break;
				}
			}
			
			if(masterLink != null) {
				ListIterator<BabyLink> babyListIterator = masterLink.getBabyList().listIterator();
				randomBabyLink = (int) (Math.random() * masterLink.getBabyList().size());
				stringBuilder.append(masterLink.getKeyword() + " ");
				for(int j = 0; j <= randomBabyLink; j++) {
					currentWord = babyListIterator.next().getNextWord();
				}
			}
		}
		
		return stringBuilder.toString().trim();
	}
}
