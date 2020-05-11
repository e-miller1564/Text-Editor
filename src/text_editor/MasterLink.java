package text_editor;

import java.io.Serializable;
import java.util.LinkedList;

public class MasterLink implements Serializable {
	
	private String keyword;
	private LinkedList<BabyLink> babyList;
	
	public MasterLink(String keyword) {
		this.keyword = keyword;
		babyList = new LinkedList<BabyLink>();
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public LinkedList<BabyLink> getBabyList() {
		return babyList;
	}

	public void setBabyList(LinkedList<BabyLink> babyList) {
		this.babyList = babyList;
	}

	public void display() {
		System.out.print(keyword + " ");
	}

}
