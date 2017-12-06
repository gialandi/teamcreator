package it.unisa.gps.teamcreator.client;


import java.util.Iterator;

import it.unisa.gps.teamcreator.client.event.NewTaskEvent;
import it.unisa.gps.teamcreator.client.event.NewTaskHandler;
import it.unisa.gps.teamcreator.client.event.RegisterButton;
import it.unisa.gps.teamcreator.client.event.RegisterButtonHandler;
import it.unisa.gps.teamcreator.client.event.SearchTaskEvent;
import it.unisa.gps.teamcreator.client.event.SearchTaskHandler;
import it.unisa.gps.teamcreator.client.event.SubmitEvent;
import it.unisa.gps.teamcreator.client.event.SubmitEventHandler;
import it.unisa.gps.teamcreator.client.presenter.CoWorkersPresenter;
import it.unisa.gps.teamcreator.client.presenter.DashboardPresenter;
import it.unisa.gps.teamcreator.client.presenter.LoginPresenter;
import it.unisa.gps.teamcreator.client.presenter.NewTaskPresenter;
import it.unisa.gps.teamcreator.client.presenter.Presenter;
import it.unisa.gps.teamcreator.client.presenter.RegistrationPresenter;
import it.unisa.gps.teamcreator.client.presenter.SearchTaskPresenter;
import it.unisa.gps.teamcreator.client.view.CoWorkersView;
import it.unisa.gps.teamcreator.client.view.LoginView;
import it.unisa.gps.teamcreator.client.view.NewTaskView;
import it.unisa.gps.teamcreator.client.view.RegistrationView;
import it.unisa.gps.teamcreator.client.view.SearchTaskView;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import it.unisa.gps.teamcreator.client.GreetingService;
import it.unisa.gps.teamcreator.client.view.HeaderView;
import it.unisa.gps.teamcreator.client.view.ProfileView;
import it.unisa.gps.teamcreator.client.view.SideBarView;
import it.unisa.gps.teamcreator.shared.Worker;



public class AppController extends Composite implements HasWidgets,Presenter, ValueChangeHandler<String> {
	private final HandlerManager eventBus;
	private final GreetingServiceAsync rpcService; 
	private RootPanel container;
	private static SideBarView s ;
	private static ProfileView p ;
	private static HeaderView h ;
	private static FlowPanel divWrapper;

	private static Worker user;
	
	public AppController (GreetingServiceAsync rpcService, HandlerManager eventBus ) {
		this.eventBus = eventBus;
		this.rpcService = rpcService;
		bind();



	}



	private void bind() {
		History.addValueChangeHandler(this);
		
		eventBus.addHandler(SubmitEvent.TYPE,
		        new SubmitEventHandler() {
				@Override
				public void onLogin(SubmitEvent event) {
					goDashboard();
					
				}
		        }); 
		
		eventBus.addHandler(RegisterButton.TYPE,
		        new RegisterButtonHandler() {
				
				@Override
				public void signIn(RegisterButton event) {
					goRegistrationPage();
				}
		        }); 
		
		eventBus.addHandler(NewTaskEvent.TYPE,
		        new NewTaskHandler() {
				@Override
				public void newTask(NewTaskEvent event) {
					goNewTaskPage();
				}
		        }); 
		eventBus.addHandler(SearchTaskEvent.TYPE, new SearchTaskHandler() {
			
			@Override
			public void searchTask(SearchTaskEvent event) {
				goSearchTaskPage();
				
			}
		});
		
		
	}
		

	




	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();

		if (token != null) {
			Presenter presenter = null;

			if (token.equals("Login")) {
				//LoginView login = new LoginView();
				presenter = new LoginPresenter(rpcService, eventBus, new LoginView());
				
				
			}

			else if (token.equals("Dashboard")) {

				ProfileView p = new ProfileView();
				buildContainer(p.asWidget());

				presenter = new DashboardPresenter(rpcService, eventBus);


			}
			else if (token.equals("NewTask")) {
				NewTaskView newTask = new NewTaskView(rpcService);
				buildContainer(newTask.asWidget());
				presenter = new NewTaskPresenter(rpcService, eventBus, newTask);
				//goNewTaskPage();
			}
			else if (token.equals("Registration")) {
				RegistrationView registration = new RegistrationView();
				presenter = new RegistrationPresenter(rpcService, eventBus, registration);


			}

			else if (token.equals("CoWorkers")) {
				
				
				//goNewTaskPage();
				CoWorkersView listCoworkers = new CoWorkersView();
				buildContainer(listCoworkers.asWidget());
				presenter = new CoWorkersPresenter(rpcService, eventBus, listCoworkers);
				//presenter = new NewTaskPresenter(rpcService, eventBus, newTask);
				//goNewTaskPage();
			}
			else if(token.equals("SearchTask")){
				SearchTaskView searchView = new SearchTaskView(rpcService);
				buildContainer(searchView.asWidget());
				presenter= new SearchTaskPresenter(rpcService,eventBus,searchView);
				
			}

			if (presenter != null) {

				presenter.go(container);
			}

		}


	}

	@Override
	public void go(RootPanel container) {
		this.container = container;
		checkLogin();
		if ("".equals(History.getToken())) {
			History.newItem("Login");
		}
		else {

			
			History.fireCurrentHistoryState();
		}

	}

	private void checkLogin() {
		String sessionID = Cookies.getCookie("sid");
		if (sessionID == null){
			displayLoginWindow();
		} else {
			checkWithServerIfSessionIdIsStillLegal(sessionID);
		}
	}
	
	private void displayLoginWindow() {
		History.newItem("Login");
	}



	private void checkWithServerIfSessionIdIsStillLegal(String sessionID){
	    LoginService.Util.getInstance().loginFromSessionServer(new AsyncCallback<Worker>()
	    {
	        @Override
	        public void onFailure(Throwable caught){
	            displayLoginWindow();
	        }
	 
	        @Override
	        public void onSuccess(Worker result)
	        {
	            if (result == null){
	                displayLoginWindow();
	            } else {
	                if (result.getLoggedIn()){
	                	LoginService.Util.setLoggedUser(result);
	                	//Cookies.setCookie("", value);
	                	if (History.getToken().equals("Dashboard")){
	                		History.fireCurrentHistoryState();
	                	}
	                	History.fireCurrentHistoryState();

	                } else {
	                    displayLoginWindow();
	                }
	            }
	        }
	 
	    });
	}



	private void goRegistrationPage() {
		History.newItem("Registration");
	}

	private void goDashboard() {
		History.newItem("Dashboard");
	}

	private void goCoWorkers() {
		History.newItem("CoWorkers");
	}

	
	private void buildContainer (Widget c){

		s = new  SideBarView(eventBus);

		h = new HeaderView();
		
		

		container.clear();

		container.add(h);

		divWrapper = new FlowPanel();
		divWrapper.getElement().setAttribute("id", "wrapper");

		divWrapper.add(s);
		
		divWrapper.add(c);
		

		
		container.add(divWrapper);
		//		container.add(s);
		//		container.add(c);
	}

	private void goNewTaskPage() {
		History.newItem("NewTask");

	}
	
	private void goSearchTaskPage() {
		History.newItem("SearchTask");

	}



	@Override
	public void add(Widget w) {
		// TODO Auto-generated method stub

	}



	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}



	@Override
	public Iterator<Widget> iterator() {
		// TODO Auto-generated method stub
		return RootPanel.get().iterator();
	}



	@Override
	public boolean remove(Widget w) {
		// TODO Auto-generated method stub
		return false;
	}



}
