function WishList(id, name) {
	var self = this;
	
	this.id = id || 0;
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
		var uploadData = ko.toJSON(self);
		var lastId = self.id;
		$.ajax({
			headers: { 
                'Accept': 'application/json',
                'Content-Type': 'application/json' 
            },
			url: "/Registry/REST/wishLists/save",
			dataType: "json",
			method: "POST",
			data: uploadData,
			success: function(data) {
				console.log("success");
				console.log(data);
				if (data > 0) {	//successfully created/altered entity on server
					if (lastId == 0) {
						self.id = data;  //assign id to new wishlist
						wishLists.push(self);
					}
				}
			}
		});
	}
}

var wishLists = ko.observableArray();

var wishListEdit = new WishList();