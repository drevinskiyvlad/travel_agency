<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<!DOCTYPE html>
<html lang="${sessionScope.lang}">
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
                <h1 class="site-title"><a href="index.jsp"><fmt:message key="header.companyName"/></a></h1>
                <small class="site-description"><fmt:message key="header.tagline"/></small>
            </div>

            <nav class="main-navigation">
                <button type="button" class="menu-toggle"><i class="fa fa-bars"></i></button>
                <ul class="menu">
                    <li class="menu-item"><a href="our-offer.jsp"><fmt:message key="header.ourOffer"/></a></li>
                    <li class="menu-item current-menu-item"><a href="user-cabinet.jsp"><fmt:message
                            key="header.userCabinetUnlogined"/></a></li>
                </ul>
            </nav>


            <div class="social-links">
                <a href="" class="facebook"><i class="fa fa-facebook"></i></a>
                <a href="" class="twitter"><i class="fa fa-twitter"></i></a>
                <a href="" class="google-plus"><i class="fa fa-google-plus"></i></a>
                <a href="" class="pinterest"><i class="fa fa-pinterest"></i></a>
            </div>
            <div class="social-links">
                <a href="?sessionLocale=ua" class="ua"><img src="images/ukraine.png" alt="ua"></a>
                <a href="?sessionLocale=en" class="en"><img src="images/usa.png" alt="en"></a>
            </div>
        </div>
        <nav class="breadcrumbs">
            <a href="index.jsp"><fmt:message key="header.mainPage"/></a> &rarr;
            <span><fmt:message key="header.register"/></span>
        </nav>
    </div>
</header> <!-- .site-header -->

<main class="content">
    <div class="main">
        <div class="col-md-6 col-sm-12">
            <div class="login-form">
                <form accept-charset="UTF-8" role="form" action="controller?action=signUp" method="post">
                    <c:if test="${sessionScope.invalid_registration_message != null}">
                        <Label>
                            <h4>${sessionScope.invalid_registration_message}</h4><hr>
                        </Label>
                    </c:if>
                    <div class="form-group">
                        <label><fmt:message key="auth.email"/></label>
                        <input type="text" name="email" class="form-control" placeholder="doe@example.com">
                    </div>

                    <div class="form-group">
                        <label><fmt:message key="auth.password"/></label>
                        <input type="password" name="password" class="form-control" placeholder="Password">
                    </div>

                    <div class="form-group">
                        <label><fmt:message key="reg.firstName"/></label>
                        <input type="text" name="firstName" class="form-control" placeholder="John">
                    </div>

                    <div class="form-group">
                        <label><fmt:message key="reg.lastName"/></label>
                        <input type="text" name="lastName" class="form-control" placeholder="Doe">
                    </div>

                    <div class="form-group">
                        <label><fmt:message key="reg.phone"/></label>
                        <input type="text" name="phone" class="form-control" placeholder="+380680000000">
                    </div>

                    <button type="submit" class="btn btn-black"><fmt:message key="auth.signUp"/></button>
                    <br><br><h5><fmt:message key="reg.currentlyHaveAccount"/><a href="authorization.jsp"> <fmt:message key="auth.signIn"/></a></h5>
                </form>
            </div>
        </div>
    </div>
</main>

</body>
</html>
