package it.unisa.gps.teamcreator.client.presenter;





import it.unisa.gps.teamcreator.client.GreetingServiceAsync;
import it.unisa.gps.teamcreator.client.LoginService;
import it.unisa.gps.teamcreator.client.view.HeaderView;
import it.unisa.gps.teamcreator.client.view.NewTaskView;
import it.unisa.gps.teamcreator.shared.Competence;
import it.unisa.gps.teamcreator.shared.Task;
import it.unisa.gps.teamcreator.shared.Worker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;




public class CoWorkersPresenter implements Presenter{

	public interface Display {
		Widget asWidget();
		void disp(String nome, String cognome, String immagine, String id_worker);
		void setCoWorkers (List<Worker> array);
	}

	private final GreetingServiceAsync rpcService;
	private final HandlerManager eventBus;
	public static List<Worker> array;
	private Worker user;
	@UiField Button workButton;

	private final Display display;

	public CoWorkersPresenter(GreetingServiceAsync rpcService, HandlerManager eventBus, Display view) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
		user = LoginService.Util.getLoggedUser();
		bind();

	}

	@Override
	public void go(RootPanel container) {
		HeaderView.showSearch();
		rpcService.getCoworkersOf(user.getUri(),new AsyncCallback<List<Worker>>() {

			@Override
			public void onSuccess(List<Worker> result) {
				display.setCoWorkers(result);
				if (Document.get().getElementById("divLoading") != null)
				{
					Document.get().getElementById("divLoading").setAttribute("style", "display:none");
				}
				
				for (Worker w : result){
					display.disp(w.getFirstname(),w.getSurname(),w.getImagePath(),w.getUri());
				}			

			}

			@Override
			public void onFailure(Throwable caught) {
				if (Document.get().getElementById("divLoading") != null)
				{
					Document.get().getElementById("divLoading").setAttribute("style", "display:none");
				}
				Window.alert("Error page");


			}
		});


	}




	//	public static native void addWorker(com.google.gwt.dom.client.Element  form,String s) /*-{
	//	var x=document.getElementById("content");
	//
	//
	//  	var node = document.createElement("div");
	//	//node.setAttribute("class", "clearfix");
	//    var text =  s;
	//    
	//    //var div = document.createElement('div');
	//	//node.appendChild(div);
	//	node.innerHTML = text;
	//	
	////  var textnode = document.createTextNode(text);  
	////  node.appendChild(textnode); 
	//  	form.appendChild(node);
	//}-*/;	



	public void bind() { 

	}




}


