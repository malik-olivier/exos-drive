<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="_header.jsp" %>

<h1>Liste des commandes</h1>
<c:choose>
    <c:when test="${orders.size()==0}">
        Aucune commande
    </c:when>
    <c:otherwise>
        <c:forEach items="${orders}" var="order">
            <div style="border-bottom: #204d74;border-bottom: solid;">
                <div style="margin: 1em auto 1em auto;width: 50%;">
                    <p>Created on : <fmt:formatDate  pattern = "d/M/yy" value = "${order.createdOn}" /></p>
                    <p>
                        Amount :
                        <fmt:formatNumber maxFractionDigits="2" type="CURRENCY" value="${order.amount/100}"/>
                    </p>
                    <p>Status : ${order.currentStatus}</p>
                </div>
            </div>
        </c:forEach>
    </c:otherwise>
</c:choose>


<%@include file="_footer.jsp" %>