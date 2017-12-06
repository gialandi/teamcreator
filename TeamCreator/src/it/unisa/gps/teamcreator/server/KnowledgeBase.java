package it.unisa.gps.teamcreator.server;

import it.unisa.gps.teamcreator.shared.Competence;
import it.unisa.gps.teamcreator.shared.Task;
import it.unisa.gps.teamcreator.shared.Worker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hp.hpl.jena.query.*;

import virtuoso.jena.driver.*;

public class KnowledgeBase {
	
	private static final Logger LOGGER = Logger.getLogger( KnowledgeBase.class.getName() );
	private static final Level LOGGER_LEVEL = Level.INFO;

	private static final String PASSWORD = "dba";
	private static final String USER = PASSWORD;

//	private static final String URL_VIRTUOSO = "jdbc:virtuoso://utkk5d5e29a4.teamcreatorgps.koding.io:1111";
	private static final String URL_VIRTUOSO = "jdbc:virtuoso://localhost:1111";
	private static final String NS = "http://www.teamcreator.org/ontologies/";
	private static final String URI_WORKER = "http://www.teamcreator.org/ontologies/Worker";
	private static final String URI_COMPETENCE = "http://www.teamcreator.org/ontologies/Competence";
	private static final String URI_FEEDBACK = "http://www.teamcreator.org/ontologies/Feedback";
	private static final String URI_TASK = "http://www.teamcreator.org/ontologies/Task";
	private static final String PREF_LABEL = "<http://www.w3.org/2004/02/skos/core#prefLabel>";
	
	private String pathImage = "/images/avatar/128.jpg";

	private VirtGraph DATASET;
	
	public static void main(String[] args) {
	try {
		testCreateWorker();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		//	System.out.println(new KnowledgeBase().getCompetenceUri("GOogle Web Toolkit"));
	}

	
	public KnowledgeBase(){
		ConsoleHandler handler = new ConsoleHandler();
		handler.setLevel(LOGGER_LEVEL);
		LOGGER.addHandler(handler);
		DATASET = new VirtGraph (URL_VIRTUOSO, USER, PASSWORD);
	}
	
	public Worker getUser(String accountName, String password){
		String query = "SELECT ?firstname ?surname ?uri FROM <"+NS+"> WHERE {"
					+  "?uri a <"+URI_WORKER+"> . "
					+  "?uri <http://xmlns.com/foaf/0.1/accountName> \""+accountName+"\" . "
					+  "?uri <http://www.teamcreator.org/ontologies/password> \""+password+"\" . "
					+  "?uri <http://xmlns.com/foaf/0.1/firstName> ?firstname . "
					+  "?uri <http://xmlns.com/foaf/0.1/surname> ?surname }"; 
		Query sparql = QueryFactory.create(query);
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (sparql, DATASET);
		ResultSet results = vqe.execSelect();
		while (results.hasNext()) {
			QuerySolution rs = results.nextSolution();
			return getWorkerByUri(rs.get("uri").toString());
		}
		return null;
	}
	
	private static void testCreateWorker() throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader("/Users/gabrielenapoli/Dropbox/UniSa/Esami/GPS/Eclipse Workspace/TeamCreator/war/elenco2.txt"));
		String[] info;
		String line;
		while((line = reader.readLine())!=null){
			Worker w = new Worker();
			w.setDescription("Questa è la mia description");
			w.setUri(generateUri("Worker"));
			info=line.split(";");
			w.setFirstname(info[1]);
			w.setEmail(info[3]);
			w.setImagePath("images/avatar/"+info[1]+" "+info[2]+".jpg");
			w.setPassword(info[0]);
			w.setSurname(info[2]);
			for (int i=4;i<info.length-1;i=i+2){
			
				Competence c=new Competence();
				c.setName(info[i]);
				c.setRating(Double.parseDouble(info[i+1]));
				w.addCompetence(c);
			}
			new KnowledgeBase().createWorker(w);
		}
		reader.close();
	}
	
/*	private static String testCreateTask(){
		Task t = new Task();
		t.setNameTask("NOME DEL TASK");
		t.setDescription("DESCRIZIONE DEL TASK");
		Worker[] workers = new Worker[2];
		for(int i=0; i<2; i++){
			Worker w = new Worker();
			w.setUri("WORKER-"+i);
			workers[i] = w;
		}
		t.setListWorkers(workers);
		List<String> competences = new ArrayList<String>();
		competences.add("C1");
		competences.add("C2");
		t.setListSkills(competences);
		return new KnowledgeBase().insertTask(t);
	}
*/
	
	
	public static String generateUri(String string) {
		return NS+string+"#"+UUID.randomUUID();
	}

	public String createWorker(Worker worker){
		worker.setUri(generateUri("Worker"));
		if(worker.getDescription() == null) worker.setDescription("");
		worker.setDescription(worker.getDescription().replaceAll("(\\r|\\n)", ""));
		String imageUri = generateUri("ImageProfile");
		String query = "INSERT INTO GRAPH <"+NS+"> { "
				+ "<"+worker.getUri()+"> a <"+URI_WORKER+"> ."
				+ "<"+worker.getUri()+"> foaf:firstName \""+worker.getFirstname()+"\" ."
				+ "<"+worker.getUri()+"> foaf:surname \""+worker.getSurname()+"\" ."
				+ "<"+worker.getUri()+"> foaf:accountName \""+worker.getEmail()+"\" ."
				+ "<"+worker.getUri()+"> tc:password \""+worker.getPassword()+"\" ."
				+ "<"+worker.getUri()+"> tc:description \""+worker.getDescription()+"\" ."
				+ "<"+imageUri+"> a foaf:Image . "
				+ "<"+imageUri+"> rdf:resource \""+worker.getImagePath()+"\" . "
				+ "<"+worker.getUri()+"> foaf:img <"+imageUri+"> . "
		+ "}";
		LOGGER.log(Level.INFO, query);
		VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(query, DATASET);
		vur.exec();
		for(Competence c : worker.getCompetences()) this.addCompetence(worker, c);
		return worker.getUri();
	}
	
	
	public void addCompetence(Worker worker, Competence c) {
		String competenceUri = getCompetenceUri(c);
		if (competenceUri == null) competenceUri = createCompetence(c);
		c.setUri(competenceUri);
		String query = "INSERT INTO GRAPH <"+NS+"> { "
				+ "<"+worker.getUri()+"> tc:has_competence <"+c.getUri()+"> ."
		+ "}";
		VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(query, DATASET);
		vur.exec();
		releaseFeedback(worker, c);
	}
	
	public String addCompetence(String workerUri, Competence c) {
		String competenceUri = getCompetenceUri(c);
		if (competenceUri == null) competenceUri = createCompetence(c);
		c.setUri(competenceUri);
		String query = "INSERT INTO GRAPH <"+NS+"> { "
				+ "<"+workerUri+"> tc:has_competence <"+c.getUri()+"> ."
		+ "}";
		VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(query, DATASET);
		vur.exec();
		releaseFeedback(workerUri, c);
		return c.getUri();
	}

	public void releaseFeedback(Worker worker, Competence c) {
		releaseFeedback(worker.getUri(), c);
	}
	
	public void releaseFeedback(String workerUri, Competence c) {
		releaseFeedback(workerUri, c.getUri(), c.getName(), c.getRating());
	}
	
	public String releaseFeedback(String workerUri, String competenceUri, String competenceName, Double rating) {
		if (workerUri == null) return null;
		if ((competenceUri == null) && (competenceName!=null)) competenceUri = getCompetenceUri(competenceName);
		if (competenceUri == null) return null;
		if(rating == null) rating = 3.0;

		String uri = generateUri("Feedback");
		String query = "INSERT INTO GRAPH <"+NS+"> { "
				+ "<"+uri+"> a <"+URI_FEEDBACK+"> ."
				+ "<"+uri+"> tc:released_by <"+workerUri+"> ."
				+ "<"+uri+"> tc:stars \""+rating+"\" ."
				+ "<"+uri+"> tc:about_competence <"+competenceUri+">"
		+ "}";
		LOGGER.log(Level.INFO, query);
		VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(query, DATASET);
		vur.exec();
		return uri;
	}
//	
//	public List<String> getWorkersWithCompetences(List<String> competences){
//		String query = "SELECT DISTINCT * FROM <"+NS+"> WHERE { ";
//		for(int i=0; i < competences.size(); i++){
//			query += "{ ?worker tc:has_competence ?competence . "
//					+ " ?competence <http://www.w3.org/2004/02/skos/core#prefLabel> ?competenceName . "
//					+ " FILTER (?competenceName = \""+competences.get(i)+"\") }";
//			if(i < competences.size() - 1 ) query += " UNION ";
//		}
//		query += "} ";
//		System.out.println(query);
//		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (query, DATASET);
//		ResultSet results = vqe.execSelect();
//		while (results.hasNext()) {
//			QuerySolution rs = results.nextSolution();
//			Worker w = new Worker();
//			w.setUri(rs.get("worker").toString());
//			Competence c = new Competence();
//			c.setUri(rs.get("competence").toString());
//			c.setName(rs.get("competenceName").toString());
//			List<Competence> list = new ArrayList<Competence>();
//			list.add(c);
//			w.setCompetences(list);
//			//System.out.println(w);
//			System.out.println(rs);
//		}
//		return null;
//	}
	
	/**
	 * 
	 * @param competences competenze richieste
	 * @param num_workers num_workers[i] contiene il numero di worker necessario per soddisfare la competenza[i]
	 * @param workers_forbidden workers_forbidden[i] contiene la uri del worker non richiesti dal pm per la competneza[i]
	 * @return
	 */
	public Map<String, List<Worker>> getWorkersWithCompetences(List<String> competences, List<Integer> num_workers, List<List<String>> workers_forbidden){
		Map<String, List<Worker>> toReturn = new HashMap<String, List<Worker>>();
		for(int i=0; i < competences.size(); i++) {
			List<Worker> workers = getWorkersWithCompetence(competences.get(i));
			
			// rimuovo i workers forbidden
			for(int j=0; j < workers.size(); j++) {
				if(i >= workers_forbidden.size()) break;
				if(workers_forbidden.get(i).contains(workers.get(j))) workers.remove(j);
			}
			
			// aggiusto le dimensioni degli array per rispettare i vincoli
			for(int j=num_workers.get(i); j < workers.size(); j++) 
				workers.remove(j);
			
			toReturn.put(competences.get(i), workers);
		}	
		return toReturn;

	}
	
	public List<Worker> getWorkersWithCompetence(String competence){
		String query = "SELECT DISTINCT * FROM <"+NS+"> WHERE { "
					+ " ?worker a <"+URI_WORKER+">. "
					+ " ?worker tc:has_competence+ ?competence . "
					+ " ?competence <http://www.w3.org/2004/02/skos/core#prefLabel> ?competenceName . "
					+ " FILTER (LCASE(?competenceName) = LCASE(\""+competence+"\")) }";
		LOGGER.log(Level.INFO, query);
		List<Worker> result = new ArrayList<Worker>();
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (query, DATASET);
		ResultSet results = vqe.execSelect();
		while (results.hasNext()) {
			QuerySolution rs = results.nextSolution();
			Worker w = getWorkerByUri(rs.get("worker").toString());
			String competenceUri = rs.get("competence").toString();
			Competence c = getCompetenceWithFeedback(competenceUri, w.getUri());
			w.addCompetence(c);
			inserimentoOrdinato(result, w, c);
		}
		return result;
	}

	private void inserimentoOrdinato(List<Worker> list, Worker w, Competence c) {
		for(int i=0; i < list.size(); i++)
			if(list.get(i).getCompetences().get(0).getRating() < c.getRating()) {
				list.add(i, w);
				return;
			}
		list.add(w);
	}
	
	/**
	 * 
	 * @param competenceUri
	 * @param workerUri
	 * @return la competenza con uri competenceUri, completa con i feedback per l'utente workeruri
	 */

	private Competence getCompetenceWithFeedback(String competenceUri, String workerUri){
		String query = "SELECT * FROM <"+NS+"> WHERE { "
					+  "<"+workerUri+"> tc:has_competence+ <"+competenceUri+"> ."
					+  "?feedback tc:about_competence <"+competenceUri+"> . "
					+ " <"+competenceUri+"> <http://www.w3.org/2004/02/skos/core#prefLabel> ?competenceName . "
					+ " ?feedback tc:stars ?rating }";
		LOGGER.log(Level.INFO, query);
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (query, DATASET);
		ResultSet results = vqe.execSelect();
		int count = 0;
		double sum = 0;
		double avg;
		String competenceName = "";
		while(results.hasNext()){
			QuerySolution rs = results.nextSolution();
			sum += Double.parseDouble(rs.get("rating").toString());
			count++;
			competenceName = rs.get("competenceName").toString();
		}
		if(count == 0) avg = -1; else avg = sum / count;
		Competence c = new Competence();
		c.setUri(competenceUri);
		c.setName(competenceName);
		c.setRating(avg);
		return c;
	}
	
//	private List<Worker> getWorkersWithCompetences(List<Competence> competences){
//		String query = "SELECT DISTINCT * FROM <"+NS+"> WHERE { ";
//		for(int i=0; i < competences.size(); i++){
//			query += "{ ?worker tc:has_competence ?competence . "
//					+ " ?competence <http://www.w3.org/2004/02/skos/core#prefLabel> ?competenceName . "
//					+ " FILTER (?competenceName = \""+competences.get(i).getName()+"\") . "
//					+ " ?feedback tc:about_competence ?competence . "
//					+ " ?feedback tc:stars ?rating }";
//			if(i < competences.size() - 1 ) query += " UNION ";
//		}
//		query += "} ";
//		System.out.println(query);
//		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (query, DATASET);
//		ResultSet results = vqe.execSelect();
//		HashMap<String, Worker> map = new HashMap<String, Worker>();
//		while (results.hasNext()) {
//			QuerySolution rs = results.nextSolution();
//			String uriWorker = rs.get("worker").toString();
//			Worker w = map.get(uriWorker);
//			if(w == null){
//				w = new Worker();
//				w.setUri(uriWorker);
//			}
//			Competence c = new Competence();
//			c.setUri(rs.get("competence").toString());
//			c.setName(rs.get("competenceName").toString());
//			c.setRating(Integer.parseInt(rs.get("rating").toString()));
//			w.addCompetence(c);
//			map.put(uriWorker, w);
//		}
//		TreeMap<Competence, Worker> result = new TreeMap<Competence, Worker>();
//		for(Worker w : map.values()) {
//			for(Competence c : w.getCompetences()){
//			//	result.get
//			}
//		}
//		return null;
//	}
	
	
	public String createCompetence(String competenceName) {
		String uri = generateUri("Competence");
		String query = "INSERT INTO GRAPH <"+NS+"> { "
				+ "<"+uri+"> a <"+URI_COMPETENCE+"> ."
				+ "<"+uri+"> <http://www.w3.org/2004/02/skos/core#prefLabel> \""+competenceName+"\" ."
		+ "}";
		VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(query, DATASET);
		vur.exec();
		return uri;
	}
	

	public String createCompetence(Competence c) {
		return createCompetence(c.getName());
	}
	
	public List<Competence> getCompetenceOfWorker(String workerUri) {
		String query = "SELECT DISTINCT * FROM <"+NS+"> WHERE {"
				+  "?uri a <"+URI_COMPETENCE+"> . "
				+  "<"+workerUri+"> <"+NS+"has_competence>+ ?uri . "
				+  "?uri <http://www.w3.org/2004/02/skos/core#prefLabel> ?name "
				+ "}"; 
		LOGGER.log(Level.INFO, query);
		Query sparql = QueryFactory.create(query);
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (sparql, DATASET);
		ResultSet results = vqe.execSelect();
		List<Competence> toReturn = new ArrayList<Competence>();
		while (results.hasNext()) {
			QuerySolution rs = results.nextSolution();
			Competence c = getCompetenceWithFeedback(rs.get("uri").toString(), workerUri);
			c.setName(rs.get("name").toString());
			toReturn.add(c);
		}
		return toReturn;
	}
	
	/**
	 * 
	 * @param workerUri
	 * @return il worker identificato dalla uri passata
	 */
	public Worker getWorkerByUri(String workerUri){
		String query = "SELECT * FROM <"+NS+"> WHERE {"
				+  "<"+workerUri+"> a <"+URI_WORKER+"> . "
				+  "<"+workerUri+"> <http://xmlns.com/foaf/0.1/accountName> ?accountName . "
				+  "<"+workerUri+"> <http://www.teamcreator.org/ontologies/password> ?password . "
				+  "<"+workerUri+"> <http://www.teamcreator.org/ontologies/description> ?description . "
				+  "<"+workerUri+"> <http://xmlns.com/foaf/0.1/firstName> ?firstname . "
				+  "<"+workerUri+"> <http://xmlns.com/foaf/0.1/surname> ?surname . "
				+  "<"+workerUri+"> <http://xmlns.com/foaf/0.1/img> ?uri_img . "
				+  "?uri_img <http://www.w3.org/1999/02/22-rdf-syntax-ns#resource> ?path_img "
				+ "}"; 
		Query sparql = QueryFactory.create(query);
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (sparql, DATASET);
		ResultSet results = vqe.execSelect();
		Worker w = new Worker();
		while (results.hasNext()) {
			QuerySolution rs = results.nextSolution();
			w.setUri(workerUri);
			w.setEmail(rs.get("accountName").toString());
			w.setFirstname(rs.get("firstname").toString());
			w.setSurname(rs.get("surname").toString());
			w.setImagePath(rs.get("path_img").toString());
			w.setDescription(rs.get("description").toString());
			w.setPassword(rs.get("password").toString());
			w.setLoggedIn(true);
			w.setCompetences(getCompetenceOfWorker(workerUri));
		}
		return w;
	}
	

	
	
	/**
	 * 
	 * 
	 * @param c
	 * @return restituisce la uri di una competenza a partire dal nome. Se non è presente nella kb, restituisce null.
	 */
	public String getCompetenceUri(String competenceName) {
		if (competenceName == null) return null;
		String query = "SELECT ?uri  FROM <"+NS+"> WHERE {"
				+  "?uri a <"+URI_COMPETENCE+"> . "
				+  "?uri "+PREF_LABEL+" ?prefLabel."
				+ " FILTER (LCASE(?prefLabel) = LCASE(\""+competenceName+"\")) }"; 
		Query sparql = QueryFactory.create(query);
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (sparql, DATASET);
		ResultSet results = vqe.execSelect();
		String uriCompetence = null;
		if (results.hasNext()) {
			QuerySolution rs = results.nextSolution();
			uriCompetence = rs.get("uri").toString();
			return uriCompetence;
		}
		return null;
	}
	
	public String getCompetenceUri(Competence c) {
		return getCompetenceUri(c.getName());
	}

	private static void clear(){
		VirtGraph dataset = new VirtGraph (URL_VIRTUOSO, USER, PASSWORD);
		String str = "CLEAR GRAPH <"+NS+">";
		VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(str, dataset);
		vur.exec();     
	}


	public String insertTask(Task task) {
		task.setUri(generateUri("Task"));
		String query = "INSERT INTO GRAPH <"+NS+"> { "
				+ "<"+task.getUri()+"> a <"+URI_TASK+"> . "
				+ "<"+task.getUri()+"> "+PREF_LABEL+" \""+task.getNameTask()+"\" . ";
		for(String competenceName : task.getListSkills()){
			String competenceUri = getCompetenceUri(competenceName);
			if(competenceUri == null) competenceUri = createCompetence(competenceName);
			query += "<"+task.getUri()+"> tc:require_competence \""+competenceUri+"\" .";
		}
		for(Worker worker : task.getListWorkers())
			query += "<"+worker.getUri()+"> tc:work_in <"+task.getUri()+"> .";
		query += "<"+task.getUri()+"> tc:description \""+task.getDescription()+"\" }";
		VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(query, DATASET);
		vur.exec();
		return task.getUri();
	}

	public Task getTaskByUri(String taskUri){
		String query = "SELECT * FROM <"+NS+"> WHERE {"
				+  "<"+taskUri+"> a <"+URI_TASK+"> . "
				+  "<"+taskUri+"> <http://www.teamcreator.org/ontologies/description>  ?description . "
				+  "<"+taskUri+"> "+PREF_LABEL+"  ?name . "
		+ "}"; 
		Query sparql = QueryFactory.create(query);
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (sparql, DATASET);
		ResultSet results = vqe.execSelect();
		Task task = null;
		if (results.hasNext()) {
			QuerySolution rs = results.nextSolution();
			task = new Task();
			task.setUri(taskUri);
			task.setNameTask(rs.get("name").toString());
			task.setDescription(rs.get("description").toString());
			task.setListSkills(getRequiredCompetenceOfTask(taskUri));
			List<Worker> workers = getWorkersOfTask(taskUri);
			task.setListWorkers(workers);
		}
		return task;
	}


	public List<Worker> getWorkersOfTask(String taskUri) {
		String query = "SELECT * FROM <"+NS+"> WHERE {"
				+  "<"+taskUri+"> a <"+URI_TASK+"> . "
				+  "?worker <http://www.teamcreator.org/ontologies/work_in>  <"+taskUri+"> . "
				+  "<"+taskUri+"> "+PREF_LABEL+"  ?name . "
		+ "}"; 
		Query sparql = QueryFactory.create(query);
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (sparql, DATASET);
		ResultSet results = vqe.execSelect();
		List<Worker> toReturn = new ArrayList<Worker>();
		while (results.hasNext()) {
			QuerySolution rs = results.nextSolution();
			Worker w = getWorkerByUri(rs.get("worker").toString());
			toReturn.add(w);
		}
		return toReturn;
	}
	
	public List<Task> getTasksOfWorker(String workerUri){
		String query = "SELECT * FROM <"+NS+"> WHERE {"
				+  "<"+workerUri+"> a <"+URI_WORKER+"> . "
				+  "<"+workerUri+"> <http://www.teamcreator.org/ontologies/work_in> ?task "
		+ "}";
		LOGGER.log(Level.INFO, query);
		Query sparql = QueryFactory.create(query);
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (sparql, DATASET);
		ResultSet results = vqe.execSelect();
		List<Task> toReturn = new ArrayList<Task>();
		while (results.hasNext()) {
			QuerySolution rs = results.nextSolution();
			Task task = getTaskByUri(rs.get("task").toString());
			toReturn.add(task);
		}
		return toReturn;
	}


	private List<String> getRequiredCompetenceOfTask(String taskUri) {
		String query = "SELECT * FROM <"+NS+"> WHERE {"
				+  "<"+taskUri+"> a <"+URI_TASK+"> . "
				+  "<"+taskUri+"> <http://www.teamcreator.org/ontologies/require_competence> ?competence "
		+ "}"; 
		Query sparql = QueryFactory.create(query);
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (sparql, DATASET);
		ResultSet results = vqe.execSelect();
		List<String> toReturn = new ArrayList<String>();
		while (results.hasNext()) {
			QuerySolution rs = results.nextSolution();
			Competence c = getCompetenceByUri(rs.get("competence").toString());
			toReturn.add(c.getName());		// FACILMENTE CONVERTIBILE PER RESTITUIRE COMPETENCE COMPLETE
		}
		return toReturn;
	}


	public Competence getCompetenceByUri(String competenceUri) {
		String query = "SELECT * FROM <"+NS+"> WHERE {"
				+  "<"+competenceUri+"> a <"+URI_COMPETENCE+"> . "
				+  "<"+competenceUri+"> <http://www.w3.org/2004/02/skos/core#prefLabel> ?name ."
		+ "}"; 
		Query sparql = QueryFactory.create(query);
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (sparql, DATASET);
		ResultSet results = vqe.execSelect();
		Competence c = null;
		if (results.hasNext()) {
			QuerySolution rs = results.nextSolution();
			c = new Competence();
			c.setName(rs.get("name").toString());
			c.setUri(competenceUri);
		}
		return c;
	}
	
	public List<Worker> getCoworkerOf(String workerUri){
		List<Worker> toReturn = new ArrayList<Worker>();
		HashMap<String, Worker> map = new HashMap<String, Worker>();
		for(Task t : getTasksOfWorker(workerUri)){
			for(Worker w : t.getListWorkers())
				map.put(w.getUri(), w);
		}
		map.remove(workerUri);
		toReturn.addAll(map.values());
		return toReturn;
	}
}
	
//
//	private static void example8(String[] args) {
//		String url;
//		if(args.length == 0)
//			url = URL_VIRTUOSO;
//		else
//			url = args[0];
//
//		/*			STEP 1			*/
//		VirtGraph set = new VirtGraph (url, PASSWORD, PASSWORD);
//
//		/*			STEP 2			*/
//		System.out.println("\nexecute: CLEAR GRAPH <http://test1>");
//		String str = "CLEAR GRAPH <http://test1>";
//		VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(str, set);
//		vur.exec();                  
//
//		System.out.println("\nexecute: INSERT INTO GRAPH <http://test1> { <aa> <bb> 'cc' . <aa1> <bb1> 123. }");
//		str = "INSERT INTO GRAPH <http://test1> { <aa> <bb> 'cc' . <aa1> <bb1> 123. }";
//		vur = VirtuosoUpdateFactory.create(str, set);
//		vur.exec();                  
//
//		/*			STEP 3			*/
//		/*		Select all data in virtuoso	*/
//		System.out.println("\nexecute: SELECT * FROM <http://test1> WHERE { ?s ?p ?o }");
//		Query sparql = QueryFactory.create("SELECT * FROM <http://test1> WHERE { ?s ?p ?o }");
//
//		/*			STEP 4			*/
//		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (sparql, set);
//
//		ResultSet results = vqe.execSelect();
//		while (results.hasNext()) {
//			QuerySolution rs = results.nextSolution();
//			RDFNode s = rs.get("s");
//			RDFNode p = rs.get("p");
//			RDFNode o = rs.get("o");
//			System.out.println(" { " + s + " " + p + " " + o + " . }");
//		}
//
//
//		System.out.println("\nexecute: DELETE FROM GRAPH <http://test1> { <aa> <bb> 'cc' }");
//		str = "DELETE FROM GRAPH <http://test1> { <aa> <bb> 'cc' }";
//		vur = VirtuosoUpdateFactory.create(str, set);
//		vur.exec();                  
//
//		System.out.println("\nexecute: SELECT * FROM <http://test1> WHERE { ?s ?p ?o }");
//		vqe = VirtuosoQueryExecutionFactory.create (sparql, set);
//		results = vqe.execSelect();
//		while (results.hasNext()) {
//			QuerySolution rs = results.nextSolution();
//			RDFNode s = rs.get("s");
//			RDFNode p = rs.get("p");
//			RDFNode o = rs.get("o");
//			System.out.println(" { " + s + " " + p + " " + o + " . }");
//		}
//	}
//}
