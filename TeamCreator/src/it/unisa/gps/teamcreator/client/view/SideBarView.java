package it.unisa.gps.teamcreator.client.view;


import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import it.unisa.gps.teamcreator.client.event.NewTaskEvent;
import it.unisa.gps.teamcreator.client.event.SearchTaskEvent;
import it.unisa.gps.teamcreator.client.presenter.DashboardPresenter;


public final class SideBarView extends Composite implements DashboardPresenter.Display{

	// The @UiField annotation tags elements that the widget should
	// maintain references too.

	private static HandlerManager eventBus;
	@UiField  Button newTaskButton;

	@UiField Button searchTaskButton;

	Widget widgetDaCancellare;

	public SideBarView(HandlerManager events) {

		eventBus = events;
		initWidget(binder.createAndBindUi(this));

		newTaskButton.getElement().setAttribute("style","background:none; border:none;");
		searchTaskButton.getElement().setAttribute("style","background:none; border:none;");

		bind();
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



	private void bind() {

		newTaskButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new NewTaskEvent());

			}
		});

		searchTaskButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new SearchTaskEvent());

			}
		});
	}


	private static final Binder binder = GWT.create(Binder.class);
	interface Binder extends UiBinder<Widget, SideBarView> {}



	public Widget asWidget() {
		return this;
	}



	@Override
	public HasClickHandlers getNewTaskButton() {
		// TODO Auto-generated method stub
		return  newTaskButton;
	}


	@Override
	public HTMLPanel rootDashboad() {
		// TODO Auto-generated method stub
		return null;
	}



	public static void shiftMenu(){

		if (Document.get().getElementById("sidebar").getAttribute("style").equals("")){
			Document.get().getElementById("page-wrapper").setAttribute("style", "margin:0 0 0 50px;");
			Document.get().getElementById("sidebar").setAttribute("style", "width:50px;");

			NodeList<Element> listaLI = Document.get().getElementById("side-menu").getElementsByTagName("li");

			for (int i=0;i<listaLI.getLength();i++){
				Element span =  listaLI.getItem(i).getElementsByTagName("a").getItem(0).getElementsByTagName("span").getItem(0);
				span.setAttribute("style","display:none;");
			}
		}
		else{
			Document.get().getElementById("page-wrapper").setAttribute("style", "margin:0 0 0 250px;");

			Document.get().getElementById("sidebar").setAttribute("style", "");
			NodeList<Element> listaLI = Document.get().getElementById("side-menu").getElementsByTagName("li");

			for (int i=0;i<listaLI.getLength();i++){
				Element span =  listaLI.getItem(i).getElementsByTagName("a").getItem(0).getElementsByTagName("span").getItem(0);
				span.setAttribute("style","display:inline;");
			}
		}
	}
}
