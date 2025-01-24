<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container">
        <div class="row justify-content-center mt-5">
            <div class="col-md-6">
                <div class="card shadow">
                    <div class="card-body p-5">
                        <h2 class="text-center mb-4">Register</h2>
                        
                        <form:form modelAttribute="user" method="post">
                            <div class="mb-3">
                                <form:label path="firstName" class="form-label">First Name</form:label>
                                <form:input path="firstName" class="form-control"/>
                                <form:errors path="firstName" class="text-danger"/>
                            </div>

                            <div class="mb-3">
                                <form:label path="lastName" class="form-label">Last Name</form:label>
                                <form:input path="lastName" class="form-control"/>
                                <form:errors path="lastName" class="text-danger"/>
                            </div>

                            <div class="mb-3">
                                <form:label path="email" class="form-label">Email</form:label>
                                <form:input path="email" type="email" class="form-control"/>
                                <form:errors path="email" class="text-danger"/>
                            </div>

                            <div class="mb-3">
                                <form:label path="phoneNumber" class="form-label">Phone Number</form:label>
                                <form:input path="phoneNumber" class="form-control"/>
                                <form:errors path="phoneNumber" class="text-danger"/>
                            </div>

                            <div class="mb-3">
                                <form:label path="gender" class="form-label">Gender</form:label>
                                <div class="form-check">
                                    <form:radiobutton path="gender" value="1" class="form-check-input"/>
                                    <label class="form-check-label">Male</label>
                                </div>
                                <div class="form-check">
                                    <form:radiobutton path="gender" value="2" class="form-check-input"/>
                                    <label class="form-check-label">Female</label>
                                </div>
                                <form:errors path="gender" class="text-danger"/>
                            </div>

                            <div class="mb-3">
                                <form:label path="password" class="form-label">Password</form:label>
                                <form:password path="password" class="form-control"/>
                                <form:errors path="password" class="text-danger"/>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Confirm Password</label>
                                <input type="password" name="confirmPassword" class="form-control"/>
                            </div>

                            <button type="submit" class="btn btn-primary w-100">Sign Up</button>
                            
                            <div class="mt-3 text-center">
                                Already have an account? <a href="${pageContext.request.contextPath}/login">Sign in</a>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>