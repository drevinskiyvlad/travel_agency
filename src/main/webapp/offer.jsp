<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/date.tld" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1">

    <title>Our Offer</title>
    <!-- Loading third party fonts -->
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:300,300italic,400,600,700" rel="stylesheet"
          type="text/css">
    <link href="fonts/font-awesome.min.css" rel="stylesheet" type="text/css">
    <!-- Loading main css file -->
    <link rel="stylesheet" href="css/animate.min.css">
    <link rel="stylesheet" href="css/style.css">

</head>

<body>

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
                        <li class="menu-item current-menu-item"><a href="our-offer.jsp"><fmt:message
                                key="header.ourOffer"/></a></li>
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
                    <a href="?code=${requestScope.offerItem.code}&sessionLocale=ua" class="ua"><img src="images/ukraine.png" alt="ua"></a>
                    <a href="?code=${requestScope.offerItem.code}&sessionLocale=en" class="en"><img src="images/usa.png" alt="en"></a>
                </div>
            </div>
            <nav class="breadcrumbs">
                <a href="index.jsp"><fmt:message key="header.mainPage"/></a> &rarr;
                <a href="our-offer.jsp"><fmt:message key="header.ourOffer"/></a> &rarr;
                <span><fmt:message key="header.offer"/> ${requestScope.offerItem.code}</span>
                <div class="current-date"><fmt:message key="header.date"/> <ctg:info-time/></div>
            </nav>
        </div>
    </header> <!-- .site-header -->

    <main class="content">
        <div class="offer-container">
            <div class="offer-image"><img src="images/cities/${requestScope.offerItem.city}.jpg"
                                          alt="${requestScope.offerItem.city}"></div>
            <div class="information">
                <c:if test="${requestScope.offerItem.isHot()}">
                    <h2 class="entry-title"><b><fmt:message key="offer.hotTour"/></b> ${requestScope.offerItem.city}
                    </h2>
                </c:if>
                <c:if test="${requestScope.offerItem.isHot() == false}">
                    <h2 class="entry-title"><b><fmt:message key="offer.tour"/></b> ${requestScope.offerItem.city}</h2>
                </c:if>
                <p><b><fmt:message key="offer.type"/></b>: ${requestScope.offerItem.offerType}</p>
                <p><b><fmt:message key="offer.hotel"/></b>: ${requestScope.offerItem.hotel}</p>
                <p><b><fmt:message key="offer.hotelType"/></b>: ${requestScope.offerItem.hotelType}</p>
                <p><b><fmt:message key="offer.places"/></b>: ${requestScope.offerItem.places}</p>
                <p><b><fmt:message key="offer.price"/></b>:
                    <s>${String.format("%.2f", requestScope.offerItem.fullPrice)}$</s> ${String.format("%.2f", requestScope.offerItem.price)}$
                </p>
                <p><b><fmt:message
                        key="offer.discount"/></b>: ${String.format("%.0f", requestScope.offerItem.discount * 100)}%</p>
                <c:if test="${sessionScope.user.role == 'user'}">
                    <c:choose>
                        <c:when test="${requestScope.offerItem.active}">
                            <a href="controller?action=makeOrder&code=${requestScope.offerItem.code}"
                               class="button"><fmt:message key="offer.order"/></a>
                        </c:when>
                        <c:when test="${requestScope.offerItem.active == false}">
                            <div class="inactive-button"><fmt:message key="offer.order"/></div>
                        </c:when>
                    </c:choose>
                </c:if>
                <c:if test="${sessionScope.user != null && sessionScope.user.role != 'user' && requestScope.offerItem.isHot() == false}">
                    <a href="controller?action=makeOfferHot&code=${requestScope.offerItem.code}"
                       class="button"><fmt:message key="offer.makeHot"/></a>
                </c:if>

                <c:if test="${sessionScope.user.role == 'admin'}">
                    <a href="controller?action=deleteOffer&code=${requestScope.offerItem.code}"
                       class="button"><fmt:message key="offer.delete"/></a>
                    <a href="update-offer.jsp?code=${requestScope.offerItem.code}" class="button"><fmt:message
                            key="offer.edit"/></a>
                </c:if>

                <c:if test="${requestScope.error != null}">
                    <Label>
                        <hr>
                        <h4>${requestScope.error}</h4>
                    </Label>
                </c:if>
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