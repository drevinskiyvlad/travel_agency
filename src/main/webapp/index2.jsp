<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<br/>
<a href="test">Hello Servlet</a><br>
<a href="authorization.jsp">to auth</a>>

<br><br>

<c:if test="${sessionScope.user != null}">
    <h1>Hi, ${sessionScope.user.firstName} ${sessionScope.user.lastName}</h1>
</c:if>
</body>
</html>