/** File legato a co-workers
 * Ricordarsi che ogni worker ha un ID associato che è anche ID del div in cui c'è la sua immagine
 * Le skills sono una lista (array)
 */

//visualizza i vari co-workers quando entro nella pagina
function disp(nome,cognome,immagine,id_worker){
var inn="<figure><img id='"+id_worker+"' src='"+immagine+"' alt='' style='display: inline-block' class='img-responsive img-circle' onclick='view()'></img><span>"+nome+" "+cognome+"</span></figure>";
var div=document.createElement("div");
div.setAttribute("class","col-xs-12 col-sm-4 text-center");
div.setAttribute("style","display: block;width:200px");
div.innerHTML=inn;
document.getElementsByClassName("col-lg-4")[0].appendChild(div);
}


//visualizza il div per valutare il worker
function view(competenze) {
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
/*
//quante skills ho?
var n=get_n_s(idw);
*/

//inserisco le varie skills
list=document.getElementById("lista");

for (i=0;i<competenze.length;i++){
list.appendChild(get_skills(competenze,i));
}
}

//la funzione 
function create_div_skill(){
var div_col_lg_otto=document.createElement("div");
div_col_lg_otto.setAttribute("id","skillcont");
div_col_lg_otto.setAttribute("class","col-lg-8");
div_col_lg_otto.innerHTML="<div class='portlet box'> <div class='portlet-header'> <div class='caption'> Skills</div> </div> <div class='portlet-body' style='overflow: scroll;'> <div class='slimScrollDiv' style='position: relative; overflow: hidden; width: 100%; height: 250px;'> <ul id='lista' class='todo-list sortable ui-sortable' id='idListSkills'> </ul> <div class='slimScrollBar' style='background-color: rgb(0, 0, 0); width: 7px; position: absolute; top: 0px; opacity: 0.4; display: none; border-top-left-radius: 7px; border-top-right-radius: 7px; border-bottom-right-radius: 7px; border-bottom-left-radius: 7px; z-index: 99; right: 1px; height: 184.9112426035503px; background-position: initial initial; background-repeat: initial initial;'></div> <div class='slimScrollRail' style='width: 7px; height: 100%; position: absolute; top: 0px; display: none; border-top-left-radius: 7px; border-top-right-radius: 7px; border-bottom-right-radius: 7px; border-bottom-left-radius: 7px; background-color: rgb(51, 51, 51); opacity: 0.2; z-index: 90; right: 1px; background-position: initial initial; background-repeat: initial initial;'></div> </div> <br> <button type='button' class='btn btn-yellow btn-block'>Evaluate</button></div></div>";

return div_col_lg_otto;
}

//la funzione crea gli elmenti per mostrare le skills del worker passate in comp con indice y
function get_skills(comp,y){
var li=document.createElement("li");
li.setAttribute("class","clearfix");
var divgenerico=document.createElement("div");
divgenerico.setAttribute("id",comp[y]); // id della skill è il nome della skill
li.appendChild(divgenerico);
var drag_n_drop=document.createElement("span");
drag_n_drop.setAttribute("class","drag-drop");
divgenerico.appendChild(drag_n_drop);
var i=document.createElement("i");
drag_n_drop.appendChild(i);
var todocheck=document.createElement("div");
todocheck.setAttribute("class","todo-check pull-left");
divgenerico.appendChild(todocheck);
//elemento dove bisogna inserire la skill y prelevata dalla lista comp
var title=document.createElement("div");
title.setAttribute("class","todo-title");
title.innerHTML=comp[y];
todocheck.appendChild(title);
var actions=document.createElement("div");
actions.setAttribute("class","todo-actions pull-right clearfix");
todocheck.appendChild(actions);
var figcaption=document.createElement("figcaption");
figcaption.setAttribute("class","ratings");
figcaption.setAttribute("valore","0");
figcaption.setAttribute("style","display:inline;");
actions.appendChild(figcaption);
for (j=0;j<=4;j++){
figcaption.appendChild(get_star(j,y));
}
return li;
}

/*//restituisce il numero di skills di un worker
function get_n_s(x){
return x.charAt(2);
}*/

function get_star(indice,isk){
var ancora=document.createElement("a");
ancora.setAttribute("href","#");
ancora.innerHTML="<span id='skill_"+isk+'_'+indice+"' class='fa fa-star-o' onclick='stars(this.id); return false'></span>";

return ancora;
}

function starsRR(id){
	figcaption=document.getElementById("fake");
	var n=id.split("-");
	for (var j=0;j<=4;j++){ // coloro le stelline
	if (j<=n[1]){
	document.getElementById(n[0]+"-"+j).setAttribute("class","fa fa-star");
	}
	else {document.getElementById(n[0]+"-"+j).setAttribute("class","fa fa-star-o");}
	}
	var value=parseInt(n[1])+1;
	figcaption.setAttribute("valore",value);
	figcaption.setAttribute("nameSkill",n[0]);
	}

function abilita(valueSkill){
	if (valueSkill != ""){
		figcaption = document.getElementById("fake");
		figcaption.setAttribute("id",valueSkill);
	    text= "<a><span id='"+valueSkill+"-0' onclick='starsRR(this.id)' class='fa fa-star-o'></span></a><a><span id='"+valueSkill+"-1' onclick='starsRR(this.id)' class='fa fa-star-o'></span></a><a><span id='"+valueSkill+"-2' onclick='starsRR(this.id)' class='fa fa-star-o'></span></a><a ><span id='"+valueSkill+"-3' onclick='starsRR(this.id)' class='fa fa-star-o'></span></a><a><span id='"+valueSkill+"-4' onclick='starsRR(this.id)' class='fa fa-star-o'></span>	</a>"
	    figcaption.innerHTML=text;
	}
	}
function starsR(id){
	var n=id.split("-");
	for (var j=0;j<=4;j++){ // coloro le stelline
	if (j<=n[1]){
	document.getElementById(n[0]+"-"+j).setAttribute("class","fa fa-star");
	}
	else {document.getElementById(n[0]+"-"+j).setAttribute("class","fa fa-star-o");}
	}
	var value=parseInt(n[1])+1;
	document.getElementById(n[0]).setAttribute("valore",value);
	document.getElementById(n[0]).setAttribute("nameSkill",n[0]);
	}
function stars(id){
var n=id.split("_");
for (var j=0;j<=4;j++){ // coloro le stelline
if (j<=n[2]){
document.getElementById(n[0]+"_"+n[1]+"_"+j).setAttribute("class","fa fa-star");
}
else {document.getElementById(n[0]+"_"+n[1]+"_"+j).setAttribute("class","fa fa-star-o");}
}
var value=parseInt(n[2])+1;
document.getElementById(id).parentNode.parentNode.setAttribute("valore",value);
}

//validation

//cancella username

function valid_user(id){
var user=document.getElementById(id).value;
var re=/^[a-z0-9]{4,20}$/;
if (!(re.test(user))) {

document.getElementById(id).setAttribute("style","border-style: solid;border-color:#FF3300;border-width:medium");
get_message(id);
}
else {
document.getElementById(id).setAttribute("style","");
del_mess(id+"mess");
}
}

function valid_email(id){
var re=/^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+[\.]([a-z0-9-]+)*([a-z]{2,3})$/;
var mail=document.getElementById(id).value;
if (!(re.test(mail))){

document.getElementById(id).setAttribute("style","border-style: solid;border-color:#FF3300;border-width:medium");
get_message(id);
}
else {
document.getElementById(id).setAttribute("style","");
del_mess(id+"mess");
}
}

function valid_n_s(id){
var re=/^[a-zA-Z]{1,16}$/;
var n_s=document.getElementById(id).value;
if (!(re.test(n_s))){

document.getElementById(id).setAttribute("style","border-style: solid;border-color:#FF3300;border-width:medium"); //thick
get_message(id);
}
else {
document.getElementById(id).setAttribute("style","");
del_mess(id+"mess");
}
}

function valid_p(id){
if (document.getElementById(id).value!=document.getElementById("pass").value){

document.getElementById(id).setAttribute("style","border-style: solid;border-color:#FF3300;border-width:medium"); //thick
get_message(id);
}
else {
document.getElementById(id).setAttribute("style","");
del_mess(id+"mess");
}
}

function get_message(x){
if (document.getElementById(x+"mess")==null){
var mess="<div class='alert alert-danger'><strong>Errore!</strong>";
var appnd="";
if (x=="mela"){
appnd="Inserisci una mail valida</div>";
mess=mess+appnd;
ID="melamess";

}
if (x=="confirm"){
appnd="Conferma la password</div>";
mess=mess+appnd;
ID="confirmmess";

}
if (x=="nome"){
appnd="Inserisci un nome senza numeri</div>";
mess=mess+appnd;
ID="nomemess";
}
if (x=="cognome"){
appnd="Inserisci un cognome senza numeri</div>";
mess=mess+appnd;
ID="cognomemess";
}

var div=document.createElement("div");
div.setAttribute("id",ID);
div.innerHTML=mess;

document.getElementsByClassName("form-body pal")[0].appendChild(div);
mess="";
ID="";
appnd="";
}
}

function del_mess(x){
if (document.getElementById(x)!=null)
document.getElementsByClassName("form-body pal")[0].removeChild(document.getElementById(x));
}

