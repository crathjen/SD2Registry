<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>Login Page</title>
<script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
<script>
	var timeoutId = 0;
	$(document).ready(function() {

		//if there is a message (either error or successful logout), the #messages
		// div will contain " " (there's some text inside it with a space)... hide after 2.5 seconds
 		if($("#messages").html().contains(" ")){
 			alert("we have a message");
			//use Window.setTimeout to call the hideDiv function after 2500 ms
			//timeoutId is used to track the id of the timeout function to stop it later.
 			timeoutId = setTimeout(hideDiv, 2500);
 		}
 		
	}); //end $(document).ready()	

	//hides the messages div, clears the timeout funciton used to delay the hiding of the div.
	function hideDiv() {
		$("#messages").slideUp(500);
		//stop the timeout function we started earlier.
		clearTimeout(timeoutId);
	}
	
</script>
<style>
   .error {
	    padding: 15px;
	    margin-bottom: 20px;
	    border: 1px solid transparent;
	    border-radius: 4px;
	    color: #a94442;
	    background-color: #f2dede;
	    border-color: #ebccd1;
	}

	.msg {
	    padding: 15px;
	    margin-bottom: 20px;
	    border: 1px solid transparent;
	    border-radius: 4px;
	    color: #31708f;
	    background-color: #d9edf7;
	    border-color: #bce8f1;
	}

	#login-box {
	    width: 300px;
	    padding: 20px;
	    margin: 100px auto;
	    background: #fff;
	    -webkit-border-radius: 2px;
	    -moz-border-radius: 2px;
	    border: 1px solid #000;
	}
	
</style>
</head>
<body onload="document.f.username.focus();">
<h3 id="headh3">Login with Username and Password</h3>

<div id="messages">
	<c:if test="${not empty error}">
	   <div class="error" id="errorMessageDiv">${error}</div>
	</c:if>
	<c:if test="${not empty msg}">
	   <div class="msg" id="logoutMessageDiv">${msg}</div>
	</c:if>
</div>

<form name="f" action="/Registry/login" method="POST">
    <table>
        <tbody>
        <tr>
            <td>User:</td>
            <td><input name="username" value="" type="text"></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input name="password" type="password"></td>
        </tr>
        <tr>
            <td colspan="2"><input name="submit" value="Login" type="submit"></td>
        </tr>
        </tbody>
        <tfoot>
            <tr>
                <td><a href="/Registry/createAccount">Create A New Account</a></td>
            </tr>
        </tfoot>
    </table>
</form>
</body>
</html>
