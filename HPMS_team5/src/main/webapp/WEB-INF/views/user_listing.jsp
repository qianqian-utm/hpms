<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title}</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<%@ include file="sidebar.jsp"%>
			<main class="col-md-10 ms-sm-auto px-4">
				<c:if test="${not empty successMessage}">
					<div class="alert alert-success">${successMessage}</div>
				</c:if>
				<div class="d-flex justify-content-between">
					<h3>User information</h3>
					<a href="add_user">
						<button type="button" class="btn btn-primary">
							<i class="bi bi-plus"></i>Add User
						</button>
					</a>
				</div>

				<table class="table table-bordered">
					<thead>
						<tr>
							<th scope="col">Name</th>
							<th scope="col">Gender</th>
							<th scope="col">Phone Number</th>
							<th scope="col">Email</th>
							<th scope="col">User Type</th>
							<th scope="col">User Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${users}" var="user">
							<tr>
								<td>${user.firstName} ${user.lastName}</td>
								<td>${user.gender == 1 ? 'Male' : (user.gender == 2 ? 'Female' : '-')}</td>
								<td>${user.phoneNumber}</td>
								<td>${user.email}</td>
								<td>${user.role == 'ADMIN' ? 'Admin' : 'Patient'}
								</td>
								<td><c:if test="${user.id != currentUser.id}">
										<a href="edit_user/${user.id}"
											class="btn btn-outline-secondary"> <i
											class="bi bi-pencil-fill"></i>
										</a>
										<form action="delete_user/${user.id}" method="post"
											style="display: inline;">
											<input type="hidden" name="${_csrf.parameterName}"
												value="${_csrf.token}" />
											<button type="submit" class="btn btn-outline-danger"
												onclick="return confirm('Are you sure you want to delete this user?')">
												<i class="bi bi-trash-fill"></i>
											</button>
										</form>
									</c:if></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</main>
		</div>
	</div>
</body>
</html>