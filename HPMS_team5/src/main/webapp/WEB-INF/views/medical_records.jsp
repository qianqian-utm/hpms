<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Medical records</title>
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
				<div class="d-flex justify-content-between">
					<h3>Medical records</h3>
				</div>

				<table class="table table-bordered">
					<thead>
						<tr>
							<th scope="col">Patient</th>
							<th scope="col">Date</th>
							<th scope="col">Time</th>
							<th scope="col">Action</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>John Doe</td>
							<td>09/12/2024</td>
							<td>11.30-12.00</td>
							<td>
								<button type="button" class="btn btn-outline-secondary">
									<i class="bi bi-pencil-fill"></i>
								</button>
								<button type="button" class="btn btn-outline-danger">
									<i class="bi bi-trash-fill"></i>
								</button>
							</td>
						</tr>
						<tr>
							<td>Max Born</td>
							<td>07/12/2024</td>
							<td>14.30-15.00</td>
							<td>
								<button type="button" class="btn btn-outline-secondary">
									<i class="bi bi-pencil-fill"></i>
								</button>
								<button type="button" class="btn btn-outline-danger">
									<i class="bi bi-trash-fill"></i>
								</button>
							</td>
						</tr>
						<tr>
							<td>Sarah Care</td>
							<td>06/12/2024</td>
							<td>15.30-16.00</td>
							<td>
								<button type="button" class="btn btn-outline-secondary">
									<i class="bi bi-pencil-fill"></i>
								</button>
								<button type="button" class="btn btn-outline-danger">
									<i class="bi bi-trash-fill"></i>
								</button>
							</td>
						</tr>
					</tbody>
				</table>
			</main>
		</div>
	</div>
</body>
</html>