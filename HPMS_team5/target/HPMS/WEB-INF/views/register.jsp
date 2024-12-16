<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" type="text/css" href="resources/css/style.css">
</head>
<body>
	<div class="login_register_div">
	    <div class="container">
	        <div class="form-container">
	            <h1>Register</h1>
	            <form action="RegisterServlet" method="post">
	                <label>First name</label>
	                <input type="text" name="firstName" required>
	                <label>Last name</label>
	                <input type="text" name="lastName" required>
	                <label>Email address</label>
	                <input type="email" name="email" required>
	                <label>Phone number</label>
	                <input type="text" name="phone_number" required>
	                <label>Gender</label>
	                <div>
	                    <input type="radio" name="gender" value="1" required> Male
	                    <input type="radio" name="gender" value="2" required> Female
	                </div>
	                <label>Password</label>
	                <input type="password" name="password" required>
	                <label>Confirm Password</label>
	                <input type="password" name="confirmPassword" required>
	                <button type="submit">Sign up</button>
	            </form>
	            <p><%= request.getParameter("error") != null ? request.getParameter("error") : "" %></p>
	        </div>
	    </div>
    </div>
</body>
</html>