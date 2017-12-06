package it.unisa.gps.teamcreator.client.presenter;





import java.util.Date;

import it.unisa.gps.teamcreator.client.GreetingServiceAsync;
import it.unisa.gps.teamcreator.client.LoginService;
import it.unisa.gps.teamcreator.client.event.RegisterButton;
import it.unisa.gps.teamcreator.client.event.SubmitEvent;
import it.unisa.gps.teamcreator.shared.Worker;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;


public class LoginPresenter implements Presenter {

	public interface Display {
		HasClickHandlers getSubmitButton();
		HasClickHandlers getRegistrationButton();
		String getUser();
		String getPassword();
		Widget asWidget();
	}

	private final GreetingServiceAsync rpcService;
	private final HandlerManager eventBus;

	
	private final Display display;

	public LoginPresenter(GreetingServiceAsync rpcService, HandlerManager eventBus, Display display) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = display;
	}

	@Override
	public void go(RootPanel container) {
		bind();
		container.clear();
		container.add(display.asWidget());
	}

	public void bind() { 
		display.getSubmitButton().addClickHandler(new ClickHandler() {   
			public void onClick(ClickEvent event) {
				doLogin();
				//eventBus.fireEvent(new SubmitEvent());

			}
		});
		
		display.getRegistrationButton().addClickHandler(new ClickHandler() {   
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new RegisterButton());
	}

		});
}

	
	private void doLogin(){
		
		
		
		
		
		
		String user = display.getUser();
		String password = display.getPassword();
		
		LoginService.Util.getInstance().loginServer(user, password, new AsyncCallback<Worker>()
                {
                    @Override
                    public void onSuccess(Worker result){
                        if (result != null){                   
                            String sessionID = result.getSessionId();
                            final long DURATION = 1000 * 60 * 60 * 24 * 1;
                            Date expires = new Date(System.currentTimeMillis() + DURATION);
                            Cookies.setCookie("sid", sessionID, expires, null, "/", false);
                            LoginService.Util.setLoggedUser(result);
                            eventBus.fireEvent(new SubmitEvent());
                        } else {
                            Window.alert("Access Denied. Check your user-name and password.");
                        }
 
                    }
 
                    @Override
                    public void onFailure(Throwable caught)
                    {
                        Window.alert("iiiiiiAccess Denied. Check your user-name and password.");
                    }
                });
	}
}

