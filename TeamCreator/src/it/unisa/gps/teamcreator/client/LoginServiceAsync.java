package it.unisa.gps.teamcreator.client;

import it.unisa.gps.teamcreator.shared.Worker;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {

	void loginServer(String name, String password,
			AsyncCallback<Worker> callback);

	void loginFromSessionServer(AsyncCallback<Worker> callback);

	void changePassword(String name, String newPassword,
			AsyncCallback<Boolean> callback);

	void logout(AsyncCallback<Void> callback);

	void updateLoggedUser(AsyncCallback<Void> callback);



}
