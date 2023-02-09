<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1">

    <title>Home Travel</title>
    <!-- Loading third party fonts -->
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:300,300italic,400,700" rel="stylesheet"
          type="text/css">
    <link href="fonts/font-awesome.min.css" rel="stylesheet" type="text/css">
    <!-- Loading main css file -->
    <link rel="stylesheet" href="css/animate.min.css">
    <link rel="stylesheet" href="css/style.css">


</head>

<body class="slider-collapse">

<div id="site-content">

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
                        <c:if test="${sessionScope.user == null}">
                            <li class="menu-item"><a href="user-cabinet.jsp"><fmt:message
                                    key="header.userCabinetUnlogined"/></a></li>
                        </c:if>
                        <c:if test="${sessionScope.user != null}">
                            <li class="menu-item"><a href="user-cabinet.jsp"><fmt:message
                                    key="header.userCabinet"/> ${sessionScope.user.firstName}</a></li>
                        </c:if>
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
        </div>
    </header> <!-- .site-header -->

    <main class="content">
        <div class="slider">
            <ul class="slides">
                <li data-background="images/slide-1.jpg">
                    <div class="container">
                        <div class="slide-caption col-md-4">
                            <h2 class="slide-title"><fmt:message key="index.slideTitle"/></h2>
                            <p><fmt:message key="index.slideTitle.quote1"/><br><br>
                                <fmt:message key="index.slideTitle.quote1.author"/></p>
                        </div>
                    </div>
                </li>
                <li data-background="images/slide-2.jpg">
                    <div class="container">
                        <div class="slide-caption col-md-4">
                            <h2 class="slide-title"><fmt:message key="index.slideTitle"/></h2>
                            <p><fmt:message key="index.slideTitle.quote2"/><br><br>
                                <fmt:message key="index.slideTitle.quote2.author"/></p>
                        </div>
                    </div>
                </li>
                <li data-background="images/slide-3.jpg">
                    <div class="container">
                        <div class="slide-caption col-md-4">
                            <h2 class="slide-title"><fmt:message key="index.slideTitle"/></h2>
                            <p><fmt:message key="index.slideTitle.quote3"/><br><br>
                                <fmt:message key="index.slideTitle.quote3.author"/></p>
                        </div>
                    </div>
                </li>
            </ul>
            <div class="flexslider-controls">
                <div class="container">
                    <ol class="flex-control-nav">
                        <li><a>1</a></li>
                        <li><a>2</a></li>
                        <li><a>3</a></li>
                    </ol>
                </div>
            </div>
        </div>

        <div class="fullwidth-block features-section">
            <div class="container">
                <div class="row">
                    <div class="col-md-3 col-sm-6 col-xs-12">
                        <div class="feature left-icon wow fadeInLeft" data-wow-delay=".3s">
                            <i class="icon-ticket"></i>
                            <h3 class="feature-title"><fmt:message key="index.feature1.title"/></h3>
                            <p><fmt:message key="index.feature1.content"/></p>
                        </div>
                    </div>
                    <div class="col-md-3 col-sm-6 col-xs-12">
                        <div class="feature left-icon wow fadeInLeft">
                            <i class="icon-plane"></i>
                            <h3 class="feature-title"><fmt:message key="index.feature2.title"/></h3>
                            <p><fmt:message key="index.feature2.content"/></p>
                        </div>
                    </div>
                    <div class="col-md-3 col-sm-6 col-xs-12">
                        <div class="feature left-icon wow fadeInRight">
                            <i class="icon-jetski"></i>
                            <h3 class="feature-title"><fmt:message key="index.feature3.title"/></h3>
                            <p><fmt:message key="index.feature3.content"/></p>
                        </div>
                    </div>
                    <div class="col-md-3 col-sm-6 col-xs-12">
                        <div class="feature left-icon wow fadeInRight" data-wow-delay=".3s">
                            <i class="icon-shuttelcock"></i>
                            <h3 class="feature-title"><fmt:message key="index.feature4.title"/></h3>
                            <p><fmt:message key="index.feature4.content"/></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="fullwidth-block offers-section" data-bg-color="#f1f1f1">
            <div class="container">
                <h2 class="section-title"><fmt:message key="index.hotOffers"/></h2>
                <div class="row">
                    <div class="filterable-items">
                        <c:forEach items="${requestScope.hotOffers}" end="2" var="offer">
                            <div class="filterable-item offer-block"
                                 <c:if test="${offer.isHot()}">style="background-color:rgba(255,0,0,0.29)"</c:if>>
                                <article class="offer offer-item">
                                    <figure class="featured-image"><img src="images/cities/${offer.city}.jpg"
                                                                        alt="${offer.city}"></figure>
                                    <h2 class="entry-title"><b><fmt:message key="ourOffer.offer.tripTo"/></b> ${offer.city}</h2>
                                    <p><b><fmt:message key="ourOffer.offer.type"/></b>: ${offer.offerType}</p>
                                    <p><b><fmt:message key="ourOffer.offer.hotel"/></b>: ${offer.hotel}</p>
                                    <p><b><fmt:message key="ourOffer.offer.hotelType"/></b>: ${offer.hotelType}</p>
                                    <p><b><fmt:message key="ourOffer.offer.places"/></b>: ${offer.places}</p>
                                    <p><b><fmt:message key="ourOffer.offer.price"/></b>:
                                        <s>${String.format("%.2f", offer.fullPrice)}$</s> ${String.format("%.2f", offer.price)}$
                                    </p>
                                    <a href="offer.jsp?code=${offer.code}" class="button"><fmt:message key="ourOffer.offer.details"/></a>
                                </article>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </main> <!-- .content -->

    <my:footer/> <!-- .site-footer -->

</div> <!-- #site-content -->
<script src="js/jquery-1.11.1.min.js"></script>
<script src="js/min/plugins-min.js"></script>
<script src="js/min/app-min.js"></script>

</body>

</html>