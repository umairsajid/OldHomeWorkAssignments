
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
                    "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<head>
		<title>Room Number Form</title>
	</head>

	<body>
		<!-- nextPageURL: orderStatus.html or studentWelcome.html-->
		<c:url var="nextPageURL" value="${nextPageURL}"/>
		<form method="get" action="${nextPageURL}">
			Room for pizza deliveries:
			<select name="room">
				<c:forEach begin="1" end="${numRooms}" step="1" var="i">
					<option value="${i}"> ${i}
				</c:forEach>
			</select>
			<br>
			<input type="submit" value="Set room number">
		</form>
		<c:url var="studentWelcomeURL" value="studentWelcome.html"/>
    	<A HREF="${studentWelcomeURL}"> Back to Student Page</A> 
	</body>
</html>
