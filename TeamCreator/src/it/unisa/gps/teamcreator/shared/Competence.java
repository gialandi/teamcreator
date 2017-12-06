package it.unisa.gps.teamcreator.shared;


import com.google.gwt.user.client.rpc.IsSerializable;

public class Competence implements IsSerializable{
	
	private String name;
	private Double rating;
	private String uri;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating / 1.0;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.getUri().equals(((Competence)obj).getUri());
	}
	
	@Override
	public String toString() {
		return "{uri : "+uri+" , name : "+name+" , rating: "+rating+" }";
	}

}
