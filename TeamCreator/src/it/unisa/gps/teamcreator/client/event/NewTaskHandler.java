package it.unisa.gps.teamcreator.client.event;

import com.google.gwt.event.shared.EventHandler;



	public interface NewTaskHandler extends EventHandler {
		  void newTask(NewTaskEvent event);
		}
