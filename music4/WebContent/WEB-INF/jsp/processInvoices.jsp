<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
                    "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Music Store :: Process Invoices</title>
</head>
<body>
<H1>Process Invoices</H1>
<H2>Choose an invoice to process.</H2>

<ul>
	<c:forEach items="${invoices}" var="invoice">
		<c:url value="processInvoices.html" var="processInvoicesUrl">
			<c:param name="invoiceIdToProcess" value="${invoice.invoiceId}"/>
		</c:url>
		<li>${invoice.invoiceId} - ${invoice.invoiceDate} - ${invoice.totalAmount} - ${invoice.user.emailAddress} - <a href="${processInvoicesUrl}">Process</a></li>
	</c:forEach>
</ul>

	<c:url var="adminWelcomeUrl" value="adminWelcome.html" />

<A href="${adminWelcomeUrl}">Admin Home </a> 
</body>
</html>
