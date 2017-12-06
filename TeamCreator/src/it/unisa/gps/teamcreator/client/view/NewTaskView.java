package it.unisa.gps.teamcreator.client.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.ParseConversionEvent;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
//GAB
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.FormElement;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.dom.client.TableElement;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

import it.unisa.gps.teamcreator.client.GreetingServiceAsync;
import it.unisa.gps.teamcreator.client.LoginService;
import it.unisa.gps.teamcreator.client.presenter.NewTaskPresenter;
import it.unisa.gps.teamcreator.shared.Competence;
import it.unisa.gps.teamcreator.shared.ListCompetenceWithNumber;
import it.unisa.gps.teamcreator.shared.Task;
import it.unisa.gps.teamcreator.shared.Topic;
import it.unisa.gps.teamcreator.shared.Worker;

public final class NewTaskView extends Composite implements NewTaskPresenter.Display{

	//private static String[] listSkills;
	// The @UiField annotation tags elements that the widget should
	// maintain references too.
	@UiField Button btnNextButton;
	@UiField Button btnNextButton2;
	@UiField Button btnFinish;
	@UiField Anchor btnFirstListItem;
	@UiField Anchor btnThirdListItem;

	@UiField InputElement nameTask;

	@UiField TextAreaElement describeTask;

	@UiField SpanElement cellNameTask;
	@UiField SpanElement cellTaskDescription;
	@UiField SpanElement cellTeamMembers;
	//@UiField HTMLPanel provaGWT;

	@UiField Button btnAddSkill;

	@UiField TableElement tableOverview;

	@UiField UListElement idListSkills;

	private Task task;
	private static Map<String,ListCompetenceWithNumber> map;
	private static Map<String,Integer> mapCompetencesUser  = new java.util.HashMap<String,Integer>();
	private final static Map<String,Integer>  mapCompetencesWikipedia  = new java.util.HashMap<String,Integer>();
	private static Map<String, Integer> mapManuallyInsertedCompetences;

	//private static List<Worker> listWorkers;
	private static final int WM_INTERVAL = 3000;

	private final GreetingServiceAsync rpcService;
	private List<Topic> topicByWikipediaMiner;


	public NewTaskView(GreetingServiceAsync service) {
		rpcService = service;

		initWidget(binder.createAndBindUi(this));
		this.getElement().setAttribute("id", "page-wrapper");
		btnAddSkill.getElement().setAttribute("class","btn btn-primary");
		btnNextButton.getElement().setAttribute("class", "btn btn-green btn-block");
		btnFinish.getElement().setAttribute("class", "btn btn-green btn-block");
		btnNextButton2.getElement().setAttribute("class", "btn btn-green btn-block");
		//		btnFirstListItem.getElement().setAttribute("data-toggle","tab");
		//		btnFirstListItem.getElement().setAttribute("style","background:none; border:none;");


		task = new  Task();
		registerAddCompetence();

		mapManuallyInsertedCompetences = new java.util.HashMap<String, Integer>();

		Timer timer = new Timer(){
			String oldDescription = "";

			@Override
			public void  run() {

				String newDescription = describeTask.getValue();
				if(!oldDescription.equals(newDescription)) {



					NodeList<Element> listItems = idListSkills.getElementsByTagName("li");
					int x = listItems.getLength()-1;
					while(listItems.getLength()>1){
						listItems.getItem(x).removeFromParent();
						x--;
					}
					rpcService.getTopics(newDescription, new AsyncCallback<List<Topic>>() {

						@Override
						public void onSuccess(List<Topic> result) {


							topicByWikipediaMiner = result;

							for (Topic skill : topicByWikipediaMiner){
								addListItem(idListSkills, skill.getTitle(),"1");
							}
							for(Entry<String, Integer> entry : mapManuallyInsertedCompetences.entrySet()){
								addListItem(idListSkills, entry.getKey(),""+entry.getValue());
							}


							//competenceList.setInnerHTML(html);
						}



						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub

						}
					});
					oldDescription = newDescription;
				}
			}
		};
		timer.scheduleRepeating(WM_INTERVAL);

		idListSkills.setAttribute("id", "idListSkills");
	}


	private void setRatingCompetence (String competence,Integer rating){

		mapCompetencesUser.put(competence, rating);


	}



	private static final Binder binder = GWT.create(Binder.class);
	interface Binder extends UiBinder<Widget, NewTaskView> {}





	@UiHandler("btnAddSkill")
	void onAddSkill(ClickEvent e){
		if (Document.get().getElementById("idInputSkill")==null)
			insertSkill(Document.get().getElementById("idListSkills"));
		else {
			Window.alert("Insert Skill !!!");
		}




	}




	@UiHandler("btnSecondListItem")
	void SecondListItme(ClickEvent e){

		Document.get().getElementById("tab-edit").setAttribute("style", "display:none;");
		Document.get().getElementById("tab-overview").setAttribute("style", "display:none;");

		//Document.get().getElementById("secondListItem").setAttribute("style", "visibility:visible;");
		Document.get().getElementById("secondListItem").setAttribute("class", "active");

		Document.get().getElementById("firstListItem").setAttribute("class", "");
		Document.get().getElementById("thirdListItem").setAttribute("class", "");

		Document.get().getElementById("tab-messages").setAttribute("style", "display:block;");

	}

	@UiHandler("btnThirdListItem")
	void ThirdistItme(ClickEvent e){

		Document.get().getElementById("tab-edit").setAttribute("style", "display:none;");
		Document.get().getElementById("tab-messages").setAttribute("style", "display:none;");

		//Document.get().getElementById("secondListItem").setAttribute("style", "visibility:visible;");
		Document.get().getElementById("thirdListItem").setAttribute("class", "active");

		Document.get().getElementById("firstListItem").setAttribute("class", "");
		Document.get().getElementById("secondListItem").setAttribute("class", "");

		Document.get().getElementById("tab-overview").setAttribute("style", "display:block;");

		setTask();
		setOverview();
	}



	@UiHandler("btnFirstListItem")
	void FirstListItme(ClickEvent e){
		Document.get().getElementById("tab-overview").setAttribute("style", "display:none;");
		Document.get().getElementById("tab-messages").setAttribute("style", "display:none;");

		Document.get().getElementById("tab-edit").setAttribute("style", "display:block;");

		//Document.get().getElementById("secondListItem").setAttribute("style", "visibility:visible;");
		Document.get().getElementById("secondListItem").setAttribute("class", "");
		Document.get().getElementById("thirdListItem").setAttribute("class", "");

		Document.get().getElementById("firstListItem").setAttribute("class", "active");



	}



	@UiHandler("btnNextButton")
	void handleClick(ClickEvent e){


		Document.get().getElementById("ulSkillTeam").removeAllChildren();


		Document.get().getElementById("tab-edit").setAttribute("style", "display:none;");

		Document.get().getElementById("secondListItem").setAttribute("style", "visibility:visible;");
		Document.get().getElementById("secondListItem").setAttribute("class", "active");

		Document.get().getElementById("firstListItem").setAttribute("class", "");

		Document.get().getElementById("tab-messages").setAttribute("style", "display:block;");




		ListCompetenceWithNumber elenco ;


		map = new HashMap<String,ListCompetenceWithNumber>();

		NodeList<LIElement> LI = Document.get().getElementById("idListSkills").getElementsByTagName("LI").cast();
		for (int i=1; i<LI.getLength();i++){
			elenco = new ListCompetenceWithNumber();
			SelectElement number = LI.getItem(i).getElementsByTagName("select").getItem(0).cast();
			Element skill = (LI.getItem(i).getElementsByTagName("listSkills").getItem(0));
			number.getValue().trim();


			elenco.setNumberWorkerForSkillSelect(Integer.parseInt(number.getValue().trim()));
			elenco.setNameCompetence(skill.getInnerText().trim());
			//Window.alert("Skill "+ elenco.getNameCompetence() +"Numero "+ ele);
			map.put(skill.getInnerText().trim(), elenco);
		}





		//task.setListSkills(listCompetence);

		final List<Integer> listPersonXCompetence = new ArrayList<Integer>();

		NodeList<SelectElement> numberPersonForSkill = Document.get().getElementsByTagName("select").cast();

		int x ;
		//Creo la lista di NumWorkers per competence
		for (int i=0; i<numberPersonForSkill.getLength();i++){
			x = Integer.parseInt(numberPersonForSkill.getItem(i).getValue().trim());
			listPersonXCompetence.add(x);

		}

		setTask(); // set l'oggetto task che andrà nella overview 

		List<String> lista22 = task.getListSkills();


		List<List<String>> forbbiden = new ArrayList<List<String>>();

		loading(Document.get().getElementById("laoding"));
		rpcService.getWorkersWithCompetences(lista22, listPersonXCompetence, forbbiden, new AsyncCallback<Map<String,List<Worker>>>() {

			@Override
			public void onSuccess(Map<String, List<Worker>> result) {
				// TODO Auto-generated method stub

				List<Worker> lista;

				for (String skill : result.keySet()){
					//id ul = ulSkillTeam
					lista = result.get(skill);


					skill = skill.trim();
					skill.replace(" ", "_");

					//Window.alert("Skill :"+skill+" Numero persone: "+lista.size());
					map.get(skill).setListWorker(lista);

				}


				Document.get().getElementById("divLoading").removeFromParent();
				for (String skill : map.keySet()){
					ListCompetenceWithNumber list = map.get(skill);


					int nPersonPerSkill = list.getNumberWorkerForSkillSelect();

					insertSkillListTeam(Document.get().getElementById("ulSkillTeam"),skill,nPersonPerSkill,list.getListWorker().size());

					for (Worker w : list.getListWorker()){
						if (nPersonPerSkill > 0){
							addWorkers(Document.get().getElementById(skill), w.getUri(), w.getFirstname(),w.getSurname(),skill,w.getImagePath());
							nPersonPerSkill --;
						}
						else break;
					}

				}


			}
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
	}




	public static native void addWorker(com.google.gwt.dom.client.Element  form ,String s) /*-{

  	var node = document.createElement("div");
	//node.setAttribute("class", "clearfix");
    var text =  s;

    //var div = document.createElement('div');
	//node.appendChild(div);
	node.innerHTML = text;

//  var textnode = document.createTextNode(text);  
//  node.appendChild(textnode); 
  	form.appendChild(node);
}-*/;	



	private void setTask() {

		task.setNameTask(nameTask.getValue());  // Nome Task 


		task.setDescription(describeTask.getValue()); // Descrizione del task


		List<String> skills = new  ArrayList<String>(); 

		NodeList<Element> listCompetence = Document.get().getElementsByTagName("listSkills");

		for (int i=0 ; i <  listCompetence.getLength();i++){

			skills.add(listCompetence.getItem(i).getInnerText());
		}

		task.setListSkills(skills);		// Lista skill per il task
	}


	private void setOverview() {


		cellNameTask.setInnerHTML(task.getNameTask());
		cellTaskDescription.setInnerHTML(task.getDescription());



		List<Worker> workerHire = new ArrayList<Worker>();
		int w=0;
		for (String skill : map.keySet()){
			NodeList<Element> uriWorkers = Document.get().getElementById(skill).getElementsByTagName("worker");

			for (int i=0; i<uriWorkers.getLength();i++){

				String uri = uriWorkers.getItem(i).getAttribute("value");
				workerHire.add(map.get(skill).getWorker(uri));

				w++;
			}

		}

		task.setListWorkers(workerHire);
		cellTeamMembers.setInnerHTML(""+task.getListWorkers().size());

		tableOverview.removeAllChildren();
		for (Worker worker : task.getListWorkers()){
			TableRowElement tr = tableOverview.insertRow(0);
			TableCellElement td = tr.insertCell(0) ;
			TableCellElement td2 = tr.insertCell(0) ;
			td.setInnerText(worker.getFirstname()+" "+worker.getSurname());

			td2.setInnerText("SKILL");

			//tableOverview.appendChild(tr);
		}
	}

	@UiHandler("btnNextButton2")
	void btnNext2(ClickEvent e){



		Document.get().getElementById("tab-edit").setAttribute("style", "display:none;");
		Document.get().getElementById("tab-messages").setAttribute("style", "display:none;");

		Document.get().getElementById("tab-overview").setAttribute("style", "display:block;");

		Document.get().getElementById("firstListItem").setAttribute("class", "");

		Document.get().getElementById("secondListItem").setAttribute("class", "");

		Document.get().getElementById("thirdListItem").setAttribute("style", "visibility:visible;");

		Document.get().getElementById("thirdListItem").setAttribute("class", "active");


		setOverview();

	}
	public Widget asWidget() {
		return this;
	}





	@Override
	public HasClickHandlers getFinishButton() {
		// TODO Auto-generated method stub
		return btnNextButton;
	}





	public static native void addListItem(com.google.gwt.dom.client.Element ul, String valueSkill, String numPerson) /*-{



	  	var node = document.createElement("LI");
		node.setAttribute("class", "clearfix");
	    var text = "<span class='drag-drop'> <i></i> </span> <div class='todo-check pull-left'></div>	"
	    		+"  <div class='todo-title'style='width:100%'>"
	    		+"		<span style='margin-right:15%'>"
	    		+"			<numPerson value='"+numPerson+"'>"
	    		+"								<select class='form-control' style='width:15%; display:inline-block;' > ";
	    											for (i=1; i<=3;i++){
	    												if ( i == numPerson)
	    													text =text+ "<option selected='"+i+"'>"+i+"</option> ";
	    												else 
	    													text =text+ "<option>"+i+"</option> ";
	    											}


				text=text+"</select>"
	    		+"			</numPerson>"
	    		+"		</span>"
	    		+"		<span style=''>"
	    		+"			<listSkills value='"+ valueSkill +"'>"+valueSkill+"</listSkills>"
	    		+"		</span> </div> ";

		text=text+"<script> function onRemoveSkill(e){ e.parentNode.parentNode.removeChild(e.parentNode) } </script>";

	    text=text +"<div class='todo-actions pull-right clearfix'><a class='todo-remove' onClick='onRemoveSkill(this)'> <i class='fa fa-trash-o'> </i> </i></a></div>";
		 var div = document.createElement('div');
		 node.appendChild(div);
  			div.innerHTML = text;

//	  	var textnode = document.createTextNode(text);  
//	  	node.appendChild(textnode); 
//		ul.insertBefore(node, ul.childNodes[2]);
		ul.appendChild(node);

	}-*/;	



	public static native void insertSkill(com.google.gwt.dom.client.Element ul) /*-{



  	var node = document.createElement("LI");
  	node.setAttribute("id","idInputSkill");
	node.setAttribute("class", "clearfix");
	node.setAttribute("style"," background-color: rgba(182, 131, 73, 0.34); border-color: #ebccd1;color: #a94442");
    var text = " <div class='todo-check pull-left'>	<div class='todo-title'> "



                +"                            <div class='input-group' style='width:250%;'>"
				+"								<select id='inputSelectNumPerson' class='form-control' style='width:30%; margin-right:5%;' > "
				+"									<option>1</option> "
				+"									<option>2</option> "
				+"									<option>3</option> "
				+"									 </select>"
                +"                              <input  type='text' id='inputDialog' style='width:65%;' autofocus placeholder='Insert skill here...' value='' class='form-control'></input> <span id='btn-chat' class='input-group-btn'>"

                +"								<button onclick='onAddSkill()'type='button' class='btn btn-green'>"
                +"                                       <span class='fa fa-plus man'></span>"
                +"                                    </button>"
                 +"								<button onclick='onCancel()'type='button' class='btn btn-green'>"
                +"                                    <i class='fa fa-trash-o'> </i>"
                +"                                    </button>"

                +"                            </div>"

    +"</div> ";

    text=text +"<div class='todo-actions pull-right clearfix'></div>";
	 var div = document.createElement('div');
	 node.appendChild(div);
	 div.innerHTML = text;

//  	var textnode = document.createTextNode(text);  
//  	node.appendChild(textnode); 

	ul.insertBefore(node, ul.childNodes[2]);
  	//ul.appendChild(node);


}-*/;

	private static native void registerAddCompetence()/*-{
	$wnd.onAddSkill=@it.unisa.gps.teamcreator.client.view.NewTaskView::addSkill(*);
	$wnd.onCancel=@it.unisa.gps.teamcreator.client.view.NewTaskView::cancelSkill(*);
	$wnd.onHire=@it.unisa.gps.teamcreator.client.view.NewTaskView::hireWorker(*);
	$wnd.onProfile=@it.unisa.gps.teamcreator.client.view.NewTaskView::profileWorker(*);
	$wnd.removeProfile=@it.unisa.gps.teamcreator.client.view.NewTaskView::removeProfile(*);
	//$wnd.onRemoveSkill=@it.unisa.gps.teamcreator.client.view.NewTaskView::removeSkill(*)(param1);
}-*/;


	public static void removeSkill(){




		Document.get().getElementById("idInputSkill").removeFromParent();

		Window.alert("REMOVE");


	}
	public static void cancelSkill(){




		Document.get().getElementById("idInputSkill").removeFromParent();


	}

	public static void addSkill(){


		InputElement input = Document.get().getElementById("inputDialog").cast();

		SelectElement select = Document.get().getElementById("inputSelectNumPerson").cast();

		if (input.getValue().equals("")){
			Window.alert("Insert Skill");
		}
		else {
			Document.get().getElementById("idInputSkill").removeFromParent();
			mapManuallyInsertedCompetences.put(input.getValue(),Integer.parseInt(select.getValue()) );
			addListItem(Document.get().getElementById("idListSkills"),input.getValue(),select.getValue());
		}
	}









	/** SEZIONE SUGGERIMENTO TEAM */

	//id ul = ulSkillTeam
	public static native void insertSkillListTeam(com.google.gwt.dom.client.Element ul, String skill, int numSelect, int numTot) /*-{



		  	var node = document.createElement("LI");
			node.setAttribute("class", "clearfix");
			node.setAttribute("id", skill);
		    var text = "<li class='clearfix'> "
		    				+"<div class='page-title-breadcrumb option-demo' style='  color:white;   background-color: rgba(62, 119, 171, 0.7);'> "	
		    				+"			 <div class='page-header'>"
		    				+"				<div class='page-title mrm'>"+skill+"</div>"
		    				+"				<div id='cont"+skill+"' class='page-title' style='float:right;'>"+numSelect+"/"+ numTot+"</div> 	"	
		    				+"    	    </div> "
		    				+"	 	</div>"
		    				+"  	<br></br>" 	
//		    				+"		<div  class='form-actions text-right pal'>		"
//		    				+"		<button class='btn btn-primary'> other </button>	"	
//		    				+"		</div>"							
		    				+"	</li>";


		    var div = document.createElement('div');
			 node.appendChild(div);
					div.innerHTML = text;

		//  	var textnode = document.createTextNode(text);  
		//  	node.appendChild(textnode); 
		//	ul.insertBefore(node, ul.childNodes[0]);
		  	ul.appendChild(node);


}-*/;	


	/**
	 * 
	 *Aggiunge per ogni list item (per ogni skill) la lista dei worker suggeriti 
	 * @param li (id list item)
	 * @param 
	 */
	public static native void addWorkers(com.google.gwt.dom.client.Element li,  String idWorker, String nameWorker,String surname, String nameSkill, String pathImage) /*-{


//for (i = 0; i < listWorkers.length; i++) { 	 	

  	var node = document.createElement("div");
	//node.setAttribute("id", listWorkers[i]);

	var text ="<worker value='"+idWorker+"'>"
     	text =text+ "<"+idWorker+">"
    +"		<div  class='col-lg-4'>"
				+"	<div class='panel '>"
		+"				<div class='panel-body '>"
			+"					<div class='profile '>"
				+"					<div style='margin-bottom: 15px ' class='row '>"
					+" 						<div class='col-xs-12 col-sm-8 '>"
						+"							<h3>"+nameWorker+"<br> "+surname+"</h3>"
						+"					</div>"
					+"				<div class='col-xs-12 col-sm-4 text-center '>"
						+"					<figure>"
						+"						<img src='"+pathImage+" ' alt=' 'style='display: inline-block ' class='img-responsive img-circle ' />"
						+"					</figure>"
					+"				</div>"
				+"				</div>"
			+"			</div>"
			+"		<div class='row text-center divider'>"
			+"				<div class='col-xs-12 col-sm-5 emphasis' style='width:46.666667%;'>"
			+"					<button onclick='onHire(this)' nameskill='"+nameSkill+"' value='"+idWorker +"' class='btn btn-yellow btn-block'>"
			+"							<span class='fa fa-plus-circle'></span>"
			+"							&nbsp;Not Hire &nbsp;"
			+"					</button>"
			+"				</div>"
			+"				<div class='col-xs-12 col-sm-6 emphasis'>"


			+"					<button onclick='onProfile(this.parentNode,event,this.value)'  value='"+nameSkill+";;"+idWorker+"' class='btn btn-blue btn-block'>"
			+"						<span class='fa fa-user'></span>"
			+"						&nbsp; Profile&nbsp;"
		+"						</button>"
			+"		</div>"

			+	" 	</div>"
		+"    	 </div>"
		+"		</div>"
	+"		<div class='clearfix'>"
+"			</div>"
+" 		</div>"
+"		</"+idWorker+">"
+"		</worker>";


    var div = document.createElement('div');
	 node.appendChild(div);
	 div.innerHTML = text;

//  	var textnode = document.createTextNode(text);  
//  	node.appendChild(textnode); 
	//ul.insertBefore(node, ul.childNodes[0]);

  	li.appendChild(node);

//}
}-*/;	


	public static void hireWorker(Element elem) {



		NodeList<Element> lista = Document.get().getElementsByTagName("worker"); //elem.getAttribute("value"));





		String nameSkill = elem.getAttribute("nameskill");
		String idWorker = elem.getAttribute("value");

		if (map.get(nameSkill).getListWorker().size() > map.get(nameSkill).getNumberWorkerForSkillSelect()){
			Worker w = map.get(nameSkill).getNextWorker(idWorker);
			Document.get().getElementById("cont"+nameSkill).setInnerText(map.get(nameSkill).getNumberWorkerForSkillSelect() +"/"+(map.get(nameSkill).getListWorker().size()));
			addWorkers(Document.get().getElementById(nameSkill),w.getUri(),  w.getFirstname(),w.getSurname(), nameSkill,w.getImagePath());

			int size = lista.getLength();
			for (int i=size-1; size>0;i--){

				Element el = lista.getItem(i);
				String attributeValue = el.getAttribute("value");
				if (idWorker.equals(attributeValue)){
					el.removeFromParent();
					break;
				}
			}

		}

	}

	/*
	 * Visualizza la tabella degli skill o la rimuove
	 */
	public  static void profileWorker(Element element, Event event,String array) {


		String[] help = array.split(";;");
		String skill = help[0];
		String id = help[1];

		Worker worker = map.get(skill).getWorker(id);

		List<Competence> listCompetence = worker.getCompetences();


		if (Document.get().getElementById(id) != null){

			Document.get().getElementById("divProfile"+id).getParentElement().removeChild(Document.get().getElementById("divProfile"+id));
		}
		else {

			int x = event.getScreenX();
			int y = event.getScreenY();

			String divText = "<div  style='background:white; left:"+x+"; top"+y+";'>"

		+"<div id='"+id+"'class='col-lg-12'>"


			+"<div class='row'>"
			+"	<div class='col-md-12'>"


			+"		<div class='row mtl'>"
			+"			<div class='col-md-12'>"

			+"				<table class='table table-striped table-hover'>"
			+"					<tbody>";
			int size = 0;
			for (Competence c : listCompetence){
				if (size != listCompetence.size()-1){	
					size++;
					if (c.getRating() == -1){
						divText = divText+"<tr> <td>"+c.getName()+"</td><td style='width:105px;'><span class='label label-primary pull-left mtm'>Inferred</span>";

					}
					else{
						divText = divText+"<tr> <td>"+c.getName()+"</td><td>";
						for(int j=0 ; j<c.getRating().intValue();j++){
							divText = divText+"<i class='fa fa-star text-yellow fa-fw'></i>";
						}
						for(int j=c.getRating().intValue(); j<5 ;j++){
							divText = divText+"<i class='fa fa-star-o text-yellow fa-fw'></i>";
						}
					}
					divText = divText+"</td></tr>";
				}
			}
			divText = divText+"	</tbody>"
					+"				</table>"
					+"			</div>"

			+"		</div>"
			+"	</div>"
			+"</div>"

		+"</div>"

	+"</div>";

			DivElement DIV = Document.get().createDivElement();
			DIV.setAttribute("id", "divProfile"+id);
			DIV.setInnerHTML(divText);
			DIV.setAttribute("style",  "background:red; left:"+x+"; top"+y);
			element.getParentElement().appendChild(DIV);


			//  Element div = Document.get().getElementById("divProfile");

			// div.setAttribute("style", " display:block; left:"+y+"; top:"+x);



			//Document.get().getElementById(value).setAttribute("style", "display:none");	

		}
	}	


	public static void removeProfile (){
		if (Document.get().getElementById("divProfile") == null)
			Document.get().getElementById("divProfile").getParentElement().removeChild(Document.get().getElementById("divProfile"));

	}





	@UiHandler("btnFinish")
	void btnFinish(ClickEvent e){

		Worker user = LoginService.Util.getLoggedUser();	
		task.getListWorkers().add(user);
		rpcService.createTask(task, new AsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				Window.alert("OK : " + result);

			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("Error Insert");
			}
		});

	}


	public static native void loading(com.google.gwt.dom.client.Element element) /*-{



	  var id = 'loader', fill = '#dc6767',
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
	node.setAttribute("style","position:absolute; width:100%; height:0px;")
	//node.setAttribute("class", "clearfix");

    //var div = document.createElement('div');
	//node.appendChild(div);
	node.innerHTML = text;

//  var textnode = document.createTextNode(text);  
//  node.appendChild(textnode); 
  	element.appendChild(node);



}-*/;	
}
