<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<html>
<head>
	<title>Delete subject</title>
</head>
<body>
	<jsp:include page="/index.jsp"/>
	<%@ page import="com.course.task.dto.SubjectDTO" %>
	<% if(request.getAttribute("subjectId") == null) {
		System.out.println("Request in deleteSubject.jsp is null!"); 
	}%>
		<h2>Subject with id <%=request.getAttribute("subjectId")%> is deleted!</h2>
		<a href = "servlet?command=showStudents">Home page</a>
</body>
</html>