package it.unisa.gps.teamcreator.client.view;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import it.unisa.gps.teamcreator.client.GreetingServiceAsync;
import it.unisa.gps.teamcreator.client.presenter.SearchTaskPresenter;
import it.unisa.gps.teamcreator.shared.Task;

public final class SearchTaskView extends Composite implements SearchTaskPresenter.Display {

	// The @UiField annotation tags elements that the widget should
	// maintain references too.

	
	@UiField DialogBox dialogBox;
	@UiField TextBox skillDialog;
	@UiField Button cancelButton;
	@UiField Button okButton;
	@UiField DivElement Loading;

	private static ArrayList<Widget> widgets= new ArrayList<Widget>();
	private Task task;
	private static HTMLPanel p;
	private final GreetingServiceAsync rpcService;
	private static boolean showWorker=false;
	private static boolean showDetail=false;
	
	public SearchTaskView(GreetingServiceAsync service) {
		rpcService = service;
	
		initWidget(binder.createAndBindUi(this));
		this.getElement().setAttribute("id", "page-wrapper");
		loading(Loading);
		
	}	
	//	Window.alert(Document.get().getElementById("prova").getInnerText());
		//btnAddSkill.getElement().setAttribute("class","btn btn-primary");
		//btnNextButton.getElement().setAttribute("class", "btn btn-green btn-block");
		//btnFinish.getElement().setAttribute("class", "btn btn-green btn-block");
		//btnNextButton2.getElement().setAttribute("class", "btn btn-green btn-block");
		//		btnFirstListItem.getElement().setAttribute("data-toggle","tab");
		//		btnFirstListItem.getElement().setAttribute("style","background:none; border:none;");


		//Panel.setHTML(toAdd);
//		HTMLTask w;
//		VerticalPanel vp= new VerticalPanel();
//		
//		for(int i=0;i<4;i++)
//			{
//			w=new HTMLTask("Progetto","Di prova","GWT",i);
//			w.showWidget();
//			widgets.add(w);
//			vp.add(w);
//			vp.setCellWidth(w, "1700");
//			
//			
//		}
//		Panel.add(vp);
//		p=Panel;
//	}
//	
//	public static void showDetail(int param1)
//	{
//		if(!showDetail)
//		{Widget w= widgets.get(param1);
//		p.clear();
//		p.setPixelSize(1600, 1500);
//		p.add(w);
//		String s="<div class='col-lg-4'><div class='jumbotron'><h1>Jumbotron</h1><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum tincidunt est vitae ultrices accumsan. Aliquam ornare lacus adipiscing, posuere lectus et, fringilla augue. Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p><p><a role='button' class='btn btn-primary btn-lg'>Learn more</a></p></div></div>";
//		HTML jumbo = new HTML(s);
//		jumbo.setWidth("100%");
//		p.add(jumbo);
//		showDetail=true;
//		}
//		else
//		{
//
//			p.clear();
//			VerticalPanel vp= new VerticalPanel();
//			for(int i=0;i<widgets.size();i++)
//				{vp.add(widgets.get(i));
//				vp.setCellWidth(widgets.get(i), "1700");
//				}
//			p.add(vp);
//			showDetail=false;
//		}
//	}
//	
//	public static void showWorker(int param1){
//		if(!showWorker)
//			{Widget w= widgets.get(param1);
//			p.clear();
//			p.setPixelSize(1600, 1500);
//			HorizontalPanel hp= new com.google.gwt.user.client.ui.HorizontalPanel();
//			
//			VerticalPanel vp = new VerticalPanel();
//			VerticalPanel vp2 = new VerticalPanel();
//			HTMLWorker h=new HTMLWorker("GIANLUCA", "LANDI","CHALET");
//			HTMLWorker h1= new HTMLWorker("Gab","Nap","Chalet");
//			HTMLWorker h2= new HTMLWorker("Gab","Nap","Chalet");
//			HTMLWorker h3=new HTMLWorker("GIANLUCA", "LANDI","CHALET");
//			h.showWidget();
//			h.setWidth("300%");
//			h1.setWidth("300%");
//			h2.setWidth("300%");
//			h3.setWidth("300%");
//			h1.showWidget();
//			h2.showWidget();
//			h3.showWidget();
//			vp.add(h);
//			vp.add(h1);
//			vp.add(h2);
//			vp.add(h3);
//			vp.setCellWidth(h, "500");
//			vp.setCellWidth(h1, "500");
//			vp.setCellWidth(h2, "500");
//			vp.setCellWidth(h3, "500");
//		
//			
//			p.add(w);
//			hp.add(vp);
//			p.add(hp);
//			
//			vp.setWidth("500");
//			vp2.setWidth("1000");
//			
//			
//			showWorker=true;
//			}
//		else
//			{
//				p.clear();
//				VerticalPanel vp= new VerticalPanel();
//				for(int i=0;i<widgets.size();i++)
//					{vp.add(widgets.get(i));
//					vp.setCellWidth(widgets.get(i), "1700");
//					}
//				p.add(vp);
//				showWorker=false;
//			}
//		}
//	


	
	public  native void loading(com.google.gwt.dom.client.Element element) /*-{


	 
	
	  var id = 'loader', fill = '#999999',
	      size = 20, radius = 3, duration = 1000,
	      maxOpacity = 0.6, minOpacity = 0.15;
	  text='<svg id="'+id+'" width="'+(size*3.5)+'" height="'+size+'" >' + 
	   		'<rect width="'+size+'" height="'+size+'" x="0" y="0" rx="'+radius+'" ry="'+radius+'" fill="'+fill+'" fill-opacity="'+maxOpacity+'">' + 
	   			'<animate attributeName="opacity" values="1;'+minOpacity+';1" dur="'+duration+'ms" repeatCount="indefinite"/>' + 
	   		'</rect>' + 
	    	'<rect width="'+size+'" height="'+size+'" x="'+(size*1.25)+'" y="0" rx="'+radius+'" ry="'+radius+'" fill="'+fill+'" fill-opacity="'+maxOpacity+'">' + 
	    		'<animate attributeName="opacity" values="1;'+minOpacity+';1" dur="'+duration+'ms" begin="'+(duration/4)+'ms" repeatCount="indefinite"/>' + 
	    	'</rect>' + 
	    	'<rect width="'+size+'" height="'+size+'" x="'+(size*2.5)+'" y="0" rx="'+radius+'" ry="'+radius+'" fill="'+fill+'" fill-opacity="'+maxOpacity+'">' + 
	    		'<animate attributeName="opacity" values="1;'+minOpacity+';1" dur="'+duration+'ms" begin="'+(duration/2)+'ms" repeatCount="indefinite"/>' + 
	    	'</rect>' + 
	   	'</svg>';
	   	
	var node = document.createElement("div");
	node.setAttribute("id","divLoading");
	node.setAttribute("style","position:absolute; width:100%; height:0px; top:10%;")
	//node.setAttribute("class", "clearfix");

//var div = document.createElement('div');
	//node.appendChild(div);
	node.innerHTML = text;

//var textnode = document.createTextNode(text);  
//node.appendChild(textnode); 
	element.appendChild(node);
	   	


}-*/;	

	
	private static final Binder binder = GWT.create(Binder.class);
	interface Binder extends UiBinder<Widget, SearchTaskView> {}
	



}