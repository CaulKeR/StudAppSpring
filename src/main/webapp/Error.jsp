<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<html>
<head>
	<meta charset = "utf-8">
	<title>Oops...</title>
</head>
<body>
	<jsp:include page="/index.jsp"/>
	<% if(request.getAttribute("ErrorMessage") != null) {%>
	<p><font size="4" color="#FF6347" face="Arial"><%=request.getAttribute("ErrorMessage")%></font></p>
	<a href="servlet?command=showStudents" style="color: #808080" id = "home">Home page</a>
	<%}%>
</body>
</html>