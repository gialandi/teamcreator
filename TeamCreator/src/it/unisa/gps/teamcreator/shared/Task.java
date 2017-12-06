package it.unisa.gps.teamcreator.shared;

import java.io.Serializable;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Task implements IsSerializable {
	

	private String uri;
	private String nameTask;
	private String projectManager;
	private String teamMembers;
	private String description;
	private List<String> listSkills;
	private List<Worker> listWorkers;
	
	
	

	public String getNameTask() {
		return nameTask;
	}

	public void setNameTask(String nameTask) {
		this.nameTask = nameTask;
	}

	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

	public String getTeamMembers() {
		return teamMembers;
	}

	public void setTeamMembers(String teamMembers) {
		this.teamMembers = teamMembers;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getListSkills() {
		return listSkills;
	}

	public void setListSkills(List<String> listSkills) {
		this.listSkills = listSkills;
	}

	public List<Worker> getListWorkers() {
		return listWorkers;
	}

	public void setListWorkers(List<Worker> listWorkers) {
		this.listWorkers = listWorkers;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	@Override
	public String toString() {
		return "{uri :"+getUri()+" ; name: "+getNameTask()+"; workers: "+getListSkills()+"}";
	}
	
	
 }
