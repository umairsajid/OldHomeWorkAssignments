<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
                    "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="admin" class="cs636.pizza.presentation.web.AdminBean"
	scope="session" />
<html>
<head>
<title>Pizza size form</title>
</head>

<body>
<form action="changeSize.jsp" method="get">Add a new pizza size:
<input type="text" name="size"> 
<input type="submit" name="command" value="add">
</form>
<br>
<form action="changeSize.jsp" method="get">
	<select name="size">
	<c:forEach items="${admin.allSizes}" var="curSize">
		<option value="${curSize.id}">${curSize.sizeName} </option>
	</c:forEach>
	</select> 
<input type="submit" name="command" value="remove"></form>

<A HREF="adminWelcome.html">Back to Admin page</A>
</body>
</html>
