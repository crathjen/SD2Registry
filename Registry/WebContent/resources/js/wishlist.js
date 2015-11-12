function WishList(id, name) {
	var self = this;
	
	this.id = id || 0;
	this.name = ko.observable(name || "");
	
	
	this.editWishList = function() {
		
		wishListEdit.id = self.id;
		wishListEdit.name(self.name());
		console.log(wishListEdit.id);
		$("#wishListEditDiv").show();
	}
	
	this.deleteWishList = function() {
		var dataToDelete = ko.toJSON(self);
		$.ajax({
			headers: { 
                'Accept': 'application/json',
                'Content-Type': 'application/json' 
            },
			url: "/Registry/REST/wishLists/delete",
			dataType: "json",
			method: "POST",
			data: dataToDelete,
			success: function(data) {
				if (data===-1) {
					console.log("Something went wrong deleting wish list.")
				} else {
					var tmp = 0;
					for (var i = 0; i < wishLists().length; i++) {
						if (self.id===wishLists()[i].id) {
							tmp = i;
							break;
						}
					}
					wishLists.splice(tmp, 1);
				}
			},
			error: function(status) {
				console.log("Error, status: " + status);
			}
		});
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
				console.log("The id we got back is: " + data);
				if (data > 0) {	//successfully created/altered entity on server
					if (lastId == 0) {
						//creating a new wish list from the data we get back and pushing to the array.
						var returnedWishList = new WishList(data, self.name());
						wishLists.push(returnedWishList);
					} else {
						//updating an existing wishlist
						for(var i = 0; i < wishLists().length; i++) {
							if (data===wishLists()[i].id) {
								wishLists()[i].name(self.name());
								break;
							}
						}
					}
					//reset the self's properties after updating or creating a WishList
					self.name("");
					self.id=0;
				}
				
				$("#wishListEditDiv").hide();
			}
		});
	}
}

var wishLists = ko.observableArray();
var inventory = ko.observableArray();

var wishListEdit = new WishList();

$(document).ready(function() {
	
	$("#newWishListButton").click(function(){
		wishListEdit.id=0;
		wishListEdit.name("");
		$("#wishListEditDiv").show();
	});
	
	
}); // end document.ready