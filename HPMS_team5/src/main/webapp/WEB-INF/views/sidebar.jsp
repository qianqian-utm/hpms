<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nav bar</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/navbar.css">
</head>
<body>
	<nav class="col-md-2 d-none d-md-block bg-light sidebar">
		<div class="sidebar-sticky d-flex flex-column h-100">
			<a class="navbar-brand col-sm-3 col-md-2 mr-0" href="#"><img src="${pageContext.request.contextPath}/resources/img/hpms-logo.png" width="25px" alt="HPMS logo">HPMS</a>
			<ul class="nav flex-column">
				<li class="nav-item"><a class="nav-link ${currentPage == 'userlisting' ? 'active' : ''}"
					href="${pageContext.request.contextPath}/userlisting"> Users <i class="bi bi-people-fill"></i>
				</a></li>
				<li class="nav-item"><a class="nav-link" href="#">
						Appointments <i class="bi bi-file-earmark-text"></i>
				</a></li>
				<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/medicalRecords"> Medical
						records <i class="bi bi-clipboard2-pulse"></i>
				</a></li>
				<li class="nav-item"><a class="nav-link" href="transaction">
						Reporting <i class="bi bi-cloud-arrow-down"></i>
				</a></li>
				<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/editaccount">
						Edit account <i class="bi bi-gear"></i>
				</a></li>

			</ul>
			<div class="mt-auto mb-3">
			    <form action="/HPMS/signout" method="POST">
				    <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" />
				    <button type="submit" class="btn btn-light"><i class="bi bi-box-arrow-right"></i> Logout</button>
				</form>
		    </div>

		</div>
	</nav>
</body>
</html>