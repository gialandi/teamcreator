package it.unisa.gps.teamcreator.client.view;


import java.util.List;
import java.util.Map;

import it.unisa.gps.teamcreator.client.GreetingService;
import it.unisa.gps.teamcreator.client.GreetingServiceAsync;
import it.unisa.gps.teamcreator.client.LoginService;
import it.unisa.gps.teamcreator.shared.Competence;
import it.unisa.gps.teamcreator.shared.Task;
import it.unisa.gps.teamcreator.shared.Worker;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dev.asm.Label;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.impl.WindowImpl;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;


public final class ProfileView extends Composite {

	@UiField Button btnAddSkill;


	@UiField UListElement idListSkills;
	@UiField  SpanElement skillNumber;
	@UiField SpanElement taskNumber;
	@UiField SpanElement CoWorkersNumber;
	@UiField SpanElement nameUser;
	@UiField ImageElement avatarProfile;
	@UiField SpanElement description;

	@UiField DivElement taskLoading;
	@UiField DivElement taskLoading2;

	@UiField DivElement skillLoading;
	@UiField DivElement skillLoading2;

	@UiField DivElement coWorkerLoading;
	@UiField DivElement coWorkerLoading2;
	// The @UiField annotation tags elements that the widget should
	// maintain references too.

	private static final GreetingServiceAsync rpcService =  GWT.create(GreetingService.class);;



	public ProfileView() {
		initWidget(binder.createAndBindUi(this));



		this.getElement().setAttribute("id", "page-wrapper");

		final Worker user = LoginService.Util.getLoggedUser();


		btnAddSkill.getElement().setAttribute("class", "btn btn-yellow btn-block");
		btnAddSkill.getElement().setAttribute("style","background-color:#3E77AB; border-color: rgba(62, 119, 171, 0.49)");
		//    sendButton.addDomHandler(
		//            new MouseOverHandler() {
		//              @Override
		//              public void onMouseOver(MouseOverEvent event) {
		//              Window.alert("DOM");
		//              }
		//            },
		//            MouseOverEvent.getType());
		registerAddCompetence();



		for (Competence s : user.getCompetences()){
			insertListSkill(idListSkills, s.getName(), s.getRating());
			//Window.alert(("idList") +" lISt");
		}
		idListSkills.setAttribute("id", "idListSkills");

		loading(skillLoading);
		loading(taskLoading);
		loading(coWorkerLoading);
		nameUser.setInnerText(user.getFirstname()+" " + user.getSurname());
		avatarProfile.setAttribute("src", user.getImagePath());
		description.setInnerText(user.getDescription());
		//description.setInnerText(user.get);

		rpcService.getTasksOfWorker(user.getUri(), new AsyncCallback<List<Task>>() {

			@Override
			public void onSuccess(List<Task> result) {
				taskNumber.setInnerText(""+result.size());
				skillLoading.setAttribute("style", "visibility:hidden;");
				skillLoading2.setAttribute("style", "visibility:visible;");

				taskLoading.setAttribute("style", "visibility:hidden;");
				taskLoading2.setAttribute("style", "visibility:visible;");
				skillNumber.setInnerText(""+user.getCompetences().size());
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				taskNumber.setInnerText("");
			}
		});

		rpcService.getCoworkersOf(user.getUri(), new AsyncCallback<List<Worker>>() {

			@Override
			public void onSuccess(List<Worker> result) {
				// TODO Auto-generated method stub
				coWorkerLoading.setAttribute("style", "visibility:hidden;");
				coWorkerLoading2.setAttribute("style", "visibility:visible;");
				CoWorkersNumber.setInnerText(""+result.size());
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				CoWorkersNumber.setInnerText("");
			}
		});
	}





	private static final Binder binder = GWT.create(Binder.class);
	interface Binder extends UiBinder<Widget, ProfileView> {}


	public Widget asWidget(){
		return this;
	}





	//	@UiHandler ("okButton")
	//	void onOkButton (ClickEvent e){
	//	if (skillDialog.getValue().equals("")){
	//		dialogBox.hide();
	//	}
	//	else {
	//		addListItem(Document.get().getElementById("idListSkills"),skillDialog.getValue());
	//		dialogBox.hide();
	//	}
	//	}

	@UiHandler("btnAddSkill")
	void handleClick(ClickEvent e){
		Worker user = LoginService.Util.getLoggedUser();
		if (Document.get().getElementById("idInputSkill")==null){
			insertSkill(Document.get().getElementById("idListSkills"));

		}
		else {
			Window.alert("Insert Skill !!!");
		}
		//dialogBox.center();



	}

	public static native void addListItem(com.google.gwt.dom.client.Element ul, String valueSkill) /*-{



	  	var node = document.createElement("LI");
		node.setAttribute("class", "clearfix");
	    var text = "<span class='drag-drop'> <i></i> </span> <div class='todo-check pull-left'>	<div class='todo-title'>"+ valueSkill +"</div> ";

	    text=text +"<div class='todo-actions pull-right clearfix'><figcaption class='ratings' style='display:inline;'><a href='#'><span class='fa fa-star'></span></a>";

	    text=text + "<a href='#'><span class='fa fa-star'></span></a><a href='#'><span class='fa fa-star'></span></a><a href='#'><span class='fa fa-star'></span></a><a href='#'>";

		text=text+"<span class='fa fa-star-o'></span>	</a></figcaption></div></div>";
		 var div = document.createElement('div');
		 node.appendChild(div);
  			div.innerHTML = text;

//	  	var textnode = document.createTextNode(text);  
//	  	node.appendChild(textnode); 
		ul.insertBefore(node, ul.childNodes[0]);
//	  	ul.appendChild(node);

	}-*/;	

	public static native void insertListSkill(com.google.gwt.dom.client.Element ul, String value, Double rating) /*-{

	  	var node = document.createElement("LI");
		node.setAttribute("class", "clearfix");
	    var text = "<span class='drag-drop'> <i></i> </span> <div class='todo-check pull-left'></div>	<div class='todo-title' style='width:80%;'>"+ value +"</div> ";


	    text=text +"<div class='todo-actions pull-right clearfix'> <figcaption class='ratings' style='display:inline;'>";
		r = Math.round(rating);

	if (rating < 0){
	text=text +	"<span class='label label-primary pull-left mtm'>Inferred</span>";
	} 
	else{
    for ( i=0; i<r ;i++){

     text=text +"<a><span class='fa fa-star'></span></a>";
    }

    for ( i=r; i<5 ;i++){
     text=text +"<a ><span class='fa fa-star-o'></span></a>";
    }
	}


    	text=text+"</figcaption> </div>";
		 var div = document.createElement('div');
		 node.appendChild(div);
  			div.innerHTML = text;

//	  	var textnode = document.createTextNode(text);  
//	  	node.appendChild(textnode); 
//		ul.insertBefore(node, ul.childNodes[0]);
	  	ul.appendChild(node);


}-*/;


	public static native void insertSkill(com.google.gwt.dom.client.Element ul) /*-{



  	var node = document.createElement("LI");
  	node.setAttribute("id","idInputSkill");
	node.setAttribute("class", "clearfix");
	node.setAttribute("style"," background-color:rgba(62, 119, 171, 0.26); border-color: rgba(62, 119, 171, 0.49); color: #a94442");
    var text = " <div class='todo-check pull-left'>	<div class='todo-title'> "

                +"                            <div class='input-group' style='width:250%;'>"

                +"                              <input  type='text' id='inputDialog'   autofocus placeholder='Insert skill here...' value='' class='form-control'></input> <span id='btn-chat' class='input-group-btn'>"

                +"								<button onclick='onAddSkill()'type='button' class='btn btn-green'>"
                +"                                       <span class='fa fa-plus man'></span>"
                +"                                    </button>"
                 +"								<button onclick='onCancel()'type='button' class='btn btn-green'>"
                +"                                    <i class='fa fa-trash-o'> </i>"
                +"                                    </button>"

                +"                            </div>"

    +"</div> ";

    text=text +"<div class='todo-actions pull-right clearfix'><figcaption id='fake' class='ratings' valore='' style='display:inline;'><a><span id='skill-0' onclick='starsRR(this.id)' class='fa fa-star-o'></span></a><a><span id='skill-1' onclick='starsRR(this.id)' class='fa fa-star-o'></span></a><a><span id='skill-2' onclick='starsRR(this.id)' class='fa fa-star-o'></span></a><a ><span id='skill-3' onclick='starsRR(this.id)' class='fa fa-star-o'></span></a><a><span id='skill-4' onclick='starsRR(this.id)' class='fa fa-star-o'></span>	</a> ";

	text=text+"</figcaption></div></div>";
	 var div = document.createElement('div');
	 node.appendChild(div);
	 div.innerHTML = text;

//  	var textnode = document.createTextNode(text);  
//  	node.appendChild(textnode); 

	ul.insertBefore(node, ul.childNodes[0]);
  	//ul.appendChild(node);


}-*/;

	private static native void registerAddCompetence()/*-{
	$wnd.onAddSkill=@it.unisa.gps.teamcreator.client.view.ProfileView::addSkill(*);
	$wnd.onCancel=@it.unisa.gps.teamcreator.client.view.ProfileView::cancelSkill(*);
}-*/;


	public static void cancelSkill(){




		Document.get().getElementById("idInputSkill").removeFromParent();


	}

	public  static void addSkill(){

		Worker user = LoginService.Util.getLoggedUser();
		final InputElement input = Document.get().getElementById("inputDialog").cast();


		LIElement item =  Document.get().getElementById("idInputSkill").cast();
		final com.google.gwt.dom.client.Element figcaption = item.getElementsByTagName("figcaption").getItem(0);
		if (input.getValue().equals("")){
			Window.alert("Insert Skill ");

		}
		else  if ( figcaption.getAttribute("valore")==""){
			Window.alert("Insert Rating");

		}

		else {
			rpcService.addCompetence(user.getUri(),input.getValue().trim(), Double.parseDouble(figcaption.getAttribute("valore")), new AsyncCallback<String>() {

				@Override
				public void onSuccess(String result) {
					// TODO Auto-generated method stub

					Document.get().getElementById("idInputSkill").removeFromParent();
					Competence c = new Competence();
					c.setName(input.getValue());
					c.setRating(2);
					LoginService.Util.getInstance().updateLoggedUser(new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onSuccess(Void result) {
							// TODO Auto-generated method stub

						}
					});
					insertListSkill(Document.get().getElementById("idListSkills"),input.getValue(),Double.parseDouble(figcaption.getAttribute("valore")));

				}

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					Window.alert("Error ...");
				}
			});


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
	node.setAttribute("style","position:absolute; width:100%; height:0px;")
	//node.setAttribute("class", "clearfix");

  //var div = document.createElement('div');
	//node.appendChild(div);
	node.innerHTML = text;

//var textnode = document.createTextNode(text);  
//node.appendChild(textnode); 
	element.appendChild(node);



}-*/;	
}


