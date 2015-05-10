<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
                    "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Welcome to Pizza Order Shop</title>
</head>
<body>
<H1>Welcome to the Pizza Shop System!</H1>
<H2>Please select a Service:</H2>


	<c:if test="${student.roomNo != 0}">
		Your Room Number now is: ${student.roomNo}<br/>
	</c:if>
	
	<!-- if no room no. set, go to room form on way to status page -->
    <c:url var="statusNextPageEncURL" value = "${statusNextPageURL}"/>

	<!-- Define relative links for various cases: 
		c:url handles URL rewriting if needed to maintain session -->
	<c:url var="roomFormURL" value="roomForm.html" />
	<c:url var="orderFormURL" value="orderForm.html" />
<UL>	
	<LI><A href="${roomFormURL}"> Change Room Number </a> </LI>
	<LI><A HREF="${orderFormURL}"> Make an order</A> </LI>
	<LI><A HREF="${statusNextPageEncURL}"> Check the status of an order</A><br><br></LI>	
	<LI><A HREF="welcome.html"> Back to Site Homepage</A></LI>
</UL>

</body>
</html>
