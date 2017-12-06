package it.unisa.gps.teamcreator.server;


import java.util.ArrayList;
import java.util.List;

public class Category {
	
	private String keyword;
	private String uri;
	private List<String> categories;
	
	public Category(){
		categories = new ArrayList<String>();
	}
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public List<String> getCategories() {
		return categories;
	}
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	
	public void addCategory(String c){
		categories.add(c);
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	@Override
	public String toString() {
		return keyword+" | "+uri+" | "+categories;
	}

}
