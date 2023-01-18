<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
                        <li class="menu-item current-menu-item"><a href="our-offer.jsp"><fmt:message key="header.ourOffer"/></a></li>
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
            <nav class="breadcrumbs">
                <a href="index.jsp"><fmt:message key="header.mainPage"/></a> &rarr;
                <span><fmt:message key="header.ourOffer"/></span>
            </nav>
        </div>
    </header> <!-- .site-header -->

    <main class="content">
        <div class="fullwidth-block">
            <div class="container">

                <c:if test="${sessionScope.user.role == 'admin'}">
                    <div class="add-offer-button">
                        <a href="add-offer.jsp" class="button"><fmt:message key="ourOffer.addOffer"/></a>
                    </div>
                </c:if>

                <div class="filter-links filterable-nav">

                </div>

                <div class="filterable-items">
                    <c:forEach items="${requestScope.offers}" var="offer">
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

                <div class="pagination wow fadeInUp">
                    <c:forEach begin="1" end="${requestScope.numberOfPagesInOffers}" var="i">
                        <c:choose>
                            <c:when test="${requestScope.currentPage eq i}">
                                <span class="page-numbers current">${i}</span>
                            </c:when>
                            <c:otherwise>
                                <a href="offerPagination?offerListPage=${i}" class="page-numbers">${i}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>

            </div>

        </div>


    </main> <!-- .content -->

    <footer class="site-footer wow fadeInUp">
        <div class="footer-bottom">
            <div class="container">
                <div class="branding pull-left">
                    <img src="images/logo-footer.png" alt="Company Name" class="logo">
                    <h1 class="site-title"><a href="index.jsp"><fmt:message key="header.companyName"/></a></h1>
                    <small class="site-description"><fmt:message key="footer.tagline"/></small>
                </div>

                <div class="contact-links pull-right">
                    <i class="fa fa-map-marker"></i> <fmt:message key="footer.address"/><br>
                    <i class="fa fa-phone"></i> +380 68 111 22 33<br>
                    <i class="fa fa-envelope"></i> doe@companyname.com
                </div>
            </div>
        </div>
    </footer> <!-- .site-footer -->

</div> <!-- #site-content -->
<script src="js/jquery-1.11.1.min.js"></script>
<script src="js/min/plugins-min.js"></script>
<script src="js/min/app-min.js"></script>

</body>

</html>