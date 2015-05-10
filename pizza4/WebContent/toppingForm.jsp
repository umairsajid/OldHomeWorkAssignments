<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
                    "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="admin" class="cs636.pizza.presentation.web.AdminBean"/>
<html>
  <head>   
    <title>Change Topping Form </title>
  </head>
  
  <body>
    <form action="changeTopping.jsp" method="get">
	Add a new topping: 
      <input type="text" name="topping">
	  <input type="submit" name="command" value="add">
	  <br>
	</form>
	<form action="changeTopping.jsp" method="get">
	Delete an old topping: 
      <select name="topping">
			<c:forEach items="${admin.allToppings}" var="curTopping">
				<option value="${curTopping.id}">${curTopping.toppingName} </option>
			</c:forEach>
	  </select> 
	  <input type="submit" name="command" value="remove">
    </form>
    <A HREF="adminWelcome.html">Back to Admin page</A>
  </body>
</html>
