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
		<h1>Welcome ${user.name}</h1>
	<a href="/logout">Logout</a>
	
	<h3>Ideas</h3>
	
	<!-- low likes and high likes -->
		<!-- jstl sql tag...? -->
	<!-- end of likes and high likes -->
	
	<table>
		<tr>
			<th>Idea</th>
			<th>Created By:</th>
			<th>Likes</th>
			<th>Action</th>
		</tr>
		<c:forEach items="${allIdeas}" var="idea">	
		<tr>
			<td>
				<a href="/ideas/show/${idea.id}">
					${idea.idea}
				</a>
			</td>
			<td>${idea.user.name}</td>
			<!-- collection size -->
			<td>${idea.likedUser.size()}</td>
			<td>
				<!-- toggle -->
				<c:choose>
							<c:when test="${idea.likedUser.contains(user)==false}">
								<form action="/ideas/like/${idea.id}" method="post">
									<input type="submit" value="Like">
								</form>
							</c:when>
							<c:otherwise>
								<form action="/ideas/unlike/${idea.id}" method="post">
									<input type="submit" value="Unlike">
								</form>
							</c:otherwise>
				</c:choose>
			</td>
		</tr>
		</c:forEach>
	</table>
	
	<!-- create new idea -->
	<a href="/ideas/new">Create an idea</a>
</body>
</html>