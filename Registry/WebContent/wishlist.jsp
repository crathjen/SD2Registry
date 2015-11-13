<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User Wish List</title>
<script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/knockout/3.3.0/knockout-min.js"></script>
<script src="/Registry/resources/js/wishlist.js" language="javascript"></script>
<script type="text/javascript">
	var currentUser;
	
	$(document).ready(function() {	
		ko.applyBindings();
		$.get("/Registry/REST/getUser", function(data) {
			currentUser = data;	
			console.log(data);
		})
		
		// AJAX call to get all of the wishlists from the database when the page loads.
		$.ajax({
			url : "/Registry/REST/wishLists",
			dataType : "json",
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					var newWishList = new WishList(data[i].id, data[i].name);
					//	populate items observable array with existing items in wishlist
					for(var j = 0; j < data[i].items.length; j++) {
						newWishList.items.push(data[i].items[j]);
					}
					newWishList.account(data[i].account);
					wishLists.push(newWishList);
				}

				console.log(data);
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
				console.log(data);
			}

		})
		
		// shows the wishlist editing tables, hides the 'admin' wishlists available to edit,
		// and resets ViewModel wishListEdit to a fresh WishList
		$("#newWishListButton").click(function(){
			wishListEdit.id=0;
			wishListEdit.name("");
			wishListEdit.items.removeAll();
			wishListEdit.account({});
			$("#wishListEditDiv").show();
			$("#wishListAdmin").hide();
		});
		
	}); //end $(document).ready
</script>

<style>

	#wishlistItemsTable, #inventoryTable{
		padding: 2rem;
		border: 1px solid black;
		border-radius: 10px;
		margin: 2rem;
		height: 25rem;
		width: 40%;
		float: left;
	}
	
</style>
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
			<tbody data-bind="foreach: wishLists">
				<tr data-bind="attr: {'data-id': id}">
					<td><span style = "cursor: pointer; display: inline-block;" data-bind="text: name, click: editWishList"></span></td>
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
	</div>
	
	<!-- CONTAINS THE TABLES FOR ALL INVENTORY AND INVENTORY OF THE EDITABLE WISHLIST YOU HAVE SELECTED -->
	<div id="wishListEditDiv" style="display: none">
		
		<!-- TABLE CONTAINS ALL OF THE ITEMS IN THE DATABASE -->
		<div id="inventoryTable">
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
					<tr data-bind="attr: {'data-id': id}">
						<td><span style = "cursor: pointer; display: inline-block;" data-bind="text: name, event: { click: addToWL }, attr: {title: description}"></span></td>
						<td><span data-bind="text: price"></span></td>
						<td><span data-bind="text: vendor"></span></td>
					</tr>
				</tbody>
			</table>
		</div>

		<!-- TABLE CONTAINS ALL OF THE ITEMS IN THE SELECTED WISHLIST -->
		<div id="wishlistItemsTable">
			<label>Wish List Name: <input type="text" id="wishListName"
				name="wishListName" data-bind="value: wishListEdit.name" />
				<button id="btnSave" data-bind="click: wishListEdit.saveWishList">Save</button>
				<button id = "btnCancel"  data-bind= "click: cancelWishListEdit">Cancel</button>
			</label>
			<table>
				<thead>
					<tr>
						<th>Name</th>
						<th>Price</th>
						<th>Vendor</th>
					</tr>
				</thead>
				<tbody data-bind="foreach: wishListEdit.items">
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
	
</body>
</html>












