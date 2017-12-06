package it.unisa.gps.teamcreator.client;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import it.unisa.gps.teamcreator.shared.Competence;
import it.unisa.gps.teamcreator.shared.Task;
import it.unisa.gps.teamcreator.shared.Topic;
import it.unisa.gps.teamcreator.shared.Worker;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;

	Worker login(String user, String password);
	
	/**
	 * 
	 * @param text
	 * @return la lista dei topic del testo passato. Powered by WikipediaMiner
	 */
	List<Topic> getTopics(String text);

	List<Worker> getWorkersRecommended(List<String> listSkills);
	boolean registerNewUser(Worker worker);
	
	List<Worker> getWorkersWithCompetence(String competence);
	
	Map<String, List<Worker>> getWorkersWithCompetences(List<String> competences, List<Integer> num_workers, List<List<String>> workersUri_forbidden);
	
	String addCompetence(String workerUri, String competenceName, Double rating);
	
	/**
	 * 
	 * @param task completo bello di tutt cos tranne l'uri ovviamente
	 * @return uri del task creato
	 */
	String createTask(Task task);
	
	List<Worker> getWorkersOfTask(String taskUri);
	List<Task> getTasksOfWorker(String workerUri);
	List<Competence> getCompetenceOfWorker(String workerUri);
	List<Worker> getCoworkersOf(String workerUri);
	void releaseFeedback(String workerUri, List<Competence> competences);
	
	

}
