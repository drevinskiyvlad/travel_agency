<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
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
                    <h1 class="site-title"><a href="index.jsp">Travel agency</a></h1>
                    <small class="site-description">Подорожуйте разом з нами</small>
                </div>

                <nav class="main-navigation">
                    <button type="button" class="menu-toggle"><i class="fa fa-bars"></i></button>
                    <ul class="menu">
                        <li class="menu-item  current-menu-item"><a href="our-offer.jsp">Наші пропозиції</a></li>
                        <c:if test="${sessionScope.user == null}">
                            <li class="menu-item"><a href="user-cabinet.jsp">Увійти</a></li>
                        </c:if>
                        <c:if test="${sessionScope.user != null}">
                            <li class="menu-item"><a href="user-cabinet.jsp">До
                                кабінету: ${sessionScope.user.firstName}</a></li>
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
                <span>Наші пропозиції</span>
            </nav>
        </div>
    </header> <!-- .site-header -->

    <main class="content">
        <div class="fullwidth-block">
            <div class="container">

                <c:if test="${sessionScope.user.role == 'admin'}">
                    <div class="add-offer-button">
                        <a href="add-offer.jsp" class="button">Створити пропозицію</a>
                    </div>
                </c:if>

                <div class="filter-links filterable-nav">
                    <select class="mobile-filter">
                        <option value="*">Show all</option>
                        <option value=".south-america">South America</option>
                        <option value=".asia">Asia</option>
                        <option value=".africa">Africa</option>
                        <option value=".north-america">North America</option>
                        <option value=".europe">Europe</option>
                        <option value=".australia">Australia</option>
                    </select>
                    <a href="#" class=" current wow fadeInRight" data-filter="*">Show all</a>
                    <a href="#" class="wow fadeInRight" data-wow-delay=".2s" data-filter=".south-america">South
                        America</a>
                    <a href="#" class="wow fadeInRight" data-wow-delay=".4s" data-filter=".asia">Asia</a>
                    <a href="#" class="wow fadeInRight" data-wow-delay=".6s" data-filter=".africa">Africa</a>
                    <a href="#" class="wow fadeInRight" data-wow-delay=".8s" data-filter=".north-america">North
                        America</a>
                    <a href="#" class="wow fadeInRight" data-wow-delay="1s" data-filter=".europe">Europe</a>
                    <a href="#" class="wow fadeInRight" data-wow-delay="1.2s" data-filter=".australia">Australia</a>
                </div>

                <div class="filterable-items">
                    <c:forEach items="${requestScope.offers}" var="offer">
                        <div class="filterable-item south-america offer-block"
                             <c:if test="${offer.isHot()}">style="background-color:rgba(255,0,0,0.29)"</c:if>>
                            <article class="offer offer-item">
                                <figure class="featured-image"><img src="images/cities/${offer.city}.jpg"
                                                                    alt="${offer.city}"></figure>
                                <h2 class="entry-title"><a href=""><b>Путівка до</b> ${offer.city}</a></h2>
                                <p><b>Тип</b>: ${offer.offerType}</p>
                                <p><b>Готель</b>: ${offer.hotel}</p>
                                <p><b>Тип готелю</b>: ${offer.hotelType}</p>
                                <p><b>Кількість місць</b>: ${offer.places}</p>
                                <p><b>Всього за</b>:
                                    <s>${String.format("%.2f", offer.fullPrice)}$</s> ${String.format("%.2f", offer.price)}$
                                </p>
                                <a href="offer.jsp?code=${offer.code}" class="button">Подивитись деталі</a>
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
                    <h1 class="site-title"><a href="index.jsp">Travel agency</a></h1>
                    <small class="site-description">Ми покажемо вам іншу сторону цього світу</small>
                </div>

                <div class="contact-links pull-right">
                    <i class="fa fa-map-marker"></i> Провулок Бандери 15, Київ<br>
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