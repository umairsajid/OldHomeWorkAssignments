<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
                    "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>Pizza Order Form</title>
</head>

<body>
<h1><u>Order Your Pizza Here</u></h1>

<!-- for second display of form: error message from previous try -->
<font color="red">  ${errorMessage} <br/> </font>

<c:if test="${student.roomNo > 0}">
		Your selected room number now is: ${student.roomNo}<br/>
</c:if>

<c:if test="${empty allSizes}">
	<font color="red"> 
     Note: Admin needs to add pizza sizes (go to home page, then admin page)
     </font>
</c:if>
<br>
<c:url var="orderPizzaURL" value="orderPizza.html"/>

<!--change to method="post" when development is done -->
<form method="get" action="${orderPizzaURL}">

Pizza Size: <br>  
<c:forEach items="${allSizes}" var="curSize">
	<input type="radio" name="sizeId" value="${curSize.id}">  ${curSize.sizeName} <br>
</c:forEach> <br>

Pizza Toppings: <br>
<c:forEach items="${allToppings}" var="curTopping">
	<input type="checkbox" name="toppings" value="${curTopping.id}">  ${curTopping.toppingName}  <br>
</c:forEach> 
<br>
Room for pizza delivery:
	<select name="room" >
		<c:choose>
		<c:when test="${student.roomNo == 0}">
			<!-- no current room number, so no special one selected -->
			<c:forEach begin="1" end="${numRooms}" step="1" var="i">
				<option value="${i}">${i} </option>
			</c:forEach>		
		</c:when>
		<c:otherwise>
			<!-- use current room number selected in the pull-down list -->
			<c:forEach begin="1" end="${student.roomNo - 1}" step="1" var="i">
				<option value="${i}">${i}</option>
			</c:forEach>
			<option value="${student.roomNo}" SELECTED>${student.roomNo}</option>
			<c:forEach begin="${student.roomNo + 1}" end="${numRooms}" step="1" var="i">
				<option value="${i}">${i} </option>
			</c:forEach>	
			</c:otherwise>
		</c:choose>		
	</select>
<br>
<br>
<input type="submit" value="Place Your Order"> 
<input type="reset" value="Reset">
</form>
<c:url var="studentWelcomeURL" value="studentWelcome.html"/>
<A HREF="${studentWelcomeURL}"> Back to Student Page</A> 
</body>
</html>
