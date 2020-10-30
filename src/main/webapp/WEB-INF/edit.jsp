<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css">
<meta charset="ISO-8859-1">
<title>Ideas</title>
</head>
<body>
		<h1>Edit: ${idea.idea}</h1>
	<c:out value="${error}" />
	<form:form action="/ideas/edit/${idea.id}" method="post" modelAttribute="updateIdea">
			<input type="hidden" name="content" value="">
			<p>
				<form:label path="idea">Content: </form:label>
				<form:input path="idea" value="${idea.idea}" />
			</p>
			<p>
				<form:errors path="idea" />
			</p>
			<input type="submit" value="Update" />
		</form:form>
		
		<form action="/ideas/delete/${idea.id}" method="post">
			<input type="hidden" name="content" value="delete">
			<input type="submit"  value="Delete">
		</form>
</body>
</html>