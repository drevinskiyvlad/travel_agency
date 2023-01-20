<%@attribute name="numberOfPages" type="java.lang.Integer" required="true" %>
<%@attribute name="currentPage" type="java.lang.Integer" required="true" %>
<%@attribute name="redirectTo" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pagination wow fadeInUp">
    <c:forEach begin="1" end="${numberOfPages}" var="i">
        <c:choose>
            <c:when test="${currentPage eq i}">
                <span class="page-numbers current">${i}</span>
            </c:when>
            <c:otherwise>
                <a href="${redirectTo}${i}" class="page-numbers">${i}</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</div>
