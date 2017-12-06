package it.unisa.gps.teamcreator.shared;

import java.util.List;

public class ListCompetenceWithNumber {

	private String  nameCompetence;
	private List<Worker> listWorker;
	private int numberWorkerForSkillSelect;

	private int indexNextWorker = 0;
	
	

	public String getNameCompetence() {
		return nameCompetence;
	}

	public void setNameCompetence(String nameCompetence) {
		this.nameCompetence = nameCompetence;
	}

	public List<Worker> getListWorker() {
		return listWorker;
	}

	public void setListWorker(List<Worker> listWorker) {
		this.listWorker = listWorker;
	}

	public int getNumberWorkerForSkillSelect() {
		indexNextWorker = numberWorkerForSkillSelect;
		return numberWorkerForSkillSelect;
	}

	public void setNumberWorkerForSkillSelect(int numberWorkerForSkillSelect) {
		this.numberWorkerForSkillSelect = numberWorkerForSkillSelect;
	}
	
	public Worker getNextWorker (String idWorkerRemove){
		Worker w = listWorker.get(indexNextWorker);
		indexNextWorker++;
		for (int i=0;i<listWorker.size();i++){
			if (idWorkerRemove.equals(listWorker.get(i).getUri())){
				listWorker.remove(i);
				break;
			}
		}
		
		return w;
	}
	
	public Worker getWorker (String idUri){
		
		for (int i=0;i<listWorker.size();i++){
			if (idUri.equals(listWorker.get(i).getUri())){
				return listWorker.get(i);
				
			}
		
	}
		return null;
	}
}
