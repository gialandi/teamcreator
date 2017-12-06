package it.unisa.gps.teamcreator.client.presenter;






import it.unisa.gps.teamcreator.client.GreetingServiceAsync;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;



public class RegistrationPresenter implements Presenter {

	public interface Display {
	//	HasClickHandlers getRegisterButton();
		Widget asWidget();
	}

	private final GreetingServiceAsync rpcService;
	private final HandlerManager eventBus;

	
	private final Display display;

	public RegistrationPresenter(GreetingServiceAsync rpcService, HandlerManager eventBus, Display display) {
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
//		display.getRegisterButton().addClickHandler(new ClickHandler() {   
//			public void onClick(ClickEvent event) {
//				eventBus.fireEvent(new SubmitEvent());
//
//			}
//		});
	}


}


