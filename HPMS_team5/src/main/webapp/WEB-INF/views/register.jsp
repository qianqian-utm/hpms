<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Register</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="bg-light">
	<div class="container">
		<div class="row justify-content-center mt-5">
			<div class="col-md-6">
				<div class="card shadow">
					<div class="card-body p-5">
						<h2 class="text-center mb-4">Register</h2>

						<c:if test="${not empty error}">
							<div class="alert alert-danger">${error}</div>
						</c:if>
						<c:if test="${not empty message}">
							<div class="alert alert-success">${message}</div>
						</c:if>

						<form action="${pageContext.request.contextPath}/register" method="post">
							<div class="mb-3">
								<label class="form-label">First Name</label> <input type="text" name="firstName" class="form-control" required>
							</div>

							<div class="mb-3">
								<label class="form-label">Last Name</label> <input type="text"
									name="lastName" class="form-control" required>
							</div>

							<div class="mb-3">
								<label class="form-label">Email</label> <input type="email"
									name="email" class="form-control" required>
							</div>

							<div class="mb-3">
								<label class="form-label">Phone Number</label> <input
									type="text" name="phoneNumber" class="form-control" required>
							</div>

							<div class="mb-3">
								<label class="form-label">Gender</label>
								<div class="form-check">
									<input type="radio" name="gender" value="1"
										class="form-check-input" required> <label
										class="form-check-label">Male</label>
								</div>
								<div class="form-check">
									<input type="radio" name="gender" value="2"
										class="form-check-input" required> <label
										class="form-check-label">Female</label>
								</div>
							</div>

							<div class="mb-3">
								<label class="form-label">Password</label> <input
									type="password" name="password" class="form-control" required>
							</div>

							<div class="mb-3">
								<label class="form-label">Confirm Password</label> <input
									type="password" name="confirmPassword" class="form-control"
									required>
							</div>

							<button type="submit" class="btn btn-primary w-100">Sign
								Up</button>
							<div class="mt-3 text-center">
							    Already have an account? <a href="${pageContext.request.contextPath}/login" class="text-decoration-none">Sign in</a>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>