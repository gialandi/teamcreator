function setRating(topicTitle, rating){
	stars = document.getElementsByTagName("ratingstar");
	for(i=0; i<stars.length; i++){
		if(stars[i].getAttribute("topic") == topicTitle){
			if(stars[i].getAttribute("rating") <= rating)
				stars[i].children[0].children[0].className = "fa fa-star";
			else stars[i].children[0].children[0].className = "fa fa-star-o";
		}  
	}
	stars[0].parentNode.parentNode.parentNode.parentNode.parentNode.setAttribute("rating",rating);
}