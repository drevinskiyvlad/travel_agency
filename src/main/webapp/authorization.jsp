<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login page</title>
    <link href="css/my_style.css" rel="stylesheet">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

</head>
<body>
<div class="sidenav">
    <div class="login-main-text">
        <h2>Travel Agency<br> Login Page</h2>
        <p>Login from here to access.<br></p>
        <p>Or click "Register" if you don`t have an account.</p>
    </div>
</div>

<div class="main">
    <div class="col-md-6 col-sm-12">
        <div class="login-form">
            <c:if test="${sessionScope.user == null}">
                <form accept-charset="UTF-8" role="form" action="authorization" method="post">
                    <div class="form-group">
                        <label>User Name</label>
                        <input type="text" name="email" class="form-control" placeholder="User Name">
                    </div>
                    <div class="form-group">
                        <label>Password</label>
                        <input type="password" name="password" class="form-control" placeholder="Password">
                    </div>
                    <button type="submit" class="btn btn-black">Login</button>
                    <a href="registration.jsp">
                        <div class="btn btn-secondary">Register</div>
                    </a>
                </form>
                <c:if test="${sessionScope.is_login_valid == false}">
                    <Label>
                        <hr>
                        <h4>Email or password are incorrect</h4>
                    </Label>
                </c:if>
            </c:if>
            <c:if test="${sessionScope.user != null}">
                <br><br><h4>You are currently logged in</h4><br>
                <a href="index.jsp">
                    <div class="btn btn-secondary">Go to main page</div>
                </a>
            </c:if>
        </div>
    </div>
</div>


</body>
</html>
