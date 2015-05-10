<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
                    "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- This is the display page for URL welcome.html
	See DispatcherServlet.java for the forward action -->
<html>
  <head>
    <title>Pizza Shop System</title>
  </head>
  
  <body>
  <H1> Welcome to the Pizza Shop System</H1>
  <H2> Please select a Service</H2>
    <A HREF="<c:url value='adminWelcome.html'/>">Admin Service</A><br>
    <A HREF="<c:url value='studentWelcome.html'/>"> Student Service</A><br>
  </body>
</html>
