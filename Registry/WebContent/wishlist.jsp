<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/knockout/3.3.0/knockout-min.js"></script>
<script src="/Registry/resources/js/wishlist.js" language="javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		ko.applyBindings();
		$.ajax({
			url : "/Registry/REST/wishLists",
			dataType : "json",
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					var newWishList = new WishList(data[i].id, data[i].name);
					wishLists.push(newWishList);
				}

				console.log(data);
			}

		})

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
	});
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User Wish List</title>


</head>
<body>
	<table>
		<thead>
			<tr>
				<th>Wish List</th>
				<th>Options</th>
			</tr>
		</thead>
		<tbody data-bind="foreach: wishLists">
			<tr data-bind="attr: {'data-id': id}">
				<td><span data-bind="text: name"></span></td>
				<td>
					<button data-bind="click: editWishList">Edit</button>
					<button data-bind="click: deleteWishList">Delete</button>
				</td>
			</tr>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="2">
					<button id="newWishListButton" name="newWishList">New
						Wish List</button>
				</td>
			</tr>
		</tfoot>

	</table>

	<div id="wishListEditDiv" class="sdHidden" style="display: none">
		<label>Wish List Name: <input type="text" id="wishListName"
			name="wishListName" data-bind="value: wishListEdit.name" />
			<button id="btnSave" data-bind="click: wishListEdit.saveWishList">Save</button>
		</label>

		<div id="inventoryTable">
			<table>
				<thead>
					<tr>
						<th></th>
						<th>Name</th>
						<th>Price</th>
						<th>Vendor</th>
					</tr>
				</thead>
				<tbody data-bind="foreach: inventory">
					<tr data-bind="attr: {'data-id': id}">
						<td></td>
						<td><span data-bind="text: name"></span></td>
						<td><span data-bind="text: price"></span></td>
						<td><span data-bind="text: vendor"></span></td>
						<td>
<!-- 							<button data-bind="click: editWishList">Edit</button>
							<button data-bind="click: deleteWishList">Delete</button> -->
						</td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
<!-- 						<td colspan="2">
							<button id="newWishListButton" name="newWishList">New
								Wish List</button>
						</td> -->
					</tr>
				</tfoot>
			</table>
		</div>


	</div>
</body>
</html>












