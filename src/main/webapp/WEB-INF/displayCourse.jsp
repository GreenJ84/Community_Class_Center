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
<title>Display Course</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" href="/css/main.css"/>
</head>
<body class="body">
	<a href="/classes" class="topLink">Back to Dashboard</a>
	<h1>${ course.name } with ${ course.instructor.firstName }</h1>
	<p>Day: ${ course.day }</p>
	<p>Cost: $${ course.price }</p>
	<p>Time: ${ course.time }</p>
	<br>
	<p>${ course.description }</p>
	<br>
	<hr>
	
	<h3>Students</h3>
	
	<c:forEach var="student" items="${ course.studentsAttending }">
	<p>${ student.firstName } - ${ student.email }</p>
	</c:forEach>
	
	<h1>Add Students to Course</h1>
	<div class="add">
	<div style="width: 200px !important;">
	<h3>New Student</h3>
	<form:form modelAttribute="student" action="/classes/${ course.id }/createStudent" method="post">
		
		<form:errors class="validation" path="firstName"/>
		<div class="mb-3">
	  			<form:label path="firstName" class="form-label">Student First Name: </form:label>
	  			<form:input type="text" class="input form-control" path="firstName" placeholder="Student First Name" required="true"/>
			</div>
		
		<form:errors class="validation" path="lastName"/>
		<div class="mb-3">
	  			<form:label path="lastName" class="form-label">Student Last Name: </form:label>
	  			<form:input type="text" class="input form-control" path="lastName" placeholder="Student Last Name" required="true"/>
			</div>
		
		<form:errors class="email" path="email"/>
		<div class="mb-3">
	  			<form:label path="email" class="form-label">Email address: </form:label>
	  			<form:input type="email" class="input form-control" path="email" placeholder="name@example.com" required="true"/>
			</div>
			<input type="submit" value="Add Student">
	</form:form> 
	</div>
	
	<div style="width: 200px !important;">
	<h3>Existing Student</h3>
 	<form  action="/classes/${ course.id }/addStudent" method="post">
		<select name="studentId" required>
			<c:forEach var="student" items="${ allStudents }">
				<c:choose>
				<c:when test="${ student.courses.contains(course) }">
				<option value="${ student.id }" disabled>${ student.firstName } - ${ student.email }</option>
				</c:when>
				<c:otherwise>
				<option value="${ student.id }">${ student.firstName } - ${ student.email }</option>
				</c:otherwise>
				</c:choose>
			</c:forEach>
		</select>
		<input type="submit" value="Add Student">
	</form>
	</div> 
	</div>
</body>
</html>