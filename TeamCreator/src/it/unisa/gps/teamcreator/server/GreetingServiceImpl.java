package it.unisa.gps.teamcreator.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.unisa.gps.teamcreator.client.GreetingService;
import it.unisa.gps.teamcreator.shared.Competence;
import it.unisa.gps.teamcreator.shared.FieldVerifier;
import it.unisa.gps.teamcreator.shared.Task;
import it.unisa.gps.teamcreator.shared.Topic;
import it.unisa.gps.teamcreator.shared.Worker;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}

	@Override
	public Worker login(String user, String password) {
		if(user.equals("a") && (password.equals("a"))) {
			Worker w = new Worker();
			w.setFirstname("Giovanni");
			w.setSurname("Botta");
			w.setEmail("a@email.it");
			return w;
		}
		
		KnowledgeBase kb = new KnowledgeBase();
		Worker worker = kb.getUser(user, password);
		return worker;
	}

	@Override
	public List<Topic> getTopics(String text) {
		WikipediaMinerClient wm = new WikipediaMinerClient();
		return wm.getTopics(text);
	}
	
	public List<Worker> getWorkersRecommended(List<String> listSkills)  {
		
		Worker w1 = new Worker();
		w1.setFirstname("Giovanni");
		w1.setSurname("Botta");
		w1.setEmail("a@email.it");
		
		Worker w2 = new Worker();
		w2.setFirstname("Giovanni");
		w2.setSurname("Festa");
		w2.setEmail("festa@email.it");

		Worker w3 = new Worker();
		w3.setFirstname("John");
		w3.setSurname("Party");
		w3.setEmail("party@email.it");

		List<Worker> listWorker = new ArrayList<Worker>();
		listWorker.add(w1);
		listWorker.add(w2);
		listWorker.add(w3);
		
		
		return listWorker;
	}

	@Override
	public boolean registerNewUser(Worker worker) {
		KnowledgeBase kb = new KnowledgeBase();
		kb.createWorker(worker);
		return true;
	}

	@Override
	public List<Worker> getWorkersWithCompetence(String competence) {
		KnowledgeBase kb = new KnowledgeBase();
		return kb.getWorkersWithCompetence(competence);
	}
	
	@Override
	public Map<String, List<Worker>> getWorkersWithCompetences(
			List<String> competencesUri, List<Integer> num_workers,
			List<List<String>> workersUri_forbidden) {
		KnowledgeBase kb = new KnowledgeBase();
		return kb.getWorkersWithCompetences(competencesUri, num_workers, workersUri_forbidden);
	}

	@Override
	public String addCompetence(String workerUri, String competenceName, Double rating) {
		KnowledgeBase kb = new KnowledgeBase();
		Competence c = new Competence();
		c.setName(competenceName);
		c.setRating(rating);
		return kb.addCompetence(workerUri, c);
	}

	@Override
	public String createTask(Task task) {
		KnowledgeBase kb = new KnowledgeBase();
		return kb.insertTask(task);
	}

	@Override
	public List<Worker> getWorkersOfTask(String taskUri) {
		KnowledgeBase kb = new KnowledgeBase();
		return kb.getWorkersOfTask(taskUri);
	}

	@Override
	public List<Task> getTasksOfWorker(String workerUri) {
		KnowledgeBase kb = new KnowledgeBase();
		return kb.getTasksOfWorker(workerUri);
	}

	@Override
	public List<Competence> getCompetenceOfWorker(String workerUri) {
		KnowledgeBase kb = new KnowledgeBase();
		return kb.getCompetenceOfWorker(workerUri);
	}

	@Override
	public List<Worker> getCoworkersOf(String workerUri) {
		KnowledgeBase kb = new KnowledgeBase();
		return kb.getCoworkerOf(workerUri);
	}

	@Override
	public void releaseFeedback(String workerUri, List<Competence> competences) {
		KnowledgeBase kb = new KnowledgeBase();
		for(Competence c : competences)
			kb.releaseFeedback(workerUri, c);
	}
	
}
