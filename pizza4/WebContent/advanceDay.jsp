<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
                    "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="admin" class="cs636.pizza.presentation.web.AdminBean"/>
<html>
<head>
<title>Advance day Result</title>
</head>

<body>
Advance day:
<%=admin.advanceDay()%>
<br>
<A HREF="adminWelcome.html"> Back to Admin page</A>
</body>
</html>
