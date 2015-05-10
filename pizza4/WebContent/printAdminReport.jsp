<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
                    "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="admin" class="cs636.pizza.presentation.web.AdminBean"/>
<html>
  <head>    
    <title>Admin Report</title>
  </head>
  
  <body>
    Admin Report: <br/>	 
	   Order number   size     room number  status  <br/>
	 <c:forEach items="${admin.adminReport}" var="order">
  	  ${order.id}  ${order.pizzaSize.sizeName} ${order.roomNumber} ${order.statusString} <br/>
	 </c:forEach>
	 
     <A HREF="adminWelcome.html">Back to Admin page</A>    
  </body>
</html>
