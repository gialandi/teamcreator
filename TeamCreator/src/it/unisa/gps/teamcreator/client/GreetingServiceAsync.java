package it.unisa.gps.teamcreator.client;

import it.unisa.gps.teamcreator.shared.Competence;
import it.unisa.gps.teamcreator.shared.Task;
import it.unisa.gps.teamcreator.shared.Topic;
import it.unisa.gps.teamcreator.shared.Worker;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void login(String user, String password, AsyncCallback<Worker> callback);

	void getTopics(String text, AsyncCallback<List<Topic>> callback);
	
	void getWorkersRecommended(List<String> listSkills, AsyncCallback<List<Worker>> callback);

	void registerNewUser(Worker worker, AsyncCallback<Boolean> callback);

	void getWorkersWithCompetence(String competence,
			AsyncCallback<List<Worker>> callback);

	void getWorkersWithCompetences(List<String> competencesUri,
			List<Integer> num_workers, List<List<String>> workersUri_forbidden,
			AsyncCallback<Map<String, List<Worker>>> callback);

	void addCompetence(String workerUri, String competenceName, Double rating,
			AsyncCallback<String> callback);

	void createTask(Task task, AsyncCallback<String> callback);

	void getWorkersOfTask(String taskUri, AsyncCallback<List<Worker>> callback);

	void getCompetenceOfWorker(String workerUri,
			AsyncCallback<List<Competence>> callback);

	void getTasksOfWorker(String workerUri, AsyncCallback<List<Task>> callback);

	void getCoworkersOf(String workerUri, AsyncCallback<List<Worker>> callback);

	void releaseFeedback(String workerUri, List<Competence> competences,
			AsyncCallback<Void> callback);
}
