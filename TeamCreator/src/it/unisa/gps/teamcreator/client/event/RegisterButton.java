package it.unisa.gps.teamcreator.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;


public class RegisterButton extends GwtEvent<RegisterButtonHandler> {
	  public static Type<RegisterButtonHandler> TYPE = new Type<RegisterButtonHandler>();
	  
	  @Override
	  public Type<RegisterButtonHandler> getAssociatedType() {
	    return TYPE;
	  }

	

	@Override
	protected void dispatch(RegisterButtonHandler handler) {
		handler.signIn(this);
		
	}

}