function WishList(id, name) {
	var self = this;
	
	this.id = id;
	this.name = ko.observable(name || "");
	
	
	this.editWishList = function() {
		alert(self.id);
		
	}
	this.deleteWishList = function() {
		alert(self.id);
	}
}

 var wishLists = ko.observableArray();
 
