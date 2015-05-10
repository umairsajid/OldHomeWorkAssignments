<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
                    "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
  <head>   
    <title>Order Status</title>
  </head>
  <body>
    Status report for room ${student.roomNo} <br/><br/>
    <!--Since we call the BL to get student.status report, we can avoid
     a second call to the BL by setting up a local variable for curStatus
     and using it twice -->
    <c:set var="curStatus" value="${statusReport}"/>
    <c:if test="${empty curStatus}">
    	No orders for room ${student.roomNo}
    </c:if>   
    
    <!-- The following displays nothing if there are no orders -->
    <c:forEach items="${curStatus}" var="order">
       Order for room ${order.roomNumber}: size ${order.pizzaSize.sizeName}, status ${order.statusString} <br/>
       Toppings:
       <c:forEach var="top" items="${order.toppings}">
          ${top.toppingName}
       </c:forEach> 
       <br/><br/>
    </c:forEach>
     <br>
    <br>
    <c:url var="studentWelcomeURL" value="studentWelcome.html" />
    <A HREF="${studentWelcomeURL}"> Back to Homepage</A> 
  </body>
</html>
