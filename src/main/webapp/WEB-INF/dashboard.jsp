<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" href="/css/main.css"/></head>
</head>
<body class="body">
	<h1 class="title">Welcome ${ userLastName }, ${ userFirstName }!</h1>
	<a class="topLink" href="/clear">Logout</a>
	
	<p>Course Schedule</p>
	
	<table class="table table-hover form">
		<tr>
			<th>Class Name</th>
			<th>Instructor</th>
			<th>Weekday</th>
			<th>Price</th>
			<th>Time</th>
		</tr>
		<c:forEach var="course" items="${ allCourses }">
			<tr>
				
				<td><a href="/classes/${ course.id }"><c:out value="${ course.name }"></c:out></a>
					<c:choose>
						<c:when test="${ course.instructor.id == userId }">
						<a href="/classes/${ course.id }/edit">Edit</a>
						</c:when>
						<c:otherwise>
						</c:otherwise>
					</c:choose>
				</td>
				<td>${ course.instructor.firstName }</td>
				<td>${ course.day }</td>
				<td>${ course.price }</td>
				<td>${ course.time }</td>
				
			</tr>
		</c:forEach>
	</table>
	
	<a href="/classes/new">+ new Course</a>

	
</body>
</html>