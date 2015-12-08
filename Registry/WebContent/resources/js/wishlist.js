function WishList(id, name) {
	var self = this;
	
	this.id = id || 0;
	this.name = ko.observable(name || "");
	this.items = ko.observableArray();
	this.account = ko.observable({});
	this.zippers = ko.observableArray();
	
	
	this.editWishList = function() {
//		wishListEdit(self);
		var templist = new WishList(self.id, self.name());
		
//		wishListEdit(new WishList());
//		wishListEdit().id = self.id;
//		wishListEdit().name(self.name());
		for(var j = 0; j < self.items().length; j++) {
			templist.items.push(self.items()[j]);
		}
		for(var j = 0; j < self.zippers().length; j++) {
			templist.zippers.push(self.zippers()[j]);
		}
		templist.account(self.account());
		wishListEdit(templist);
		console.log(wishListEdit().id);
//		$("#wishListEditDiv").show();
//		$("#wishListAdmin").hide();
		wsEditDialog.dialog("open")
	}
	
	this.purchaseWishList = function() {
		
		var templist=new WishList(self.id, self.name());
//		wishListPurchase().id = self.id;
//		wishListPurchase().name(self.name());
		for(var j = 0; j < self.items().length; j++) {
			templist.items.push(self.items()[j]);
		}
		for(var j = 0; j < self.zippers().length; j++) {
			var tempzip = JSON.parse(ko.toJSON(self.zippers()[j]));
			tempzip.purchased=ko.observable(tempzip.purchased);
			
			templist.zippers.push(tempzip);
		}
		templist.account(self.account());
		wishListPurchase(templist);
		wsPurchaseDialog.dialog("open");
		
//		console.log(wishListPurchase().id);
//		$("#purchaseListItemsTable").show()
//		$("#wishListEditDiv").hide();
//		$("#wishListAdmin").hide();
	}
	
	this.deleteWishList = function(wl,evt) {
		evt.stopPropagation();
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
					for (var i = 0; i < myWishLists().length; i++) {
						if (self.id===myWishLists()[i].id) {
							tmp = i;
							break;
						}
					}
					myWishLists.splice(tmp, 1);
				}
			},
			error: function(status) {
				console.log("Error, status: " + status);
			}
		});
		return false;
	}
	
	this.saveWishList = function() {
//		for (var i = 0; i<self.items().length;i++){
//			var found=false;
//			for (var z=0;z<self.zippers().length;z++){
//				if (self.zippers()[z].itemId===self.items()[i].id){
//					found=true;
//					break;
//				}
//			}
//			if (!found){
//				self.zippers.push({
//					itemId: self.items()[i].id,
//					wishlistId: self.id,
//					purchased: ko.observable(false)
//				})
//			}
//		}
		var uploadData = ko.toJSON(self);
		var lastId = self.id;
		console.log(self.name);
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
					if (lastId === 0) {
						//creating a new wish list from the data we get back and pushing to the array.
						var returnedWishList = new WishList(data, self.name());
						//WE NEED TO LOOP THROUGH THE ITEMS LIST HERE
						for(var i = 0; i < self.items().length; i++) {
							returnedWishList.items.push(self.items()[i]);
						}
						for(var i = 0; i < self.zippers().length; i++) {
							returnedWishList.zippers.push(self.zippers()[i]);
						}
						returnedWishList.account(currentUser);
						myWishLists.push(returnedWishList);
					} else {
						//updating an existing wishlist
						for(var i = 0; i < myWishLists().length; i++) {
							console.log(myWishLists()[i].id);
							if (data === myWishLists()[i].id) {
//								myWishLists()[i].name(self.name());
//								//WE NEED TO LOOP THROUGH THE ITEMS LIST HERE
//								myWishLists()[i].items.removeAll();
//								for(var j = 0; j < self.items().length; j++) {
//									myWishLists()[i].items.push(self.items()[j]);
//								}
//								myWishLists()[i].account(self.account());
								myWishLists()[i]=self;
								myWishLists.valueHasMutated();
								break;
							}
						}
//						console.log(self);
					}
					//wishListEdit(new WishList());
					//reset the self's properties after updating or creating a WishList
//					self.name(""); 
//					self.id=0;
//					self.items.removeAll();
				}
				
				//$("#wishListEditDiv").hide();
				//$("#wishListAdmin").show();
			}
		});
	}
	this.zipperForItem=function(itemID){
		console.log(itemID)
		for (var h=0;h<self.zippers().length;h++){
			console.log(self.zippers()[h].itemId)
			if (self.zippers()[h].itemId===itemID){
				console.log("should be here")
				
					return self.zippers()[h].purchased;
			
			}
		}
		
		self.zippers.push({
			itemId: itemID,
			wishlistId: self.id,
			purchased: ko.observable(false)
		})
		console.log("should not be here")
		//return true;
		return self.zippers()[self.zippers().length-1].purchased();
	}
	
	
	this.buyItems=function(){
		console.log(ko.toJSON(self))
		$.ajax({
			url: "/Registry/REST/wishLists/buy",
			contentType: "application/json",
			method: "POST",
			data: ko.toJSON(self),
			success: function() {
				for (var i =0; i<purchaseLists().length; i++){
					if (purchaseLists()[i].id===self.id){
						purchaseLists()[i]=self;
						
						purchaseLists.valueHasMutated();
						break;
					}
				}
			
			}
		})
			
	}
}


var myWishLists = ko.observableArray();
var inventory = ko.observableArray();
var purchaseLists = ko.observableArray();

var wishListEdit = ko.observable(new WishList());
var wishListPurchase = ko.observable(new WishList());

function addToWL(data, event) {
	wishListEdit().items.push(data);
}

function removeFromWL(data, event) {
	console.log(data);
	if (!wishListEdit().zipperForItem(data.id)()){
	wishListEdit().items.remove(data);
	}
	else alert ("That item cannot be removed from your list because it has already been purchased")
	
}

function checkWishListEditDuplicates(inventoryItem) {
	//console.log(inventoryItem.name);
	for (var i = 0; i < wishListEdit().items().length; i += 1) {
		if (inventoryItem.id === wishListEdit().items()[i].id){
			return false;
		}
	}
	return true;
}

function cancelWishListEdit(){
	wishListEdit(new WishList());
//	wishListEdit.id=0;
//	wishListEdit.name("");
//	wishListEdit.items.removeAll();
	$("#wishListEditDiv").hide();
	$("#wishListAdmin").show();
}
function cancelWishListPurchase(){
	$("#purchaseListItemsTable").hide();
	$("#wishListAdmin").show();
}
function wishListFromID(wsID){
	for (var i=0;i<purchaseLists().length;i++){
		if (purchaseLists()[i].id===wsID){
			
			console.log(purchaseLists()[i])
			return purchaseLists()[i];
		}
	}
}
//
//$(document).ready(function() {
//	
////	$("#newWishListButton").click(function(){
////		wishListEdit.id=0;
////		wishListEdit.name("");
////		wishListEdit.items.removeAll();
////		$("#wishListEditDiv").show();
////	});
//	
//	
//}); // end document.ready

