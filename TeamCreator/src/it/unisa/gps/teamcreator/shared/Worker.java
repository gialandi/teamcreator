package it.unisa.gps.teamcreator.shared;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Worker implements IsSerializable {

	private String firstname;
	private String surname;
	private String uri;
	private String email;
	private String password;
	private List<Competence> competences;
	private int activeTask;
	private int numCoWorker;
	private boolean loggedIn;
	private String imagePath;
	private String description;
	
	private String sessionId;
	
	public Worker() {
		loggedIn = false;
		competences = new ArrayList<Competence>();
	}
	
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Competence> getCompetences() {
		return competences;
	}
	public void setCompetences(List<Competence> competences) {
		this.competences = competences;
	}
	public int getActiveTask() {
		return activeTask;
	}
	public void setActiveTask(int activeTask) {
		this.activeTask = activeTask;
	}
	public int getNumCoWorker() {
		return numCoWorker;
	}
	public void setNumCoWorker(int numCoWorker) {
		this.numCoWorker = numCoWorker;
	}
	
	public void addCompetence(Competence c){
		this.competences.add(c);
	}

	public String getSessionId() {
		return uri;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public boolean getLoggedIn() {
		return loggedIn;
	}
	
	public void setLoggedIn(boolean flag){
		loggedIn = flag;
	}
	
	@Override
	public boolean equals(Object obj) {
		Worker o = (Worker) obj;
		if ((this.getUri() == null)||(o == null)||(o.getUri() == null)) return false;
		return this.getUri().equals(o.getUri());
	}
	
	@Override
	public String toString() {
		return "{uri : "+uri+" , name : "+firstname+" "+surname+" , competences: "+competences+" }";
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

}
