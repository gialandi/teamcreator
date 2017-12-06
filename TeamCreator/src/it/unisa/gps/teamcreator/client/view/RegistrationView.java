package it.unisa.gps.teamcreator.client.view;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.SingleUploader;
import it.unisa.gps.teamcreator.client.GreetingService;
import it.unisa.gps.teamcreator.client.GreetingServiceAsync;
import it.unisa.gps.teamcreator.client.presenter.RegistrationPresenter;
import it.unisa.gps.teamcreator.shared.Competence;
import it.unisa.gps.teamcreator.shared.Topic;
import it.unisa.gps.teamcreator.shared.Worker;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public final class RegistrationView extends Composite implements RegistrationPresenter.Display{

	private static Map<String,Integer> mapCompetencesUser  = new java.util.HashMap<String,Integer>();
	private final static Map<String,Integer>  mapCompetencesWikipedia  = new java.util.HashMap<String,Integer>();
	private static Map<String, Integer> mapManuallyInsertedCompetences;
	// The @UiField annotation tags elements that the widget should
	// maintain references too.
	@UiField Button submitButton;
	@UiField InputElement email;
	@UiField InputElement password;
	@UiField InputElement confirmPassword;
	@UiField InputElement firstname;
	@UiField InputElement lastname;

	@UiField HTMLPanel provaFile;

	@UiField Button btnAddSkill;
	
	private String imagePath = "styles/login/omino.png";

	private static final int WM_INTERVAL = 3000;

	private final GreetingServiceAsync server = GWT.create(GreetingService.class);

	private List<Topic> topicByWikipediaMiner;

	@UiField TextAreaElement workerDescription;
	@UiField
	static UListElement competenceList;
	@UiField HTMLPanel fileUploadPanel;


	public RegistrationView() {
		initWidget(binder.createAndBindUi(this));

		submitButton.getElement().setAttribute("type", "submit");
		submitButton.getElement().setAttribute("class", "btn btn-primary");

		email.setAttribute("id", "mela");
		firstname.setAttribute("id", "nome");
		lastname.setAttribute("id", "cognome");
		password.setAttribute("id", "pass");
		confirmPassword.setAttribute("id","confirm");

		SingleUploader uploader = new SingleUploader();
		fileUploadPanel.add(uploader);
		uploader.setAutoSubmit(true);
		
		mapManuallyInsertedCompetences = new java.util.HashMap<String, Integer>();
		uploader.addOnFinishUploadHandler(new OnFinishUploaderHandler() {

			@Override
			public void onFinish(IUploader uploader) {
				if (uploader.getStatus() == Status.SUCCESS) {
					imagePath = uploader.getServerInfo().getFileUrl();
					fileUploadPanel.remove(uploader);
					Label l = new Label(uploader.getFileInput().getFilename().substring(uploader.getFileInput().getFilename().lastIndexOf("\\")+1) +" ... Upload succes");
					fileUploadPanel.add(l);
				}

			}
		});
		Timer timer = new Timer(){
			String oldDescription = "";

			@Override
			public void  run() {
				
				String newDescription = workerDescription.getValue();
				if(!oldDescription.equals(newDescription)) {
					competenceList.removeAllChildren();
					for (String skill : mapCompetencesUser.keySet()){
						Window.alert(skill);
						addListItem(competenceList, skill);
					}
					server.getTopics(newDescription, new AsyncCallback<List<Topic>>() {

						@Override
						public void onSuccess(List<Topic> result) {


							topicByWikipediaMiner = result;

							for (Topic skill : topicByWikipediaMiner){
								addListItem(competenceList, skill.getTitle());
							}
							for(Entry<String, Integer> entry : mapManuallyInsertedCompetences.entrySet()){
								addListItem(competenceList, entry.getKey());
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
		registerSetRating();




	}

	private static native void registerSetRating()/*-{
		$wnd.setRating=@it.unisa.gps.teamcreator.client.view.RegistrationView::setRating(*);
		$wnd.onAddSkill10=@it.unisa.gps.teamcreator.client.view.RegistrationView::onAddSkill10(*);
}-*/;

	public static void setRating(String topicTitle, int rating){
		Window.alert(topicTitle);
	}

	private String createCompetenceListElement(Topic topic, int id) {
		String html;
		//		html = 	"<topic value='"+topic.getTitle()+"'>";
		//		html += "<li class=\"clearfix\">";
		//		html += "<div class =\"todo-check pull-left\">"
		//				 		+ "<input type='checkbox' value='"+topic.getTitle()+"'></input>"
		//				 		+ "<div class='todo-title' >"+topic.getTitle()+"</div>"
		//				 	 + "<div class =\"todo-check pull-right clearfix\">"
		//				 + "<figcaption class=\"ratings\" style=\"display:inline;\"> "
		//			 		+ "<a href=\"#\"> <span class=\"fa fa-star-o\"></span></a>"	
		//			 		+ "<a href=\"#\"> <span class=\"fa fa-star-o\"></span></a>"	
		//			 		+ "<a href=\"#\"> <span class=\"fa fa-star-o\"></span></a>"	
		//			 		+ "<a href=\"#\"> <span class=\"fa fa-star-o\"></span></a>"	
		//			 		+ "<a href=\"#\"> <span class=\"fa fa-star-o\"></span></a>"	
		//			 	+ "</figcaption>"
		//			 + "</div>"
		//				 + "</div>"
		//				
		//			+ "</topic>";
		html = 	"<topic value='"+topic.getTitle()+"' rating='1'>"
				+"<li class='clearfix' style='cursor:pointer'>"
				+"	<i></i>"
				+"</span>"
				+"<div class='todo-check pull-left'>"
				+"<div class='todo-title'>"+topic.getTitle()+"</div>"
				+"	<div class='todo-actions pull-right clearfix'>"
				+"	<figcaption class='ratings' style='display:inline;'>";
		for(int i=1; i<=5; i++){
			html += "<ratingStar topic='"+topic.getTitle()+"' rating='"+i+"'>"
					+ "<a onclick='setRating(\""+topic.getTitle()+"\","+i+")'>"
					+ "<span class='fa fa-star-o'></span>"
					+ "</a>"
					+ "</ratingStar>";
		}
		html+="</figcaption>"
				+"</div>"
				+"</div>"
				+"</li></topic>";
		return html;
	}


	@UiHandler("submitButton")
	void handleSendClick(ClickEvent event) {
		Worker w = new Worker();
		w.setEmail(email.getValue());
		w.setPassword(password.getValue());
		w.setFirstname(firstname.getValue());
		w.setSurname(lastname.getValue());
		List<Competence> skills = getSelectedCompetence();
		w.setCompetences(skills);
		w.setImagePath(imagePath);
		w.setDescription(workerDescription.getValue());
		server.registerNewUser(w, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println(caught);		
			}

			@Override
			public void onSuccess(Boolean result) {
				Window.alert("success");

			}
		});

	}

	private List<Competence> getSelectedCompetence() {
		NodeList<InputElement> competences = Document.get().getElementsByTagName("figcaption").cast();
		List<Competence> skills = new  ArrayList<Competence>(); 
		for(int i=0; i<competences.getLength(); i++){
			Competence c = new Competence();
			c.setName(competences.getItem(i).getAttribute("nameSkill"));
			c.setRating(Integer.parseInt(competences.getItem(i).getAttribute("valore")));
			skills.add(c);
		}
		return skills;
	}



	private static final Binder binder = GWT.create(Binder.class);
	interface Binder extends UiBinder<Widget, RegistrationView> {}

	public Widget asWidget() {
		return this;
	}





	@UiHandler("btnAddSkill")
	void onAddSkill(ClickEvent e){

		if (Document.get().getElementById("idInputSkill")==null)
			insertSkill(competenceList);
		else {
			Window.alert("Insert Skill !!!");
		}





	}



	public static native void insertSkill(com.google.gwt.dom.client.Element ul) /*-{



  	var node = document.createElement("LI");
  	node.setAttribute("id","idInputSkill");
	node.setAttribute("class", "clearfix");
	node.setAttribute("style"," background-color: rgba(182, 131, 73, 0.34); border-color: #ebccd1;color: #a94442");
    var text = " <div class='todo-check pull-left'>	<div class='todo-title'> "



                +"                            <div class='input-group' style='width:348%;'>"
                +"                              <input  type='text' id='inputDialog' style='width:100%;' autofocus placeholder='Insert skill here...' value='' class='form-control'></input> <span id='btn-chat' class='input-group-btn'>"

                +"								<button onclick='onAddSkill10()'type='button' class='btn btn-green'>"
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

	ul.insertBefore(node, ul.childNodes[0]);
  	//ul.appendChild(node);


}-*/;
	public static void cancelSkill(){




		competenceList.removeFromParent();


	}
	public static void onAddSkill10(){

		InputElement input = Document.get().getElementById("inputDialog").cast();



		if (input.getValue().equals("")){
			Window.alert("Insert Skill");
		}
		else {
			Document.get().getElementById("idInputSkill").removeFromParent();
			mapManuallyInsertedCompetences.put(input.getValue(), 0);
			addListItem(competenceList,input.getValue());

			setRating(input.getValue(), 0);
		}
	}

	public static native void addListItem(com.google.gwt.dom.client.Element ul, String valueSkill) /*-{



  	var node = document.createElement("LI");
	node.setAttribute("class", "clearfix");
    var text = "<div class='todo-check pull-left'></div>	"
    		+"  <div class='todo-title'>"
    		+"		<span style=''>"
    		+"			<listSkills value='"+ valueSkill +"'>"+valueSkill+"</listSkills>"
    		+"		</span> </div> ";

	text=text+"<script> function onRemoveSkill(e){ e.parentNode.parentNode.removeChild(e.parentNode) } </script>";


    text = text+"	<div class='todo-actions pull-right clearfix' style='right :90px;'>"
			+"	<figcaption id='"+valueSkill+"' class='ratings' style='display:inline;'>";
		for(i=0; i<=4; i++){
			text += "<ratingStar topic='"+valueSkill+"' rating='"+i+"'>"
						+ "<a >"
							+ "<span id='"+valueSkill+"-"+i+"' onclick='starsR(this.id)' class='fa fa-star-o'></span>"
						+ "</a>"
				 + "</ratingStar>";
		}
		text+="</figcaption>"
				+"</div>"
		text=text +"<div class='todo-actions pull-right clearfix'><a class='todo-remove' onClick='onRemoveSkill(this)'> <i class='fa fa-trash-o'> </i> </i></a></div>";
	 var div = document.createElement('div');
	 node.appendChild(div);
			div.innerHTML = text;

//  	var textnode = document.createTextNode(text);  
//  	node.appendChild(textnode); 
//	ul.insertBefore(node, ul.childNodes[2]);
	ul.appendChild(node);

}-*/;	



	private void setRatingCompetence (String competence,Integer rating){

		mapCompetencesUser.put(competence, rating);


	}
}
