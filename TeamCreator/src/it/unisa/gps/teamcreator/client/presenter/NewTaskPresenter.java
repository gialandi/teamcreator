package it.unisa.gps.teamcreator.client.presenter;





import it.unisa.gps.teamcreator.client.GreetingServiceAsync;

import java.util.Iterator;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;




public class NewTaskPresenter implements Presenter{

	public interface Display {
			HasClickHandlers getFinishButton();
			Widget asWidget();
		}

	private final GreetingServiceAsync rpcService;
	private final HandlerManager eventBus;
	
	
	
	private final Display display;

	public NewTaskPresenter(GreetingServiceAsync rpcService, HandlerManager eventBus, Display view) {
		
		
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
		bind();
	}

	@Override
	public void go(RootPanel container) {
		
		
	}

	public void bind() { 
		display.getFinishButton().addClickHandler(new ClickHandler() {   
			public void onClick(ClickEvent event) {
				
				//eventBus.fireEvent(new SubmitEvent());

			}
		});
			}
		
	


}


