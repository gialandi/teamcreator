package it.unisa.gps.teamcreator.client.view;


import it.unisa.gps.teamcreator.client.presenter.LoginPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public final class LoginView extends Composite implements LoginPresenter.Display{

	// The @UiField annotation tags elements that the widget should
	// maintain references too.
	@UiField Button sendButton;
	@UiField Button registrationButton;

	public LoginView() {
		initWidget(binder.createAndBindUi(this));
		
		sendButton.getElement().setAttribute("id", "btnSubmit");
		sendButton.getElement().setAttribute("class", "btn btn-lg btn-primary btn-block btn-signin");

		//    sendButton.addDomHandler(
		//            new MouseOverHandler() {
		//              @Override
		//              public void onMouseOver(MouseOverEvent event) {
		//              Window.alert("DOM");
		//              }
		//            },
		//            MouseOverEvent.getType());
	}


	@UiHandler("sendButton")
	void handleSendClick(ClickEvent event) {
	}

	

	private static final Binder binder = GWT.create(Binder.class);
	interface Binder extends UiBinder<Widget, LoginView> {}
	
	
	@Override
	public HasClickHandlers getSubmitButton() {
		// TODO Auto-generated method stub
		return sendButton;
	}
	
	public Widget asWidget() {
		return this;
	}


	@Override
	public HasClickHandlers getRegistrationButton() {
		// TODO Auto-generated method stub
		return registrationButton;
	}


	@Override
	public String getUser() {
		InputElement user = Document.get().getElementById("inputEmail").cast();
		//Window.alert("userraaaasrere = "+user.getValue());
		return user.getValue();
		
	}


	@Override
	public String getPassword() {
		InputElement password = Document.get().getElementById("inputPassword").cast();
		//Window.alert("passwossrd = "+password.getValue());
		return password.getValue();
				}
}