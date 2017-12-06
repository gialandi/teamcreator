package it.unisa.gps.teamcreator.client.view;


import java.lang.annotation.Native;
import java.util.ArrayList;
import java.util.List;

import it.unisa.gps.teamcreator.client.GreetingService;
import it.unisa.gps.teamcreator.client.GreetingServiceAsync;
import it.unisa.gps.teamcreator.client.LoginService;
import it.unisa.gps.teamcreator.client.presenter.CoWorkersPresenter;
import it.unisa.gps.teamcreator.shared.Competence;
import it.unisa.gps.teamcreator.shared.ListCompetenceWithNumber;
import it.unisa.gps.teamcreator.shared.Worker;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;


public final class CoWorkersView extends Composite implements CoWorkersPresenter.Display{



	@UiField DivElement pageContent;
	// The @UiField annotation tags elements that the widget should
	// maintain references too.
	private static List<Worker> CoWorkers;
	private static final GreetingServiceAsync rpcService =  GWT.create(GreetingService.class);;

	public CoWorkersView() {
		initWidget(binder.createAndBindUi(this));
		this.getElement().setAttribute("id", "page-wrapper");
		//    sendButton.addDomHandler(
		//            new MouseOverHandler() {
		//              @Override
		//              public void onMouseOver(MouseOverEvent event) {
		//              Window.alert("DOM");
		//              }
		//            },
		//            MouseOverEvent.getType());



		registerAddCompetence();
		loading(pageContent);
	}



	static final Binder binder = GWT.create(Binder.class);
	interface Binder extends UiBinder<Widget, CoWorkersView> {}



	public Widget asWidget(){
		return this;
	}



	public void disp(String nome, String cognome, String immagine, String id_worker){

		//dispJavascript( nome, cognome, immagine, id_worker);


		String inn ="<figure><img id='"+id_worker+"' src='"+immagine+"' alt='' style='display: inline-block' class='img-responsive img-circle' onclick='view3(this.id)'></img><span>"+nome+" "+cognome+"</span></figure>";
		DivElement div = Document.get().createDivElement();
		div.setAttribute("class","col-xs-12 col-sm-4 text-center");
		div.setAttribute("id", "ricerca"+id_worker);
		div.setAttribute("style","display: block;width:150px");
		div.setInnerHTML(inn);
		Document.get().getElementById("fonso").appendChild(div);

		
	}

	private static native void registerAddCompetence()/*-{
	$wnd.view3=@it.unisa.gps.teamcreator.client.view.CoWorkersView::view3(*);
	$wnd.evaluate10=@it.unisa.gps.teamcreator.client.view.CoWorkersView::evaluate10(*);
	$wnd.showSearch=@it.unisa.gps.teamcreator.client.view.HeaderView::showSearch(*);

}-*/;

	public static void view3(String id_worker){


		DivElement page =  Document.get().getElementById("raffaele").cast();//getClassName("row mbl").cast();
		if (Document.get().getElementById("skillcont")!=null)
		{ //rimuovo il div delle skills precedenti
			Element el = Document.get().getElementById("skillcont");
			el.getParentNode().removeChild( el );
		}
		//creo div valutazione skills
		Element divskill=create_div_skill();
		//creare l'elemento
		page.appendChild(divskill);

		//quante skills ho?
		//var n=get_n_s(idw);


		//inserisco le varie skills
		Element list=Document.get().getElementById("lista");

		Worker coWorker = null;
		for (Worker w : CoWorkers){
			if (w.getUri().equals(id_worker.trim())){
				coWorker = w;
				break;
			}
		}

		int i=0;
		for (Competence c : coWorker.getCompetences()){
			list.appendChild(get_skills(c.getName(),i));
			i++;
		}
	}

	private static native Element getClassName(String c) /*-{
		var page=document.getElementsByClassName(c)[0];
		alert(c);
		return page;
	}-*/;



	//visualizza il div per valutare il worker
	public static native void view2(String idCoWorker) /*-{
		alert(idCoWorker);
		//visualizza il div per valutare il worker
		var page=document.getElementsByClassName("row mbl")[0];
		if (document.getElementById("skillcont")!=null)
		{ //rimuovo il div delle skills precedenti
		var el = document.getElementById("skillcont");
		el.parentNode.removeChild( el );
		}
		//creo div valutazione skills
		var divskill=create_div_skill();
		//creare l'elemento
		page.appendChild(divskill);

		//quante skills ho?
		//var n=get_n_s(idw);


		//inserisco le varie skills
		list=document.getElementById("lista");


		for (i=0;i<competenze.length;i++){
		list.appendChild(get_skills(competenze,i));

		}


}-*/;



	@Override
	public  void setCoWorkers(List<Worker> array) {
		CoWorkers = array;

	}	


	//la funzione 
	public static Element create_div_skill(){
		Element div_col_lg_otto=Document.get().createElement("div");
		div_col_lg_otto.setAttribute("id","skillcont");
		div_col_lg_otto.setAttribute("class","col-lg-8");
		div_col_lg_otto.setInnerHTML("<div class='portlet box' style='height: 580px;'> <div class='portlet-header'> <div class='caption'> Skills</div> </div> <div class='portlet-body' style='overflow: auto;height:93%;'> <div class='slimScrollDiv' style='position: relative; overflow: hidden; width: 100%; height: 90%;'> <ul id='lista' class='todo-list sortable ui-sortable' style='width:100%; height:100%;' id='idListSkills'> </ul> <div class='slimScrollBar' style='background-color: rgb(0, 0, 0); width: 7px; position: absolute; top: 0px; opacity: 0.4; display: none; border-top-left-radius: 7px; border-top-right-radius: 7px; border-bottom-right-radius: 7px; border-bottom-left-radius: 7px; z-index: 99; right: 1px; height: 184.9112426035503px; background-position: initial initial; background-repeat: initial initial;'></div> <div class='slimScrollRail' style='width: 7px; height: 100%; position: absolute; top: 0px; display: none; border-top-left-radius: 7px; border-top-right-radius: 7px; border-bottom-right-radius: 7px; border-bottom-left-radius: 7px; background-color: rgb(51, 51, 51); opacity: 0.2; z-index: 90; right: 1px; background-position: initial initial; background-repeat: initial initial;'></div> </div> <br> <button onclick='evaluate10()' type='button' class='btn btn-yellow btn-block'>Evaluate</button></div></div>");

		return div_col_lg_otto;
	}



	public static  Element get_skills(String comp,int y){
		Element li=Document.get().createElement("li");
		li.setAttribute("class","clearfix");
		Element divgenerico=Document.get().createElement("div");
		divgenerico.setAttribute("id",comp); // id della skill è il nome della skill
		li.appendChild(divgenerico);
		Element drag_n_drop=Document.get().createElement("span");
		drag_n_drop.setAttribute("class","drag-drop");
		divgenerico.appendChild(drag_n_drop);
		Element i=Document.get().createElement("i");
		drag_n_drop.appendChild(i);
		Element todocheck=Document.get().createElement("div");
		todocheck.setAttribute("class","todo-check pull-left");
		todocheck.setAttribute("style","width:50%;");
		
		divgenerico.appendChild(todocheck);
		//elemento dove bisogna inserire la skill y prelevata dalla lista comp
		Element title=Document.get().createElement("div");
		title.setAttribute("class","todo-title");
		title.setAttribute("style","width:100%;");
		title.setInnerHTML(comp);
		todocheck.appendChild(title);
		Element actions=Document.get().createElement("div");
		actions.setAttribute("class","todo-actions pull-right clearfix");
		todocheck.appendChild(actions);
		Element figcaption=Document.get().createElement("figcaption");
		figcaption.setAttribute("class","ratings");
		figcaption.setAttribute("nameSkill",comp);
		figcaption.setAttribute("valore","0");
		figcaption.setAttribute("style","display:inline;");
		actions.appendChild(figcaption);
		for (int j=0;j<=4;j++){
			figcaption.appendChild(get_star(j,y));
		}
		return li;
	}


	public static Element get_star(int indice,int isk){
		Element ancora=Document.get().createElement("a");
		ancora.setAttribute("href","#");
		ancora.setInnerHTML("<span id='skill_"+isk+'_'+indice+"' class='fa fa-star-o' onclick='stars(this.id); return false'></span>");

		return ancora;
	}


	public static void evaluate10(){

		NodeList<Element> listaSkill = Document.get().getElementsByTagName("figcaption");
		List<Competence> competences = new ArrayList<Competence>();
		Competence c =null;
		for (int i = 0; i < listaSkill.getLength(); i++) {
			if (!listaSkill.getItem(i).getAttribute("valore").equals("0")){
				//Window.alert(listaSkill.getItem(i).getAttribute("nameskill")+" "+listaSkill.getItem(i).getAttribute("valore"));
				c = new Competence();
				c.setName(listaSkill.getItem(i).getAttribute("nameskill"));
				c.setRating(Double.parseDouble(listaSkill.getItem(i).getAttribute("valore")));
				competences.add(c);
			}
		}

		rpcService.releaseFeedback(LoginService.Util.getLoggedUser().getUri(), competences, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				Window.alert("Done ");				
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("Errot");				
			}
		});
	}

	public  static void prova10(String testo){

		if(Document.get().getElementById("skillcont") != null){
			Document.get().getElementById("skillcont").setAttribute("style", "visibility:hidden;");
		}
		NodeList<Element> divs = Document.get().getElementById("fonso").getElementsByTagName("div");

		for(int i=0; i<divs.getLength();i++){
			DivElement div = (DivElement) divs.getItem(i);
			if (!div.getAttribute("id").equals("")){
				
				NodeList<Element> spans = div.getElementsByTagName("span");
				String nameWorker = spans.getItem(0).getInnerText().trim();
				if (!nameWorker.contains(testo)){
					div.setAttribute("style", "display:none");
				}
				else {
					div.setAttribute("style", "display:block; width:150px");
				}
			}
		}

	}

	
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
}


