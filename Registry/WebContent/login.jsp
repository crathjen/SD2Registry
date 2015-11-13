<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <title>Login Page</title>
    <link rel="stylesheet" type="text/css" href="/Registry/resources/css/login.css"/>
</head>
<body onload="document.f.username.focus();">
<h3>Login with Username and Password</h3>

<c:if test="${not empty error}">
    <div class="error">${error}</div>
</c:if>
<c:if test="${not empty msg}">
    <div class="msg">${msg}</div>
</c:if>

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
