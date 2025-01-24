<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit account</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" type="text/css" href="resources/css/navbar.css">
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<%@ include file="sidebar.jsp"%>
			<main class="col-md-10 ms-sm-auto px-4">
				<div class="container mt-5">
					<c:if test="${not empty successMessage and successMessage != null}">
						<div class="alert alert-success">${successMessage}</div>
					</c:if>
					<c:if test="${not empty errorMessage and errorMessage != null}">
						<div class="alert alert-danger">${errorMessage}</div>
					</c:if>
					<h3 class="mb-4">Edit account</h3>
					<form action="editaccount" method="post">
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
						<div class="mb-3">
							<label for="firstName" class="form-label">First name</label> <input
								type="text" class="form-control" name="firstName" id="firstName"
								value="${loggedInUser.firstName}" required>
						</div>
						<div class="mb-3">
							<label for="lastName" class="form-label">Last name</label> <input
								type="text" class="form-control" name="lastName" id="lastName"
								value="${loggedInUser.lastName}" required>
						</div>
						<div class="mb-3">
							<label for="email" class="form-label">Email address</label> <input
								type="email" class="form-control" id="email" name="email"
								placeholder="name@example.com" value="${loggedInUser.email}"
								required>
						</div>
						<div class="mb-3">
							<label class="form-label d-block">Gender</label>
							<div class="form-check form-check-inline">
								<input class="form-check-input" type="radio" name="gender"
									id="male_radio" value="1"
									${loggedInUser.gender == 1 ? 'checked' : ''}> <label
									class="form-check-label" for="male_radio">Male</label>
							</div>
							<div class="form-check form-check-inline">
								<input class="form-check-input" type="radio" name="gender"
									id="female_radio" value="2"
									${loggedInUser.gender == 2 ? 'checked' : ''}> <label
									class="form-check-label" for="female_radio">Female</label>
							</div>
						</div>
						<div class="mb-3">
							<label for="phoneNumber" class="form-label">Phone number</label>
							<input type="tel" id="phoneNumber" name="phoneNumber"
								class="form-control" value="${loggedInUser.phoneNumber}">
						</div>
						<button type="submit" class="btn btn-primary">Save
							changes</button>
					</form>
				</div>
			</main>
		</div>
	</div>
</body>
</html>