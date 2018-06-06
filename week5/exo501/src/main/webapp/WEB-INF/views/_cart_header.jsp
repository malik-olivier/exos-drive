<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<pre>
Panier
    <c:choose>
        <c:when test="${cart.getArticles().size() == 0}">
            Aucun article
        </c:when>
        <c:otherwise>
            <c:forEach items="${cart.articles}" var="article">
                ${article.name}
                <fmt:formatNumber maxFractionDigits="2" type="CURRENCY" value="${article.price/100}"/>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</pre>
<a class="btn btn-primary" href="cart/1/validate.html">Commander</a>