package it.unisa.gps.teamcreator.server;
import java.util.ArrayList;
import java.util.List;

import it.unisa.gps.teamcreator.client.LoginService;
import it.unisa.gps.teamcreator.shared.Competence;
import it.unisa.gps.teamcreator.shared.FieldVerifier;
import it.unisa.gps.teamcreator.shared.Worker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

 
/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {
 
	@Override
    public Worker loginServer(String name, String password){
		if((name.equals("a"))&&(password.equals("a"))){
			Worker w = new Worker();
			w.setEmail("mail@mail.it");
			w.setFirstname("Utente");
			w.setSurname("Test");
			w.setLoggedIn(true);
			List<Competence> listCompetence = new ArrayList<Competence>();
			Competence c1 = new Competence();
			c1.setName("Java");
			c1.setRating(2);
			Competence c2 = new Competence();

			c2.setName("HTML 5");
			c2.setRating(3);
			Competence c3 = new Competence();
			c3.setName("Google Web Toolkit");
			c3.setRating(5);
			listCompetence.add(c1);

			listCompetence.add(c2);

			listCompetence.add(c3);
			w.setUri("uridelFake");
			w.setCompetences(listCompetence);
			return w;
		}
        KnowledgeBase kb = new KnowledgeBase();
        Worker user = kb.getUser(name, password);
        storeUserInSession(user);
        return user;
    }
 
    @Override
    public Worker loginFromSessionServer(){
        return getUserAlreadyFromSession();
    }
 
    @Override
    public void logout(){
        deleteUserFromSession();
    }
 
    /**
     * to be implemented....
     */
    @Override
    public boolean changePassword(String name, String newPassword){
    	return false; 
    }
 
    private Worker getUserAlreadyFromSession(){
        Worker user = null;
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession();
        Object userObj = session.getAttribute("user");
        if (userObj != null && userObj instanceof Worker)
        {
            user = (Worker) userObj;
            user.setLoggedIn(true);
        }
        return user;
    }
 
    private void storeUserInSession(Worker user){
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("user", user);
    }
 
    private void deleteUserFromSession(){
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession();
        session.removeAttribute("user");
    }
 
 
	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html
	 *            the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}

	@Override
	public void updateLoggedUser() {
		Worker logged = getUserAlreadyFromSession();
		loginServer(logged.getEmail(), logged.getPassword());
		
	}
}