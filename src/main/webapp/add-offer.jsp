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
                        <li class="menu-item"><a href="user-cabinet.jsp">До кабінету: ${sessionScope.user.firstName}</a>
                        </li>
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
                <a href="our-offer.jsp">Наші пропозиції</a> &rarr;
                <span>Створення пропозиції</span>
            </nav>
        </div>
    </header> <!-- .site-header -->

    <main class="content">
        <div class="offer-container">
            <div class="information">
                <form accept-charset="UTF-8" role="form" action="updateOffer" method="get">
                    <p><b>Місто</b>: <input type="text" name="city" class="form-control"
                                            placeholder="Kyiv">
                    <p><b>Тип поїздки</b>:
                        <select id="offerType" name="offerType">
                            <c:forEach items="${requestScope.offerTypes}" var="type">
                                <option value="${type}">${type}</option>
                            </c:forEach>
                        </select>
                    <p><b>Готель</b>: <input type="text" name="hotelName" class="form-control"
                                             placeholder="Hotel France"></p>
                    <p><b>Тип Готелю</b>:
                        <select id="hotelType" name="hotelType">
                            <c:forEach items="${requestScope.hotelTypes}" var="type">
                                <option value="${type}">${type}</option>
                            </c:forEach>
                        </select>
                    <p><b>Кількість місць</b>: <input type="number" name="places" class="form-control"
                                                      placeholder="100"> місць</p>
                    <p><b>Ціна</b>:
                        <input type="number" name="price" step="0.1" class="form-control"
                               placeholder="500">$
                    </p>
                    <p><b>Знижка</b>: <input type="number" name="discount" class="form-control"
                                             placeholder="from 5 to 25">%</p>
                    <button type="submit" class="button">Додати</button>
                </form>
                </form>

                <c:if test="${requestScope.error != null}">
                    <Label>
                        <hr>
                        <h4>${requestScope.error}</h4>
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