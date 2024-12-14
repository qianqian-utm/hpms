<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add user</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
	<div class="container mt-5">
	    <h3 class="mb-4">Add user</h3>
	    <form action="UserServlet" method="post">
			<input type="hidden" name="action" value="createUser">
	        <div class="mb-3">
	            <label for="first_name" class="form-label">First name</label>
	            <input type="text" class="form-control" name="first_name" id="first_name" required>
	        </div>
	        <div class="mb-3">
	            <label for="last_name" class="form-label">Last name</label>
	            <input type="text" class="form-control" name="last_name" id="last_name" required>
	        </div>
	        <div class="mb-3">
	            <label class="form-label d-block">Gender</label>
	            <div class="form-check form-check-inline">
	                <input class="form-check-input" type="radio" name="gender" id="male_radio" value="1">
	                <label class="form-check-label" for="male_radio">Male</label>
	            </div>
	            <div class="form-check form-check-inline">
	                <input class="form-check-input" type="radio" name="gender" id="female_radio" value="2">
	                <label class="form-check-label" for="female_radio">Female</label>
	            </div>
	        </div>
	        <div class="mb-3">
	            <label for="phone" class="form-label">Phone number</label>
	            <input type="tel" id="phone" name="phone" class="form-control">
	        </div>
	        <div class="mb-3">
	            <label for="email" class="form-label">Email address</label>
	            <input type="email" class="form-control" id="email" name="email" placeholder="name@example.com" required>
	        </div>
	        <div class="mb-3">
	            <label for="user_role" class="form-label">User type</label>
	            <select class="form-select" name="user_role" id="user_role" required>
	                <option value="1">Admin</option>
	                <option value="2">Patient</option>
	            </select>
	        </div>
	        <button type="submit" class="btn btn-primary">Submit</button>
	    </form>
	</div>
</body>
</html>