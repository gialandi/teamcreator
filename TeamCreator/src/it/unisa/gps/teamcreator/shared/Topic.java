package it.unisa.gps.teamcreator.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Topic implements IsSerializable{
	
	private int id;
	private String title;
	private double weight;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "{"+id+" | "+title+" | "+weight+"}";
	}
}
