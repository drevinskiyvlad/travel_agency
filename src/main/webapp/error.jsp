<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1">

  <title>Home Travel</title>
  <!-- Loading third party fonts -->
  <link href="http://fonts.googleapis.com/css?family=Open+Sans:300,300italic,400,700" rel="stylesheet" type="text/css">
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
          <h1 class="site-title"><a href="index.jsp">Travel agency</a></h1>
          <small class="site-description">Подорожуйте разом з нами</small>
        </div>

        <nav class="main-navigation">
          <button type="button" class="menu-toggle"><i class="fa fa-bars"></i></button>
          <ul class="menu">
            <li class="menu-item"><a href="our-offer.jsp">Наші пропозиції</a></li>
            <c:if test="${sessionScope.user == null}">
              <li class="menu-item"><a href="user-cabinet.jsp">Увійти</a></li>
            </c:if>
            <c:if test="${sessionScope.user != null}">
              <li class="menu-item"><a href="user-cabinet.jsp">До кабінету: ${sessionScope.user.firstName}</a></li>
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
    </div>
  </header> <!-- .site-header -->

  <main class="content">
    <div class="error-message">
    <h1>Упс, сталась якась помилка, спробуйте знову</h1><br>
      <h1><a href="index.jsp">На головну</a></h1>
    </div>
  </main> <!-- .content -->

  <footer class="site-footer wow fadeInUp">
    <div class="footer-bottom">
      <div class="container">
        <div class="branding pull-left">
          <img src="images/logo-footer.png" alt="Company Name" class="logo">
          <h1 class="site-title"><a href="index.jsp">Travel agency</a></h1>
          <small class="site-description">Ми покажемо вам іншу сторону цього світу!</small>
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