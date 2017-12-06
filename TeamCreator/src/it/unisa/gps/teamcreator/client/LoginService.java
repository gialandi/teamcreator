package it.unisa.gps.teamcreator.client;

import it.unisa.gps.teamcreator.shared.Worker;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("LoginService")
public interface LoginService extends RemoteService
{
    /**
     * Utility class for simplifying access to the instance of async service.
     */
    public static class Util
    {
        private static LoginServiceAsync instance;
		private static Worker loggedUser;
 
        public static LoginServiceAsync getInstance(){
            if (instance == null){
                instance = GWT.create(LoginService.class);
            }
            return instance;
        }
        
        public static Worker getLoggedUser(){
        	return loggedUser;
        }
        
        public static void setLoggedUser(Worker w){
        	loggedUser = w;
        }
    }
 
    Worker loginServer(String name, String password);
 
    Worker loginFromSessionServer();
     
    boolean changePassword(String name, String newPassword);
 
    void logout();
    
    void updateLoggedUser();
}
