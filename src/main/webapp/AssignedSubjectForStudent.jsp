<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<html>
<head>
	<title>Assign subject for student</title>
</head>
<body>
	<jsp:include page="/index.jsp"/>
	<%@ page import="com.course.task.logic.StudentDTO" %>
	<%@ page import="com.course.task.logic.SubjectDTO" %>
	<% if(request.getAttribute("studentId") == null || request.getAttribute("subjectId") == null) { 
		System.out.println("Request in AssignedSubjectForStudent.jsp is null!");
	}%>
	<h2>Subject with id <%=request.getAttribute("subjectId")%> is assigned for student with id <%=request.getAttribute("studentId")%>!</h2>
	<a href = "servlet?command=showStudents">Home page</a>
</body>
</html>