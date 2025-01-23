<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${not empty errorMessage}">
	<div class="alert alert-danger">${errorMessage}</div>
</c:if>
<div class="container mt-5">
	<h3 class="mb-4">Edit User</h3>
	<form action="${pageContext.request.contextPath}/edit_user/${user.id}" method="post">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<input type="hidden" name="id" value="${user.id}">

		<div class="mb-3">
			<label for="firstName" class="form-label">First name</label> <input
				type="text" class="form-control" name="firstName" id="firstName"
				value="${user.firstName}" required>
		</div>

		<div class="mb-3">
			<label for="lastName" class="form-label">Last name</label> <input
				type="text" class="form-control" name="lastName" id="lastName"
				value="${user.lastName}" required>
		</div>

		<div class="mb-3">
			<label class="form-label d-block">Gender</label>
			<div class="form-check form-check-inline">
				<input class="form-check-input" type="radio" name="gender"
					id="male_radio" value="1" ${user.gender == 1 ? 'checked' : ''}>
				<label class="form-check-label" for="male_radio">Male</label>
			</div>
			<div class="form-check form-check-inline">
				<input class="form-check-input" type="radio" name="gender"
					id="female_radio" value="2" ${user.gender == 2 ? 'checked' : ''}>
				<label class="form-check-label" for="female_radio">Female</label>
			</div>
		</div>

		<div class="mb-3">
			<label for="phoneNumber" class="form-label">Phone number</label> <input
				type="tel" id="phoneNumber" name="phoneNumber" class="form-control"
				value="${user.phoneNumber}">
		</div>

		<div class="mb-3">
			<label for="email" class="form-label">Email address</label> <input
				type="email" class="form-control" id="email" name="email"
				value="${user.email}" required>
		</div>

		<div class="mb-3">
			<label for="role" class="form-label">User type</label> <select
				class="form-select" name="role" id="role" required>
				<option value="ADMIN" ${user.role == 'ADMIN' ? 'selected' : ''}>Admin</option>
				<option value="USER" ${user.role == 'USER' ? 'selected' : ''}>Patient</option>
			</select>
		</div>

		<div class="d-flex justify-content-between">
			<a href="${pageContext.request.contextPath}/userlisting" class="btn btn-secondary">Cancel</a>
			<button type="submit" class="btn btn-primary">Submit</button>
		</div>
	</form>
</div>