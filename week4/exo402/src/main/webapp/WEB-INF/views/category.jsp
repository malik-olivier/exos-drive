<%@ page import="fr.eservices.drive.model.Article" %>
<%@ page import="java.util.List" %>
<%@include file="_header.jsp" %>
<%-- import required classes --%>

<ul class="articles">
<%-- Iterate through articles ... --%>
	<% for (Article article: (List<Article>)request.getAttribute("articles")) {%>
		<li>
			<a href="#">
			<span class="price">
				<%
					java.text.DecimalFormat df = new java.text.DecimalFormat("0.00##");
					String priceInEuro = df.format(Double.parseDouble(article.getPrice())/100);
				%>
				<%=priceInEuro%>
				<%-- show price as X,XX --%> &euro;</span>
				<%--
					show product image, you can use 'https://static1.chronodrive.com'
					as base URL and img path to complete the image URL
				--%>
				<img src="https://static1.chronodrive.com/<%=article.getImg()%>"/><br/>
				<%-- show product name --%> <%=article.getName()%><br/>
			</a>
		</li>
	<%}%>
</ul>
<%@include file="_footer.jsp" %>