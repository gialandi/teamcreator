package it.unisa.gps.teamcreator.client.presenter;





import java.util.Iterator;

import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import it.unisa.gps.teamcreator.client.GreetingServiceAsync;
import it.unisa.gps.teamcreator.client.event.NewTaskEvent;
import it.unisa.gps.teamcreator.client.event.SubmitEvent;
import it.unisa.gps.teamcreator.client.view.HeaderView;
import it.unisa.gps.teamcreator.client.view.NewTaskView;
import it.unisa.gps.teamcreator.client.view.ProfileView;
import it.unisa.gps.teamcreator.client.view.SideBarView;



public class DashboardPresenter implements Presenter,HasWidgets{

	
	public interface Display {
		HasClickHandlers getNewTaskButton();
		HTMLPanel rootDashboad();
		Widget asWidget();
	}

	private final GreetingServiceAsync rpcService;
	private final HandlerManager eventBus;

	
	//private final Display display;
	

	public DashboardPresenter(GreetingServiceAsync rpcService, HandlerManager eventBus) {
		
		
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		//this.display = view;
		
		
		
		
		bind();
	}

	@Override
	public void go(RootPanel container) {
		
		
	}
	

	public void bind() { 
		

	}

	public boolean removeContent (){
		return false;
		
	}
	@Override
	public void add(Widget w) {
		
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Iterator<Widget> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Widget w) {
		// TODO Auto-generated method stub
		return false;
	}


}


