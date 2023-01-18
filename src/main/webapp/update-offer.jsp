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
                <a href="our-offer.jsp"><fmt:message key="header.ourOffer"/></a> &rarr;
                <a href="offer.jsp?code=${requestScope.offerItem.code}"><fmt:message key="header.offer"/> ${requestScope.offerItem.code}</a>&rarr;
                <span><fmt:message key="header.edit"/> ${requestScope.offerItem.code}</span>
            </nav>
        </div>
    </header> <!-- .site-header -->

    <main class="content">
        <div class="offer-container">
            <div class="offer-image"><img src="images/cities/${requestScope.offerItem.city}.jpg"
                                          alt="${requestScope.offerItem.city}"></div>
            <div class="information">
                <c:if test="${requestScope.offerItem.isHot()}">
                    <h2 class="entry-title"><b><fmt:message key="offer.hotTour"/></b> ${requestScope.offerItem.city}</h2>
                </c:if>
                <c:if test="${requestScope.offerItem.isHot() == false}">
                    <h2 class="entry-title"><b><fmt:message key="offer.tour"/></b> ${requestScope.offerItem.city}</h2>
                </c:if>

                <form accept-charset="UTF-8" role="form" action="updateOffer" method="get">
                    <p><b><fmt:message key="offer.type"/></b>:
                        <select id="offerType" name="offerType">
                            <c:forEach items="${requestScope.offerTypes}" var="type">
                                <option value="${type}">${type}</option>
                            </c:forEach>
                        </select>
                    <p><b><fmt:message key="offer.hotel"/></b>: <input type="text" name="hotelName" class="form-control"
                                             placeholder="Hotel France" value="${requestScope.offerItem.hotel}"></p>
                    <p><b><fmt:message key="offer.hotelType"/></b>:
                        <select id="hotelType" name="hotelType">
                            <c:forEach items="${requestScope.hotelTypes}" var="type">
                                <option value="${type}">${type}</option>
                            </c:forEach>
                        </select>
                    <p><b><fmt:message key="offer.places"/></b>: <input type="number" name="places" class="form-control"
                                                      placeholder="100" value="${requestScope.offerItem.places}"></p>
                    <p><b><fmt:message key="offer.price"/></b>:
                        <input type="number" name="price" step="0.1" class="form-control"
                               placeholder="500" value="${requestScope.offerItem.price}">$
                    </p>
                    <p><b><fmt:message key="offer.discount"/></b>: <input type="number" name="discount" class="form-control"
                                             placeholder="from 5 to 25">%</p>

                    <input value="${requestScope.offerItem.code}" name="code" style="display:none">
                    <button type="submit" class="button"><fmt:message key="offer.save"/></button>
                </form>

                <c:if test="${sessionScope.error != null}">
                    <Label>
                        <hr>
                        <h4>${sessionScope.error}</h4>
                    </Label>
                </c:if>
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