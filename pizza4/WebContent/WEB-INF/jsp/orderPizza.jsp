<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
                    "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
  <head>
    <title>pizza order complete</title>
  </head>
  <body>
    <br>
    Success!
    
    <c:url var="studentWelcomeURL" value="studentWelcome.html"/>
    <A HREF="${studentWelcomeURL}"> Back to Student Page</A> 
  </body>
</html>
