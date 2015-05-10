<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
                    "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Music Store :: Listen</title>
</head>
<body>
<H1>Listen</H1>
<H2>Choose an mp3 to listen to.</H2>

<c:url var="addToCartUrl" value="cart.html" >
	<c:param name="addItem" value="true"/>
</c:url>


<a href="${addToCartUrl}">Add to Cart</a>

<ol>
	<c:forEach items="${product.tracks}" var="track">
	<!-- the following didn't work with download.html (some kind of browser problem) -->
		<c:url value="download.do" var="downloadURL">
			<c:param name="trackNum" value="${track.trackNumber}"/>
		</c:url>
		<li>${track.title} - <a href="${downloadURL}">mp3</a></li>
	</c:forEach>
</ol>
	<c:url var="catalogURL" value="catalog.html" />
	<c:url var="cartUrl" value="cart.html" />
	<c:url var="userWelcomeUrl" value="userWelcome.html" />
	<c:url value="product.html" var="productURL">
		<c:param name="productCode" value="${product.code}" />
	</c:url>
<UL>	
	<LI><A HREF ="${productURL}">Back to Product Page</a></li>
	<LI><A href="${catalogURL}">Browse Catalog </a> </LI>
	<LI><A HREF="${cartURL}">View Cart</A> </LI>
	<LI><A href="${userWelcomeUrl}">User Home </a> </LI>
</UL>

</body>
</html>
