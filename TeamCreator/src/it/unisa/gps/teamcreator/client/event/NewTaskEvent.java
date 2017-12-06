package it.unisa.gps.teamcreator.client.event;

import com.google.gwt.event.shared.GwtEvent;


public class NewTaskEvent extends GwtEvent<NewTaskHandler> {
	  public static Type<NewTaskHandler> TYPE = new Type<NewTaskHandler>();
	  
	  @Override
	  public Type<NewTaskHandler> getAssociatedType() {
	    return TYPE;
	  }

	

	


	@Override
	protected void dispatch(NewTaskHandler handler) {
		handler.newTask(this);
		
	}
	}