package it.unisa.gps.teamcreator.client.event;

import com.google.gwt.event.shared.GwtEvent;


public class SearchTaskEvent extends GwtEvent<SearchTaskHandler> {
	  public static Type<SearchTaskHandler> TYPE = new Type<SearchTaskHandler>();
	  
	  @Override
	  public Type<SearchTaskHandler> getAssociatedType() {
	    return TYPE;
	  }

	

	


	@Override
	protected void dispatch(SearchTaskHandler handler) {
		handler.searchTask(this);
		
	}
	}