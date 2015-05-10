<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
                    "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Music Store :: Reports</title>
</head>
<body>
<H1>Reports (TODO)</H1>
<H2>Invoices (TODO)</H2>

<ul>
	<c:forEach items="${invoices}" var="invoice">
		<li>${invoice.invoiceId} - ${invoice.invoiceDate} - ${invoice.totalAmount} - ${invoice.user.emailAddress} - ${invoice.processed}</li>
	</c:forEach>
</ul>

<H2>Downloads</H2>

<ul>
	<c:forEach items="${downloads}" var="download">
		<li>${download.downloadId} - ${download.downloadDate} - ${download.track.title} - ${download.user.emailAddress}</li>
	</c:forEach>
</ul>

	<c:url var="adminWelcomeUrl" value="adminWelcome.html" />

<A href="${adminWelcomeUrl}">Admin Home </a> 
</body>
</html>
