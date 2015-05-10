<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
                    "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="admin" class="cs636.pizza.presentation.web.AdminBean"/>
<jsp:setProperty name="admin" property="command" param="command"/>
<jsp:setProperty name="admin" property="item" param="topping"/>
<html>
  <head>    
    <title>Result - Change toppings</title>
  </head>
  
  <body>
  <%=admin.changeTopping()%>
  <br/>
  <A HREF="adminWelcome.html"> Back to Admin Page</A>
  </body>
</html>
