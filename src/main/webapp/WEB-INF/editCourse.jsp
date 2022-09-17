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
<title>Create a Course</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" href="/css/main.css"/>
</head>
<body>
	<h1>${ course.name }</h1>
	
	<form:form modelAttribute="course" action="/classes/${ course.id }/edit/update" method="post">
		<input type="hidden" name="_method" value="put"/>
		<form:input type="hidden" path="id" value="${ course.id }"/>
	
		<form:errors path="name"/>
		<div class="mb-3">
  			<form:label path="name" class="form-label">Name: </form:label>
  			<form:input type="text" class="form-control" path="name" placeholder="Course Name" required="true"/>
		</div>
		
		<form:errors path="day"/>
		<div class="mb-3">
  			<form:label path="day" class="form-label">Day of Week: </form:label>
  			<form:input type="text" class="form-control" path="day" placeholder="Course Day" required="true"/>
		</div>
		
		<form:errors path="price"/>
		<div class="mb-3">
			<form:label path="price" class="form-label">Drop-In Price: </form:label>
  			<form:input type="text" class="form-control" path="price" placeholder="Course Price" required="true"/>
		</div>
		
		<form:errors path="time"/>
		<div class="mb-3">
			<form:label path="time" class="form-label">Time: </form:label>
  			<form:input type="Time" class="form-control" path="time" min="06:00:00" max="18:00:00" required="true"/>
		</div>
		
		<form:errors path="description"/>
		<div class="mb-3">
  			<form:label path="description" class="form-label">Description</form:label>
 			<form:textarea class="form-control" path="description" rows="5" required="true"></form:textarea>
		</div>
		<form:input type="hidden" class="form-control" path="instructor" value="${ userId }" required="true"/>
		<a href="/classes">Cancel</a>
		<input type="submit" value="Submit">
	</form:form>
	
	<c:choose>
		<c:when test="${ course.instructor.id == userId }">
		<form action="/classes/${ course.id }/delete" method="post">
    		<input type="hidden" name="_method" value="delete">
    		<input type="submit" value="Delete Course">
		</form>
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>
	
	
</body>
</html>