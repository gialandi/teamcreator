package it.unisa.gps.teamcreator.client.view;


import it.unisa.gps.teamcreator.client.LoginService;
import it.unisa.gps.teamcreator.client.presenter.DashboardPresenter;
import it.unisa.gps.teamcreator.shared.Worker;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public final class DashboardView extends Composite implements DashboardPresenter.Display{

	// The @UiField annotation tags elements that the widget should
	// maintain references too.
	
	

	public DashboardView() {
		initWidget(binder.createAndBindUi(this));
		Worker user = LoginService.Util.getLoggedUser();
		
		//sendButton.getElement().setAttribute("id", "btnSubmit");
		//sendButton.getElement().setAttribute("class", "btn btn-lg btn-primary btn-block btn-signin");

		//    sendButton.addDomHandler(
		//            new MouseOverHandler() {
		//              @Override
		//              public void onMouseOver(MouseOverEvent event) {
		//              Window.alert("DOM");
		//              }
		//            },
		//            MouseOverEvent.getType());
	}


//	@UiHandler("sendButton")
//	void handleSendClick(ClickEvent event) {
//		Window.alert("event");
//		RootPanel.get().getElement().setAttribute("style", "background:red;");
//	}

	

	private static final Binder binder = GWT.create(Binder.class);
	interface Binder extends UiBinder<Widget, DashboardView> {}
	
	
	
	public Widget asWidget() {
		return this;
	}


	

	@Override
	public HTMLPanel rootDashboad() {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public HasClickHandlers getNewTaskButton() {
		// TODO Auto-generated method stub
		return null;
	}
}