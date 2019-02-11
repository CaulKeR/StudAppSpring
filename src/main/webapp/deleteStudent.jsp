<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<html>
<head>
	<title>Delete student</title>
</head>
<body>
	<jsp:include page="/index.jsp"/>
	<%@ page import="com.course.task.logic.StudentDTO" %>
	<% if(request.getAttribute("studentId") == null) { 
		System.out.println("Request in deleteStudent.jsp is null!");
	}%>
	<h2>Student with id <%=request.getAttribute("studentId")%> is deleted!</h2>
	<a href = "servlet?command=showStudents">Home page</a>
</body>
</html>