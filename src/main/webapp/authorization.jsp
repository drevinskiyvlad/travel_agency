<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login page</title>
    <link href="css/style.css" rel="stylesheet">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <!-- Loading third party fonts -->
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:300,300italic,400,700" rel="stylesheet"
          type="text/css">
    <link href="fonts/font-awesome.min.css" rel="stylesheet" type="text/css">
</head>
<body>

<header class="site-header wow fadeInDown">
    <div class="container">
        <div class="header-content">
            <div class="branding">
                <img src="images/logo.png" alt="Company Name" class="logo">
                <h1 class="site-title"><a href="index.jsp">Travel agency</a></h1>
                <small class="site-description">Подорожуйте разом з нами</small>
            </div>

            <nav class="main-navigation">
                <button type="button" class="menu-toggle"><i class="fa fa-bars"></i></button>
                <ul class="menu">
                    <li class="menu-item"><a href="our-offer.jsp">Наші пропозиції</a></li>
                    <c:if test="${sessionScope.user == null}">
                        <li class="menu-item current-menu-item"><a href="user-cabinet.jsp">Увійти</a></li>
                    </c:if>
                </ul>
            </nav>

            <div class="social-links">
                <a href="" class="facebook"><i class="fa fa-facebook"></i></a>
                <a href="" class="twitter"><i class="fa fa-twitter"></i></a>
                <a href="" class="google-plus"><i class="fa fa-google-plus"></i></a>
                <a href="" class="pinterest"><i class="fa fa-pinterest"></i></a>
            </div>
        </div>
        <nav class="breadcrumbs">
            <a href="index.jsp">Головна</a> &rarr;
            <span>Авторизація</span>
        </nav>
    </div>
</header> <!-- .site-header -->
<main class="content">
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
                <c:if test="${sessionScope.invalid_authorization_message != null}">
                    <Label>
                        <hr>
                        <h4>${sessionScope.invalid_authorization_message}</h4>
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
</main>

</body>
</html>
