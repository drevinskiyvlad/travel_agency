<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1">

    <title>User cabinet</title>
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
                        <li class="menu-item"><a href="our-offer.jsp">Наші пропозиції</a></li>
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
                <span>Особистий Кабінет</span>
            </nav>
        </div>
    </header> <!-- .site-header -->

    <main class="content">
        <div class="fullwidth-block">
            <div class="container">
                <div class="filter-links filterable-nav">
                    <h1>Інформація про користувача:</h1><hr>
                    <h3>Пошта: ${sessionScope.user.email}</h3><br>
                    <h3>Роль: ${sessionScope.user.role}</h3><br>
                    <h3>Ім'я: ${sessionScope.user.firstName}</h3><br>
                    <h3>Фамілія: ${sessionScope.user.lastName}</h3><br>
                    <h3>Номер телефону: ${sessionScope.user.phone}</h3><br>

                    <form accept-charset="UTF-8" role="form" action="logout" method="get">
                        <button type="submit" class="btn btn-black">Вийти</button>
                    </form>

                    <br><hr><br>

                    <c:if test="${sessionScope.user.role == 'user'}">
                        <h2>Ваші замовлення:</h2>
                    </c:if>
                    <c:if test="${sessionScope.user.role == 'manager'}">
                        <h2>Замовлення користувачів:</h2>
                    </c:if>
                    <c:if test="${sessionScope.user.role == 'admin'}">
                        <h2>Список користувачів:</h2>
                    </c:if>
                    <hr>
                    <div class="filterable-items">
                        <div class="filterable-item south-america">
                            <article class="offer-item">
                                <figure class="featured-image">
                                    <img src="images/cities/Paris.jpg" alt="">
                                </figure>
                                <h2 class="entry-title"><a href="#">Efficitur efficitur convallis</a></h2>
                                <p>Sed vitae fermentum lacus in augue massa pellentesque mauris vel iaculis sclerisque
                                    nulla</p>
                                <div class="price">
                                    <strong>$2900</strong>
                                    <small>/10 days</small>
                                </div>
                            </article>
                        </div>
                    </div>

                    <div class="pagination wow fadeInUp">
                        <span class="page-numbers current">1</span>
                        <a href="#" class="page-numbers">2</a>
                        <a href="#" class="page-numbers">3</a>
                        <a href="#" class="page-numbers">4</a>
                    </div>

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
                    <i class="fa fa-map-marker"></i> 15 Bandery ave, Kyiv<br>
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