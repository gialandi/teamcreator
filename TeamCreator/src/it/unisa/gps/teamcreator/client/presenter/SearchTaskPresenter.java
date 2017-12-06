package it.unisa.gps.teamcreator.client.presenter;





import java.util.ArrayList;
import java.util.List;

import it.unisa.gps.teamcreator.client.GreetingServiceAsync;
import it.unisa.gps.teamcreator.client.LoginService;
import it.unisa.gps.teamcreator.client.WidgetUtil;
import it.unisa.gps.teamcreator.shared.Task;






import it.unisa.gps.teamcreator.shared.Worker;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;




public class SearchTaskPresenter implements Presenter{

	public interface Display {
			Widget asWidget();
		}

	private static GreetingServiceAsync rpcService;
	private final HandlerManager eventBus;
	private static List<Task> tasks;
	private static List<Worker> workers;
	private static WidgetUtil util;
	
	@UiField Button workButton;
	
	private final Display display;
	private static boolean showDetail=false;

	public SearchTaskPresenter(GreetingServiceAsync rpcService, HandlerManager eventBus, Display view) {
		
		
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
		bind();
		
	}

	@Override
	public void go(RootPanel container) {
		tasks=new ArrayList<Task>();
		util = new WidgetUtil();
		rpcService.getTasksOfWorker(LoginService.Util.getLoggedUser().getUri(),new AsyncCallback<List<Task>>() {
		
			@Override
			public void onSuccess(List<Task> result) {
				
				Document.get().getElementById("divLoading").setAttribute("style", "display:none");
				tasks=result;
				createMainPage();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});

		
	}
	
	private static void createMainPage(){
		Element x =(Element)Document.get().getElementById("content");
		x.setInnerHTML(" ");
		String toAdd="";
		for(int i=0;i<tasks.size();i++)
			toAdd+=util.createTask(tasks.get(i).getDescription(),tasks.get(i).getNameTask(), tasks.get(i).getProjectManager(), i);
		addWorker(x,toAdd);
	}


	public static native void addWorker(com.google.gwt.dom.client.Element  form,String s) 
	/*-{
		var x=document.getElementById("content");
		var node = document.createElement("div");
		node.setAttribute("class", "clearfix");
    	var text =  s;

  		var div = document.createElement('div');
		node.appendChild(div);
		node.innerHTML = text;

 		var textnode = document.createTextNode(text);  
  		//node.appendChild(textnode); 
  		form.appendChild(node);
	}-*/;	
	
	

	public void bind() { 
	
			}
	
	
	public static void showDetail(int id)
	{
		if(!showDetail)
		{
			//int id=0;
			Element x =(Element)Document.get().getElementById("content");
			x.setInnerHTML(" ");
			Task current=tasks.get(id);
			String s= util.createTask(current.getDescription(), current.getNameTask(), current.getProjectManager(), id);
			String w= util.createDetail();
			String toAdd="<div style='width=100%; height=100%;'>"+s+"</div>"
					+ "<table>"
					+ "<tr>"
					+ "<td style='width=100%;>"+w+"</td>"
					+ "</tr>"
					+"</table>";

			Document.get().getElementById("content").setInnerHTML(toAdd);
			showDetail=true;
		}
		else
		{
			
			createMainPage();
			showDetail=false;
		}
	}
	
	
	
	public static void showWorker(int id){
		if(!showDetail)
		{
			Element x =(Element)Document.get().getElementById("content");
			x.setInnerHTML(" ");
			Task current=tasks.get(id);
			workers= new ArrayList<Worker>();
			final String s= util.createTask(current.getDescription(), current.getNameTask(), current.getProjectManager(), id);
			String toAdd="";
			String w="";
			workers=current.getListWorkers();
			toAdd="<div style='width=100%; height=100%;'>"+s+"</div>"
					+ "<table>";
						for(int i=0;i<workers.size();i++)
						{		
							w=util.createWorker(workers.get(i));
							toAdd+="<tr>"
								+ "<td style='width=100%;>"+w+"</td>"
								+ "</tr>";
						}
							toAdd+="</table>";

						Document.get().getElementById("content").setInnerHTML(toAdd);
			showDetail = true;
		}
		else
		{
			
			createMainPage();
			showDetail=false;
		}
	}	
	
	
	


}