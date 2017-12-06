package it.unisa.gps.teamcreator.client;

import com.google.gwt.user.client.ui.HTML;

public class HTMLWorker extends HTML {

	private String html;
	
	public HTMLWorker(String nome, String cognome , String alias){
		html=createWidget(nome, cognome, alias);
	}
	
	public String createWidget(String n,String c ,String a)
	{
		String x = "<div class='col-lg-4'>"
				+ " 	<div class='panel'> "
				+ "			<div class='panel-body'>"
				+ "				 <div class='profile'>"
				+ "					 <div class='row' style='margin-bottom: 15px'> "
				+ "							<div class='col-xs-12 col-sm-8'> "
				+ "								<h2>"+n+" "+c+" "+a +"</h2>"
						+ "						<strong class='mrs'>Skills:</strong> "
						+ "						<span class='label label-green mrs'>html5</span>"
						+ "						<span class='label label-green mrs'>css3</span>"
						+ "						<span class='label label-green mrs'>jquery</span>"
						+ "					 </div>"
						+ "						 <div class='col-xs-12 col-sm-4 text-center'>"
						+ "							 <figure>"
						+ "								 <img alt='' class='img-responsive img-circle' src='images/avatar/128.jpg' style='display: inline-block'>"
						+ "								 <figcaption class='ratings'>"
						+ "								 <p> "
						+ "									<a href='#'>"
						+ "									 <span class='fa fa-star'></span>"
						+ "									</a> "
						+ "									<a href='#'> "
						+ "										<span class='fa fa-star'></span> "
						+ "									</a> "
						+ "									<a href='#'>"
						+ "										 <span class='fa fa-star'></span>"
						+ "									 </a> <a href='#'>"
						+ "											<span class='fa fa-star'></span> </a> <a href='#'> <span class='fa fa-star-o'></span> </a> </p> </figcaption> </figure>"
						+ "						 </div>"
						+ "					 </div>"
						+ "				 <div class='row text-center divider'>"
						+ "					 <div class='col-xs-12 col-sm-4 emphasis'> "
						+ "						<h2> <strong>1006</strong> </h2>"
						+ "						<p> <small>Views</small> </p> "
						+ "						<button class='btn btn-blue btn-block'> <span class='fa fa-user'></span> &nbsp; Profile"
						+ "						</button>"
						+ "					 </div> "
						+ "				<div class='col-xs-12 col-sm-4 emphasis'> "
						+ "					<h2> <strong>25</strong> </h2> <p> <small>Collaborations</small> </p> "
						+ "					<div class='btn-group dropup'> "
						+ "						<button class='btn btn-yellow btn-block'> <span class='fa fa-plus-circle'></span> &nbsp; Hire </button> "
						+ "						<ul class='dropdown-menu pull-right text-left' role='menu'>"
						+ "							 <li> <a href='#'><span class='fa fa-envelope'></span> &nbsp; Send an email </a> </li> "
						+ "							 <li> <a href='#'> <span class='fa fa-list'></span> &nbsp; Add or remove from a list </a> </li> "
						+ "							 <li class='divider'></li> <li> <a href='#'> <span class='fa fa-warning'></span> &nbsp; Report this user for spam </a> </li> "
						+ "							 <li class='divider'></li> <li> <a class='btn disabled' href='#' role='button'>Unfollow</a> </li> "
						+ "						</ul>"
						+ "					 </div>"
						+ "				 </div>"
						+ " 		</div>"
						+ " 	</div>"
						+ "   </div> "
						+ "	</div>"
						+ "</div>";
		return x;
	}
	
	public void showWidget(){
		setHTML(html);
	}
}

