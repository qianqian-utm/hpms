<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="resources/css/style.css">
</head>
<body>
	<div class="login_register_div">
	    <div class="container">
	        <div class="form-container">
	            <h1>Login</h1>
	            <form action="LoginServlet" method="post">
	                <label>Email address</label>
	                <input type="email" name="email" required>
	                <label>Password</label>
	                <input type="password" name="password" required>
	                <button type="submit">Submit</button>
	            </form>
	            <p>Don't have an account? <a href="register">Sign up</a></p>
	            <p><%= request.getParameter("error") != null ? request.getParameter("error") : "" %></p>
	        </div>
	    </div>
    </div>
</body>
</html>