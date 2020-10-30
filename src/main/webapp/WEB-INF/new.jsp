<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css">
<meta charset="ISO-8859-1">
<title>Ideas</title>
</head>
<body>
		<h1>Create a new idea</h1>
	<form:form action="/ideas/new" method="post" modelAttribute="newIdea">

		<p>
			<form:label path="idea">Content: </form:label>
			<form:input path="idea" placeholder="An idea..." />
		</p>
		<p>
			<form:errors path="idea" />
		</p>
		<input type="submit" value="Create" />

	</form:form>
</body>
</html>