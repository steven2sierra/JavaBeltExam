<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css">
<meta charset="ISO-8859-1">
<title>Ideas</title>
</head>
<body>
	<h1>${idea.idea}</h1>
	<h3>Created by: ${idea.user.name}</h3>
	
	<h2>Users who liked your idea:</h2>
	<table>
		<tr>
			<th>Name</th>
		</tr>
		<c:forEach items="${idea.likedUser}" var="user">
		<tr>
			<td>
				${user.name}
			</td>
		</tr>
		</c:forEach>
	</table>
	
	<a href="/ideas/edit/${idea.id}">Edit</a>
</body>
</html>