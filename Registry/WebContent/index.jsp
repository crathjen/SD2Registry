
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/knockout/3.3.0/knockout-min.js"></script>
<script>
	$(document).ready(function(){
		$.ajax({
			url: "/Registry/REST/AccountTest",
			dataType: "json",
			success: function(data){
				console.log(data);
			}
			
		});
		
		$("#newaccountBtn").click(function() {
			var newaccount = $("#newaccountFrm").serialize();
			$.ajax({
				url: "/Registry/REST/NewAccount",
				dataType: "json",
				type: "POST",
				data: newaccount
			})
		});
	});
</script>
<title>Registry</title>
</head>
<body>
	<div id="newaccountDiv">
		<form id="newaccountFrm">
			<div>Account Name</div><input id="accountnameBox" name="accountnameBox" type="text"/>
			<div>Password</div><input id="passwordBox" name="passwordBox" type="password"/>
			<div>Email</div><input id="emailBox" name="emailBox" type="email"/>
			<div>First Name</div><input id="firstnameBox" name="firstnameBox" type="text"/>
			<div>Last Name</div><input id="lastnameBox" name="lastnameBox" type="text"/>
			<div>Age</div><input id="ageBox" name="ageBox" type="number"/>
			<div id="btnDiv"><button id="newaccountBtn">Submit</button></div>
		</form>
	</div>
</body>
</html>