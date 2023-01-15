<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registration Page</title>
    <link href="css/style.css" rel="stylesheet">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
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
            <span>Реєстрація</span>
        </nav>
    </div>
</header> <!-- .site-header -->

<main class="content">
    <div class="main">
        <div class="col-md-6 col-sm-12">
            <div class="login-form">
                <form accept-charset="UTF-8" role="form" action="registration" method="post">
                    <c:if test="${sessionScope.invalid_registration_message != null}">
                        <Label>
                            <h4>${sessionScope.invalid_registration_message}</h4><hr>
                        </Label>
                    </c:if>
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
                    <br><br><h5>Вже маєте аккаунт? <a href="authorization.jsp">Увійти</a></h5>
                </form>
            </div>
        </div>
    </div>
</main>

</body>
</html>
