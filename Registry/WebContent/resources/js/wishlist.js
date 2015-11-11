function WishList(id, name) {
	var self = this;
	
	this.id = id;
	this.name = ko.observable(name || "");
	
	
	this.editWishList = function() {
		wishListEdit.id = self.id;
		wishListEdit.name(self.name());
		console.log(wishListEdit.id);
	}
	
	this.deleteWishList = function() {
		alert(self.id);
	}
	
	this.saveWishList = function() {
		$.ajax({
			url: "/Registry/REST/wishLists/save",
			dataType: "json",
			data: self,
			success: function(data) {
				console.log("success");
				console.log(data);
				if (data > 0) {
					
				}
			}
		});
	}
}

var wishLists = ko.observableArray();

var wishListEdit = new WishList();