<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
                    "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Music Store</title>
</head>
<body>
<H1>Welcome to the Music Store Admin Page!</H1>
<H2>Where would you like to go?</H2>
<p> <font color="blue">TODO: admin login </font></p>

	<c:url var="initDbUrl" value="initdb.html" />
	<c:url var="processInvoicesUrl" value="processInvoices.html" />
	<c:url var="reportUrl" value="reports.html" />
<UL>	
	<LI><A href="${initDbUrl}">Initialize database </a> </LI>
	<LI><A HREF="${processInvoicesUrl}">Process invoices</A> </LI>
	<LI><A HREF="${reportUrl}">View reports</A> </LI>
	<LI><A HREF="welcome.html">Back to Site Homepage</A></LI>
</UL>

</body>
</html>
