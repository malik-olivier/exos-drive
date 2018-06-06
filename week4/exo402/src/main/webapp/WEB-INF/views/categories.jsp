<%@ page import="fr.eservices.drive.model.Category" %>
<%@ page import="java.util.List" %>
<%@include file="_header.jsp" %>
<%-- import required classes --%>

<ul class="categories">
<%-- iterate through categories --%>
	<%
		for(Category category: (List<Category>)request.getAttribute("categories")) {%>
			<li>
				<a href="category/<%=category.getId()%>.html">
						 <%
							 String s = String.format("%2d",Integer.parseInt(category.getId()));
							 String res = request.getContextPath()+"/img/cat";
							 if (s.trim().length() == 1) {
								 s = '0'+s.trim();
							 }
							 res += s + ".jpg";
						 %>
					<img src="<%=res%>">
					<p><%=category.getName()%></p>
				</a>
			</li>
	<%
		}
	%>
</ul>
<%@include file="_footer.jsp" %>