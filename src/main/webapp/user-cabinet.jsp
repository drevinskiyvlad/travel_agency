<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
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
                    <h1 class="site-title"><a href="index.jsp"><fmt:message key="header.companyName"/></a></h1>
                    <small class="site-description"><fmt:message key="header.tagline"/></small>
                </div>

                <nav class="main-navigation">
                    <button type="button" class="menu-toggle"><i class="fa fa-bars"></i></button>
                    <ul class="menu">
                        <li class="menu-item"><a href="our-offer.jsp"><fmt:message
                                key="header.ourOffer"/></a></li>
                        <li class="menu-item current-menu-item"><a href="user-cabinet.jsp"><fmt:message
                                key="header.userCabinet"/> ${sessionScope.user.firstName}</a></li>
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
                <span><fmt:message key="header.userCabinet.breadcrumbs"/></span>
            </nav>
        </div>
    </header> <!-- .site-header -->

    <main class="content">
        <div class="fullwidth-block">
            <div class="container">
                <h1><fmt:message key="userCabinet.userInformation"/>:</h1>
                <hr>
                <h3><fmt:message key="userCabinet.email"/>: ${sessionScope.user.email}</h3><br>
                <h3><fmt:message key="userCabinet.role"/>: ${sessionScope.user.role}</h3><br>
                <h3><fmt:message key="userCabinet.firstName"/>: ${sessionScope.user.firstName}</h3><br>
                <h3><fmt:message key="userCabinet.lastName"/>: ${sessionScope.user.lastName}</h3><br>
                <h3><fmt:message key="userCabinet.phone"/>: ${sessionScope.user.phone}</h3><br>

                <form accept-charset="UTF-8" role="form" action="logout" method="get">
                    <button type="submit" class="btn btn-black"><fmt:message key="userCabinet.exit"/></button>
                </form>

                <br>
                <hr>
                <br>

                <c:if test="${sessionScope.user.role == 'user'}">
                    <h2><fmt:message key="userCabinet.userOrders"/>:</h2>
                    <table data-toggle="table" class="wow fadeInUp">
                        <caption>AllOrders</caption>
                        <thead>
                        <tr>
                            <th><fmt:message key="userCabinet.code"/></th>
                            <th><fmt:message key="userCabinet.offer"/></th>
                            <th><fmt:message key="userCabinet.status"/></th>
                            <th><fmt:message key="userCabinet.price"/></th>
                            <th><fmt:message key="userCabinet.action"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.userOrders}" var="order">
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
                                    <fmt:message key="userCabinet.delete"/>
                                </a>
                            </td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td><b><fmt:message key="userCabinet.total"/>:</b><br>${String.format("%.2f", requestScope.totalPrice)}</td>
                        </tr>
                        </tbody>
                    </table>
                </c:if>
                <c:if test="${sessionScope.user.role == 'manager' || sessionScope.user.role == 'admin'}">

                    <h2><fmt:message key="userCabinet.usersOrder"/>:</h2>
                    <table data-toggle="table" class="wow fadeInUp">
                        <caption>AllOrders</caption>
                        <thead>
                        <tr>
                            <th><fmt:message key="userCabinet.code"/></th>
                            <th><fmt:message key="userCabinet.offer"/></th>
                            <th><fmt:message key="userCabinet.user"/></th>
                            <th><fmt:message key="userCabinet.status"/></th>
                            <th><fmt:message key="userCabinet.price"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.orders}" var="order">
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
                                        <c:forEach items="${requestScope.orderStatuses}" var="status">
                                            <c:choose>
                                                <c:when test="${order.orderStatus eq status}">
                                                    <option value="${status}" selected>${status}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${status}">${status}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
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
<%--                    <div class="pagination wow fadeInUp">--%>
<%--                        <c:forEach begin="1" end="${requestScope.numberOfPagesInOrders}" var="i">--%>
<%--                            <c:choose>--%>
<%--                                <c:when test="${requestScope.currentOrderPage eq i}">--%>
<%--                                    <span class="page-numbers current">${i}</span>--%>
<%--                                </c:when>--%>
<%--                                <c:otherwise>--%>
<%--                                    <a href="orderPagination?orderListPage=${i}" class="page-numbers">${i}</a>--%>
<%--                                </c:otherwise>--%>
<%--                            </c:choose>--%>
<%--                        </c:forEach>--%>
<%--                    </div>--%>
                    <my:pagination numberOfPages="${requestScope.numberOfPagesInOrders}"
                                   currentPage="${requestScope.currentOrderPage}"
                                   redirectTo="orderPagination"/>
                </c:if>
                <c:if test="${sessionScope.user.role == 'admin'}">
                    <br>
                    <hr>
                    <br>
                    <h2><fmt:message key="userCabinet.userList"/>:</h2>
                    <table data-toggle="table" class="wow fadeInUp">
                        <caption>UserList</caption>
                        <thead>
                        <tr>
                            <th><fmt:message key="userCabinet.email"/></th>
                            <th><fmt:message key="userCabinet.role"/></th>
                            <th><fmt:message key="userCabinet.firstName"/></th>
                            <th><fmt:message key="userCabinet.lastName"/></th>
                            <th><fmt:message key="userCabinet.phone"/></th>
                            <th><fmt:message key="userCabinet.blocked"/></th>
                            <th><fmt:message key="userCabinet.action"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.allUsers}" var="user">
                            <tr>
                                <td>${user.email}</td>
                                <td>
                                    <form accept-charset="UTF-8" role="form" action="updateUserRole" method="get">
                                        <select name="userRole" id="userRole">
                                            <c:forEach items="${requestScope.userRoles}" var="role">
                                                <c:choose>
                                                    <c:when test="${user.role eq role}">
                                                        <option value="${role}" selected>${role}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${role}">${role}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                        <input value="${user.email}" name="email" style="display:none">
                                        <button type="submit" class="check-mark">
                                            <i class="fa fa-check"></i>
                                        </button>
                                    </form>
                                </td>
                                <td>${user.firstName}</td>
                                <td>${user.lastName}</td>
                                <td>${user.phone}</td>
                                <td>${user.blocked}</td>
                                <td>
                                    <a href="blockUser?email=${user.email}">
                                        <c:if test="${user.isBlocked() == false}"><fmt:message key="userCabinet.block"/></c:if>
                                        <c:if test="${user.isBlocked() == true}"><fmt:message key="userCabinet.unblock"/></c:if>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div class="pagination wow fadeInUp">
                        <c:forEach begin="1" end="${requestScope.numberOfPagesInUserList}" var="i">
                            <c:choose>
                                <c:when test="${requestScope.currentPage eq i}">
                                    <span class="page-numbers current">${i}</span>
                                </c:when>
                                <c:otherwise>
                                    <a href="userPagination?userListPage=${i}" class="page-numbers">${i}</a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>
                </c:if>


                <hr>

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