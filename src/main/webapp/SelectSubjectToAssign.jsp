<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<html>
<head>
	<title>Select subject to assign</title>
	<style>
		a { 
			text-decoration: none;
		} 
	</style>
</head>
<body link="#000000" vlink="#000000">
	<jsp:include page="/index.jsp"/>
	<%@ page import="java.util.List" %>
	<%@ page import="com.course.task.logic.StudentDTO" %>
	<%@ page import="com.course.task.logic.SubjectDTO" %>
	<table border="5" cellpadding="4" cellspacing="0">
	<% if(request.getAttribute("AssignSubjectForStudentList") == null) {
		System.out.println("Request in showAllSubjects.jsp is null!");
	}%>
	<tr><td><b>Id</b></td>
	<td><b>Name</b></td></tr>
	<%List<SubjectDTO> list = (List<SubjectDTO>) request.getAttribute("AssignSubjectForStudentList");
	long studentId = (long) request.getAttribute("studentId");
	for (SubjectDTO s : list) {%>
		<tr><td><a href="servlet?subjectId=<%= s.getId() %>&command=assignSubjectForStudent&studentId=<%=studentId%>"><%= s.getId() %></a></td></td> 
		<td><a href="servlet?subjectId=<%= s.getId() %>&command=assignSubjectForStudent&studentId=<%=studentId%>"><%= s.getName() %></a></td></tr>
	<%}%>
	</table>
	<br><a href="servlet?command=showStudents" style="color: #808080" id = "home">Home page</a></br>
</body>
</html>