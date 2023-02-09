<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<footer class="site-footer wow fadeInUp">
    <div class="footer-bottom">
        <div class="container">
            <div class="branding pull-left">
                <img src="${pageContext.request.contextPath}/images/logo-footer.png" alt="Company Name" class="logo">
                <h1 class="site-title"><fmt:message key="header.companyName"/></h1>
                <small class="site-description"><fmt:message key="footer.tagline"/></small>
            </div>

            <div class="contact-links pull-right">
                <i class="fa fa-map-marker"></i> <fmt:message key="footer.address"/><br>
                <i class="fa fa-phone"></i> +380 68 111 22 33<br>
                <i class="fa fa-envelope"></i> doe@companyname.com
            </div>
        </div>
    </div>
</footer>