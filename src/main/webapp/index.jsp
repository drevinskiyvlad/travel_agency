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
		<link rel="stylesheet" href="style.css">
		

	</head>

	<body class="slider-collapse">
		
		<div id="site-content">
			
			<header class="site-header wow fadeInDown">
				<div class="container">
					<div class="header-content">
						<div class="branding">
							<img src="images/logo.png" alt="Company Name" class="logo">
							<h1 class="site-title"><a href="index.jsp">Travel agency</a></h1>
							<small class="site-description">Ми покажемо іншу сторону цього світу</small>
						</div>
						
						<nav class="main-navigation">
							<button type="button" class="menu-toggle"><i class="fa fa-bars"></i></button>
							<ul class="menu">
								<li class="menu-item"><a href="our-offer.jsp">Наші пропозиції</a></li>
								<li class="menu-item"><a href="user-cabinet.jsp">До особистого кабінету</a></li>
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
				<div class="slider">
					<ul class="slides">
						<li data-background="dummy/slide-1.jpg">
							<div class="container">
								<div class="slide-caption col-md-4">
									<h2 class="slide-title">Відкривай цей світ разом з нами</h2>
									<p>Тільки про дві речі ми будемо шкодувати на смертному одрі - що мало любили і мало подорожували<br><br>©Марк Твен</p>
								</div>
							</div>
						</li>
						<li data-background="dummy/slide-2.jpg">
							<div class="container">
								<div class="slide-caption col-md-4">
									<h2 class="slide-title">Відкривай цей світ разом з нами</h2>
									<p>Людина, яка багато подорожує, схожа на камінь, пронесений водою багато сотень верст: його шорсткості згладжуються, і все в ньому набуває м'яких, заокруглених форм<br><br>©І. Реклю</p>
								</div>
							</div>
						</li>
						<li data-background="dummy/slide-3.jpg">
							<div class="container">
								<div class="slide-caption col-md-4">
									<h2 class="slide-title">Відкривай цей світ разом з нами</h2>
									<p>Подорожі навчають більше, ніж будь-що інше. Іноді один день, проведений в інших місцях, дає більше, ніж десять років життя вдома<br><br>©Анатоль Франс</p>
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
									<h3 class="feature-title">Швидке та легке бронювання квитків</h3>
									<p>Вам не потрібно переживати через квитки або вільні місця через нашу тісну співпрацю з різними компаніями!</p>
								</div>
							</div>
							<div class="col-md-3 col-sm-6 col-xs-12">
								<div class="feature left-icon wow fadeInLeft">
									<i class="icon-plane"></i>
									<h3 class="feature-title">Комфортне перебування в аеропорті</h3>
									<p>Через домовленості з компаніями по всьому світу вам забезпечать комфортне перебування при очікуванні вашого літака.</p>
								</div>
							</div>
							<div class="col-md-3 col-sm-6 col-xs-12">
								<div class="feature left-icon wow fadeInRight">
									<i class="icon-jetski"></i>
									<h3 class="feature-title">Проведіть цей відпочинок на повну</h3>
									<p>Всі розваги для вас і вашої родини вже включені у вартість вашої поїздки!</p>
								</div>
							</div>
							<div class="col-md-3 col-sm-6 col-xs-12">
								<div class="feature left-icon wow fadeInRight" data-wow-delay=".3s">
									<i class="icon-shuttelcock"></i>
									<h3 class="feature-title">Все для вашої зручності</h3>
									<p>Тільки краща їжа різних країн для вас, від кращих кузарів різних країн.</p>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="fullwidth-block offers-section" data-bg-color="#f1f1f1">
					<div class="container">
						<h2 class="section-title">Нові гарячі пропозиції</h2>
						<div class="row">
							<div class="col-md-3 col-sm-6 col-xs-12">
								<article class="offer wow bounceIn">
									<figure class="featured-image"><img src="dummy/offer-thumbnail-1.jpg" alt=""></figure>
									<h2 class="entry-title"><a href="">Nemo enim ipsam voluptatem</a></h2>
									<p>Piditate non provident similique	 sunt in culpa qui oficia deserunt molitia animi est aborum et dolorum fuga</p>
									<a href="#" class="button">See details</a>
								</article>
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
							<small class="site-description">Ми покажемо вам іншу сторону цього світу!</small>
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