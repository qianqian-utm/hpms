<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<title>Login</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="bg-light">
	<div class="container">
		<div class="row justify-content-center mt-5">
			<div class="col-md-4">
				<div class="card shadow">
					<div class="card-body p-4">
						<h2 class="text-center mb-4">Login</h2>

						<c:if test="${param.error != null}">
							<div class="alert alert-danger">Invalid email or password</div>
						</c:if>

						<c:if test="${param.logout != null}">
							<div class="alert alert-success">You have been logged out</div>
						</c:if>

						<c:if test="${param.registered != null}">
							<div class="alert alert-success">Registration successful!
								Please login</div>
						</c:if>
						<form action="${pageContext.request.contextPath}/login"
							method="post">
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
							<div class="mb-3">
								<label class="form-label">Email</label> <input type="email"
									name="email" class="form-control" required>
							</div>

							<div class="mb-3">
								<label class="form-label">Password</label> <input
									type="password" name="password" class="form-control" required>
							</div>

							<button type="submit" class="btn btn-primary w-100">Login</button>
						</form>

						<div class="text-center mt-3">
							<p>
								Don't have an account? <a
									href="${pageContext.request.contextPath}/register">Sign up</a>
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>