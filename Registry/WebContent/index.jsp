
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
	});
</script>
<title>Registry</title>
</head>
<body>
	<div>cole was here</div>
</body>
</html>