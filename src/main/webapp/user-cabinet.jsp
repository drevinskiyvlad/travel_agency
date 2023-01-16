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
    <!-- Loading files for table -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
    <link rel="stylesheet" href="https://unpkg.com/bootstrap-table@1.21.2/dist/bootstrap-table.min.css">
    <script src="https://cdn.jsdelivr.net/npm/jquery/dist/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>
    <script src="https://unpkg.com/bootstrap-table@1.21.2/dist/bootstrap-table.min.js"></script>
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
                <h1>Інформація про користувача:</h1>
                <hr>
                <h3>Пошта: ${sessionScope.user.email}</h3><br>
                <h3>Роль: ${sessionScope.user.role}</h3><br>
                <h3>Ім'я: ${sessionScope.user.firstName}</h3><br>
                <h3>Фамілія: ${sessionScope.user.lastName}</h3><br>
                <h3>Номер телефону: ${sessionScope.user.phone}</h3><br>

                <form accept-charset="UTF-8" role="form" action="logout" method="get">
                    <button type="submit" class="btn btn-black">Вийти</button>
                </form>

                <br>
                <hr>
                <br>

                <c:if test="${sessionScope.user.role == 'user'}">
                    <h2>Ваші замовлення:</h2>
                    <table data-toggle="table" class="wow fadeInUp">
                        <caption>AllOrders</caption>
                        <thead>
                        <tr>
                            <th>Код замовлення</th>
                            <th>Пропозиція</th>
                            <th>Статус</th>
                            <th>Ціна</th>
                            <th>Дії</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${sessionScope.userOrders}" var="order">
                            <c:if test="${order.orderStatus == 'registered'}"><tr style="background-color: rgba(0,212,255,0.1)"></c:if>
                            <c:if test="${order.orderStatus == 'paid'}"><tr style="background-color: rgba(0,255,0,0.1)"></c:if>
                            <c:if test="${order.orderStatus == 'canceled'}"><tr style="background-color: rgba(255,0,0,0.1)"></c:if>
                            <td>${order.code}</td>
                            <td>
                                <a href="offer.jsp?code=${order.offerCode}" target="_blank">
                                        ${order.offerCode}
                                </a>
                            </td>
                            <td>${order.orderStatus}</td>
                            <td>${String.format("%.2f", order.price)}$</td>
                            <td>
                                <a href="deleteOrder?code=${order.code}">
                                    Видалити
                                </a>
                            </td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td><b>Всього:</b><br>${String.format("%.2f", sessionScope.totalPrice)}</td>
                        </tr>
                        </tbody>
                    </table>
                </c:if>
                <c:if test="${sessionScope.user.role == 'manager'}">
                    <h2>Замовлення користувачів:</h2>
                    <table data-toggle="table" class="wow fadeInUp">
                        <caption>AllOrders</caption>
                        <thead>
                        <tr>
                            <th>Код замовлення</th>
                            <th>Пропозиція</th>
                            <th>Користувач</th>
                            <th>Статус</th>
                            <th>Ціна</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${sessionScope.orders}" var="order">
                            <c:if test="${order.orderStatus == 'registered'}"><tr style="background-color: rgba(0,212,255,0.1)"></c:if>
                            <c:if test="${order.orderStatus == 'paid'}"><tr style="background-color: rgba(0,255,0,0.1)"></c:if>
                            <c:if test="${order.orderStatus == 'canceled'}"><tr style="background-color: rgba(255,0,0,0.1)"></c:if>
                            <td>${order.code}</td>
                            <td>
                                <a href="offer.jsp?code=${order.offerCode}" target="_blank">
                                        ${order.offerCode}
                                </a>
                            </td>
                            <td>${order.userEmail}</td>
                            <td>
                                <form accept-charset="UTF-8" role="form" action="updateOrderStatus" method="get">
                                    <select name="orderStatus" id="orderStatus">
                                        <option value="${order.orderStatus}">${order.orderStatus}</option>
                                        <option value="registered">registered</option>
                                        <option value="paid">paid</option>
                                        <option value="canceled">canceled</option>
                                    </select>
                                    <input value="${order.code}" name="code" style="display:none">
                                    <button type="submit" class="check-mark">
                                        <i class="fa fa-check"></i>
                                    </button>
                                </form>
                            </td>
                            <td>${String.format("%.2f", order.price)}$</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <c:if test="${sessionScope.user.role == 'admin'}">
                    <h2>Список користувачів:</h2>
                    <table data-toggle="table" class="wow fadeInUp">
                        <caption>UserList</caption>
                        <thead>
                        <tr>
                            <th>Пошта</th>
                            <th>Роль</th>
                            <th>Ім'я</th>
                            <th>Фамілія</th>
                            <th>Номер телефону</th>
                            <th>Забанений</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${sessionScope.allUsers}" var="user">
                            <tr>
                                <td>${user.email}</td>
                                <td>${user.role}</td>
                                <td>${user.firstName}</td>
                                <td>${user.lastName}</td>
                                <td>${user.phone}</td>
                                <td>${user.banned}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <hr>


                <div class="pagination wow fadeInUp">
                    <span class="page-numbers current">1</span>
                    <a href="#" class="page-numbers">2</a>
                    <a href="#" class="page-numbers">3</a>
                    <a href="#" class="page-numbers">4</a>
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