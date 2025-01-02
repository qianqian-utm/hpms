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
<link rel="stylesheet" type="text/css" href="resources/css/navbar.css">
</head>
<body>
	<nav class="col-md-2 d-none d-md-block bg-light sidebar">
		<div class="sidebar-sticky d-flex flex-column h-100">
			<a class="navbar-brand col-sm-3 col-md-2 mr-0" href="#"><img src="${pageContext.request.contextPath}/resources/img/hpms-logo.png" width="25px" alt="HPMS logo">HPMS</a>
			<ul class="nav flex-column">
				<li class="nav-item"><a class="nav-link ${currentPage == 'userlisting' ? 'active' : ''}"
					href="userlisting"> Users <i class="bi bi-people-fill"></i>
				</a></li>
				<li class="nav-item"><a class="nav-link" href="#">
						Appointments <i class="bi bi-file-earmark-text"></i>
				</a></li>
				<li class="nav-item"><a class="nav-link" href="medicalrecord"> Medical
						records <i class="bi bi-clipboard2-pulse"></i>
				</a></li>
				<li class="nav-item"><a class="nav-link" href="transaction">
						Reporting <i class="bi bi-cloud-arrow-down"></i>
				</a></li>
				<li class="nav-item"><a class="nav-link" href="editaccount">
						Edit account <i class="bi bi-gear"></i>
				</a></li>

			</ul>
			<div class="mt-auto mb-3">
			    <a href="signout" class="btn btn-light">
			      <i class="bi bi-box-arrow-right"></i> Logout
			    </a>
		    </div>

		</div>
	</nav>
</body>
</html>