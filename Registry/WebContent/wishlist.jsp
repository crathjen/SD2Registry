<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User Wish List</title>
<script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/knockout/3.3.0/knockout-min.js"></script>
<script src="https://code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>
<script src="/Registry/resources/js/wishlist.js"></script>
<script type="text/javascript">
	var currentUser;
	
	$(document).ready(function() {	
		ko.applyBindings();
		
		var dfr = $.Deferred();
	 	$.get("/Registry/REST/getUser", function(data) {
			currentUser = data;	
			//console.log(data);
			dfr.resolve();
		});
		
		// AJAX call to get all of the wishlists from the database when the page loads.
		$.ajax({
			url : "/Registry/REST/wishLists",
			dataType : "json",
			success : function(data) {
				$.when(dfr).done(function(){
					for (var i = 0; i < data.length; i++) {
						var newWishList = new WishList(data[i].id, data[i].name);
						console.log(data[i])
						//	populate items observable array with existing items in wishlist
						for(var j = 0; j < data[i].items.length; j++) {
							newWishList.items.push(data[i].items[j]);
							
							
							
						}
						for(var j = 0; j < data[i].zippers.length; j++) {
							newWishList.zippers.push(data[i].zippers[j]);
							if (newWishList.zippers()[j].purchased===null)
								newWishList.zippers()[j].purchased=false;
							newWishList.zippers()[j].purchased=ko.observable(newWishList.zippers()[j].purchased)
						}
						
						newWishList.account(data[i].account);
						
						if(newWishList.account().accountName===currentUser.accountName)
							myWishLists.push(newWishList);
						else purchaseLists.push(newWishList);
					}
	
					//console.log(data);
				})
			}
		})

		// AJAX call to get all of the items (for the inventory table) from the database when the page loads.
		$.ajax({
			url : "/Registry/REST/items",
			dataType : "json",
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					inventory.push(data[i]);
				}
				//console.log(data);
			}

		})
		
		// shows the wishlist editing tables, hides the 'admin' wishlists available to edit,
		// and resets ViewModel wishListEdit to a fresh WishList
		wsEditDialog=$("#wishListEditDiv").dialog({
			title:"Edit Your Wishlist",
			modal:true,
			autoOpen: false,
			width: 1025,
			
			buttons: [
			          {
			            text: "Save",
			            click: function() {
			            	wishListEdit().saveWishList();
			            	console.log($(this))
			              $( this ).dialog( "close" );
			            }
			          },
			          {
			            text: "Cancel",
			            click: function() {
			            	
			              $( this ).dialog( "close" );
			            }
			          }
			        ]
			
		});
		
		wsPurchaseDialog=$(purchaseListItemsTable).dialog({
			title:"Buy something off of this WishList",
			modal:true,
			autoOpen: false,
			
			buttons: [
			          {
			            text: "Buy",
			            click: function() {
			            	wishListPurchase().buyItems();
			              $( this ).dialog( "close" );
			            }
			          },
			          {
			            text: "Cancel",
			            click: function() {
			            	
			              $( this ).dialog( "close" );
			            }
			          }
			        ]
		})
		
		$("#newWishListButton").click(function(){
			/* wishListEdit.id=0;
			wishListEdit.name("");
			wishListEdit.items.removeAll();
			wishListEdit.account({}); */
			
			wsEditDialog.dialog("open");
			wishListEdit(new WishList());
			//$("#wishListEditDiv").show();
			//$("#wishListAdmin").hide();
		});
		
	}); //end $(document).ready
</script>


<link rel="stylesheet" type="css/text" href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.css"/>
<link rel="stylesheet" type="css/text" href="/Registry/resources/CSS/registry.css"/>

</head>
<body>

	<!-- CONTAINS ALL OF THE 'ADMIN' CONTROLS, I.E. THE WISHLISTS YOU CAN PICK FROM TO EDIT -->
	<div id ='wishListAdmin'>
		<table>
			<thead>
				<tr>
					<th>Wish List</th>
					<th>Account Owner</th>
					<th>Options</th>
				</tr>
			</thead>
			<tbody data-bind="foreach: myWishLists">
				<tr data-bind="attr: {'data-id': id}, click: editWishList">
					<td><span style = "cursor: pointer; display: inline-block;" data-bind="text: name"></span></td>
					<td><span data-bind="text: account().accountName"></span></td>
					<td>
						<button style = "margin-left: 15rem;" data-bind="click: deleteWishList">Delete</button>
					</td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="2">
						<button id="newWishListButton" name="newWishList">New Wish List</button>
					</td>
				</tr>
			</tfoot>
		</table>
		<!-- THE TABLE FOR PURCHASING OFF OF OTHERS WISHLIST -->
		<div id ='purchaseLists'>
		<table>
			<thead>
				<tr>
					<th>Wish List</th>
					<th>Account Owner</th>
					<th>Options</th>
				</tr>
			</thead>
			<tbody data-bind="foreach: purchaseLists">
				<tr data-bind="attr: {'data-id': id}">
					<td><span style = "cursor: pointer; display: inline-block;" data-bind="text: name, click: purchaseWishList"></span></td>
					<td><span data-bind="text: account().accountName"></span></td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					
				</tr>
			</tfoot>
		</table>
	</div>
	</div>
	



	
	<!-- CONTAINS THE TABLES FOR ALL INVENTORY AND INVENTORY OF THE EDITABLE WISHLIST YOU HAVE SELECTED -->
	<div id="wishListEditDiv" hidden>
		
		<!-- TABLE CONTAINS ALL OF THE ITEMS IN THE DATABASE -->
		<div>Click an inventory item to add it to your wishlist.  Click an item on your wishlist to remove it.  You may also change the name of your wishlist by editing the text field. </div>
		<div id="inventoryTable">
		<h3>INVENTORY</h3>
			<label id = "invtTable">List Of Items</label>	
			<table>
				<thead>
					<tr>
						<th>Name</th>
						<th>Price</th>
						<th>Vendor</th>
					</tr>
				</thead>
				<tbody data-bind="foreach: inventory">
					<tr data-bind="attr: {'data-id': id}, if: checkWishListEditDuplicates($data)">
						<td>
							<span style = "cursor: pointer; display: inline-block;" 
								  data-bind="text: name, event: { click: addToWL }, attr: {title: description}">
							</span>
						</td>
						<td><span data-bind="text: price"></span></td>
						<td><span data-bind="text: vendor"></span></td>
					</tr>
				</tbody>
			</table>
		</div>

		<!-- TABLE CONTAINS ALL OF THE ITEMS IN THE SELECTED WISHLIST -->
		<div id="wishlistItemsTable">
			<h3>YOUR WISHLIST</h3>
			<label>Wish List Name: <input type="text" id="wishListName"
				name="wishListName" data-bind="value: wishListEdit().name" />

			</label>
			<table>
				<thead>
					<tr>
						<th>Name</th>
						<th>Price</th>
						<th>Vendor</th>
					</tr>
				</thead>
				<tbody data-bind="foreach: wishListEdit().items">
					<tr data-bind="attr: {'data-id': id}">
						<td><span style = "cursor: pointer; display: inline-block;" data-bind="text: name, event: { click: removeFromWL }" ></span></td>
						<td><span data-bind="text: price"></span></td>
						<td><span data-bind="text: vendor"></span></td>
						<td>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		
		
	</div>
	
	
			<!-- TABLE CONTAINS ALL OF THE ITEMS THIS IS FOR PURCHASE -->
		<div id="purchaseListItemsTable" hidden>
			<span data-bind="text: wishListPurchase().name"></span>
			
			
			<table>
				<thead>
					<tr>
						<th>Name</th>
						<th>Price</th>
						<th>Vendor</th>
					</tr>
				</thead>
				<tbody data-bind="foreach: wishListPurchase().items">
					<tr data-bind="attr: {	'data-id': id,
											'buyable': !(wishListFromID(wishListPurchase().id).zipperForItem(id)())? null:'false',
											'title': !(wishListFromID(wishListPurchase().id).zipperForItem(id)())? null:'that item has already been purchased'	
															}">
					<!-- todo add style  -->
						<td data-bind="if: !(wishListFromID(wishListPurchase().id).zipperForItem(id)())"><input type="checkbox" data-bind="checked: wishListPurchase().zipperForItem(id)"></td>
						<td><span style = "cursor: pointer; display: inline-block;" data-bind="text: name" ></span></td>
						<td><span data-bind="text: price"></span></td>
						<td><span data-bind="text: vendor"></span></td>
					</tr>
				</tbody>
			</table>
				
		</div>
	

	
</body>
</html>












