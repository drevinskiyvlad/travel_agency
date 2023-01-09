<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registration Page</title>
    <link href="styles/my_style.css" rel="stylesheet">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
</head>
<body>
<div class="sidenav">
    <div class="login-main-text">
        <h2>Travel Agency<br> Registration Page</h2>
        <p>Register from here to access.</p>
    </div>
</div>

<div class="main">
    <div class="col-md-6 col-sm-12">
        <div class="login-form">
            <form accept-charset="UTF-8" role="form" action="registration" method="post">

                <div class="form-group">
                    <label>Email</label>
                    <input type="text" name="email" class="form-control" placeholder="doe@example.com">
                </div>

                <div class="form-group">
                    <label>Password</label>
                    <input type="password" name="password" class="form-control" placeholder="Password">
                </div>

                <div class="form-group">
                    <label>First Name</label>
                    <input type="text" name="firstName" class="form-control" placeholder="John">
                </div>

                <div class="form-group">
                    <label>Last Name</label>
                    <input type="text" name="lastName" class="form-control" placeholder="Doe">
                </div>

                <div class="form-group">
                    <label>Phone Number</label>
                    <input type="text" name="phone" class="form-control" placeholder="+380680000000">
                </div>

                <button type="submit" class="btn btn-black">Register</button>
            </form>
            <c:if test="${sessionScope.is_register_valid == false}">
                <Label>
                    <hr><h2>Email or Phone is not valid</h2>
                </Label>
            </c:if>
        </div>
    </div>
</div>

</body>
</html>
