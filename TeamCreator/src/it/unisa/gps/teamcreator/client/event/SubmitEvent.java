package it.unisa.gps.teamcreator.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;


public class SubmitEvent extends GwtEvent<SubmitEventHandler> {
	  public static Type<SubmitEventHandler> TYPE = new Type<SubmitEventHandler>();
	  
	  @Override
	  public Type<SubmitEventHandler> getAssociatedType() {
	    return TYPE;
	  }

	

	@Override
	protected void dispatch(SubmitEventHandler handler) {
		handler.onLogin(this);
		
	}
	}