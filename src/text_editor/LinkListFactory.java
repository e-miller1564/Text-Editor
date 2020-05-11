package text_editor;

import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;

public class LinkListFactory {
	
	private LinkedList<MasterLink> masterLinkedList;
	private FileReader fileReader;
	
	public LinkListFactory(FileReader fileReader) {
		masterLinkedList = new LinkedList<MasterLink>();
		this.fileReader = fileReader;
	}
	
	public void linkListCreator() throws IOException {
		String[] words = fileReader.wordSeparator();
		

		for(int i = 0; i < words.length; i++){
			ListIterator<MasterLink> masterListIterator = masterLinkedList.listIterator();
			MasterLink newMasterLink = null;
			while(masterListIterator.hasNext()) {
				newMasterLink = masterListIterator.next();
				if(newMasterLink.getKeyword().equalsIgnoreCase(words[i])) {
					break;
				}
				else {
					newMasterLink = null;
				}
			}
			if(newMasterLink == null) {
				newMasterLink = new MasterLink(words[i]);
				masterLinkedList.add(newMasterLink);
			}
			if(i != (words.length - 1)) {
				newMasterLink.getBabyList().add(new BabyLink(words[i + 1]));
			}
		}
	}
	
	public LinkedList<MasterLink> getMasterLinkedList() {
		return masterLinkedList;
	}

}
