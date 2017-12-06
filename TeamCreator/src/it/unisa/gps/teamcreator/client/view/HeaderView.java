package it.unisa.gps.teamcreator.client.view;


import it.unisa.gps.teamcreator.client.LoginService;
import it.unisa.gps.teamcreator.shared.Worker;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;


public final class HeaderView extends Composite {

	// The @UiField annotation tags elements that the widget should
	// maintain references too.
	
	@UiField SpanElement username;
	@UiField ImageElement avatar;

	
	public HeaderView() {
		initWidget(binder.createAndBindUi(this));
		Worker user = LoginService.Util.getLoggedUser();
		username.setInnerText(user.getFirstname() + " " + user.getSurname() );
		avatar.setAttribute("src", user.getImagePath());
		registerAddCompetence();
	}


	
	
	
	private static native void registerAddCompetence()/*-{
	$wnd.showProfileMenu=@it.unisa.gps.teamcreator.client.view.HeaderView::showProfileMenu(*);
	//$wnd.onRemoveSkill=@it.unisa.gps.teamcreator.client.view.NewTaskView::removeSkill(*)(param1);
	$wnd.logout=@it.unisa.gps.teamcreator.client.view.HeaderView::logout(*);
	$wnd.prova10=@it.unisa.gps.teamcreator.client.view.CoWorkersView::prova10(*);
	$wnd.shiftMenu=@it.unisa.gps.teamcreator.client.view.SideBarView::shiftMenu(*);

}-*/;


	private static final Binder binder = GWT.create(Binder.class);
	interface Binder extends UiBinder<Widget, HeaderView> {}
	
	public Widget asWidget(){
		return this;
	}
	

	
	private static void showProfileMenu(Element li) {
		if (Document.get().getElementById("menuProfile").getAttribute("class").equals("dropdown-menu dropdown-user pull-right")){
			Document.get().getElementById("menuProfile").setAttribute("class", "dropdown-menu_show dropdown-user pull-right");
			
		}
		else{
			Document.get().getElementById("menuProfile").setAttribute("class", "dropdown-menu dropdown-user pull-right");
			
		}
		
	}
	
	private static void logout(ClickEvent e){
		Cookies.removeCookie("sid");
	}
	public static void showSearch(){
		Document.get().getElementById("topbar-search").setAttribute("style", "visibility:visible");
	}
	
	public static void hideSearch(){
		Document.get().getElementById("topbar-search").setAttribute("style", "visibility:hidden");
	}
}


	