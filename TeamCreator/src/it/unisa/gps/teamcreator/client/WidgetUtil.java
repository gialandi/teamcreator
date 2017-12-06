package it.unisa.gps.teamcreator.client;

import it.unisa.gps.teamcreator.shared.Worker;

public class WidgetUtil {
	
	public WidgetUtil(){
		registerAddCompetence();
		
	}


	private static native void registerAddCompetence()/*-{
	$wnd.showWorker=@it.unisa.gps.teamcreator.client.presenter.SearchTaskPresenter::showWorker(*);
	$wnd.showDetail=@it.unisa.gps.teamcreator.client.presenter.SearchTaskPresenter::showDetail(*);
	}-*/;
	
	
	public String createDetail(){
		String s="<div class='col-lg-4'><div class='jumbotron'><h1>Jumbotron</h1><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum tincidunt est vitae ultrices accumsan. Aliquam ornare lacus adipiscing, posuere lectus et, fringilla augue. Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p><p><a role='button' class='btn btn-primary btn-lg'>Learn more</a></p></div></div>";
		return s;
	}
	
	public String createTask(String n,String c ,String a,int id)
	{
		String toAdd="<div id=''class='col-sm-6 col-md-3'>"
				+ "<div class='thumbnail'>"
					+ "<div class='caption'>"
						+ "<h3>"+n+"</h3>"
						+ "<p>"+c+"</p>"
						+ "<p>"+a+"</p>"
						+ "<p>"
							+"<Button onclick='showDetail(value)' role='button' value='"+id+"' class='btn btn-primary'>Details</Button>"
							+"<Button onclick='showWorker(value)' role='button' value='"+id+"' class='btn btn-default'>Workers</Button>"
						+ "</p>"
					+ "</div>"
				+ "</div>"
			+ "</div>";
		return toAdd;
	}
	
	public String createWorker(Worker w)
	{
		String x = "<div class='col-lg-3'>"
				+ " 	<div class='panel'> "
				+ "			<div class='panel-body'>"
				+ "				 <div class='profile' style='width:100%'>"
				+ "					 <div class='row' style='margin-bottom: 10px'> "
				+ "							<div class='col-xs-12 col-sm-8'> "
				+ "								<h2>"+w.getFirstname()+" "+w.getSurname()+"</h2>"
				+ "									<div class='col-xs-12 col-sm-4'> "																	
				+ "										<strong class='mrs'>Skills:</strong> ";
		
			for(int i=0;i<w.getCompetences().size();i++)
			{
					x+="<span class='label label-green mrs'>"+w.getCompetences().get(i).getName()+"</span>";
					if(i==w.getCompetences().size()/2)
						x+="<br>";
			}
							
						x+= "					 </div>"
					+ "						 </div>"		
						+ "						 <div class='col-xs-12 col-sm-4 text-right'>"
						+ "								 <img alt='' class='img-responsive img-circle' src='/"+w.getImagePath()+"'+ style='display: inline-block'>"
						+ "						 </div>"
					+ "					 </div>"
						+ " 	</div>"
						+ "   </div> "
						+ "	</div>"
						+ "</div>";
		return x;
	}
}