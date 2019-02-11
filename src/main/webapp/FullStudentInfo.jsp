<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<html>
<head>
	<title>Full student information</title>
</head>
<body>
	<jsp:include page="/index.jsp"/>
	<%@ page import="java.util.List" %>
	<%@ page import="com.course.task.logic.StudentDTO" %>
	<%@ page import="com.course.task.logic.SubjectDTO" %>
	<%@ page import="com.course.task.logic.MarkDTO" %>
	<b>Main student information:</b>
	<table border="5" cellpadding="4" cellspacing="0">
	<% if(request.getAttribute("student") == null || request.getAttribute("fullStudentInfoList") == null ||
			request.getAttribute("MarkList") == null) {
		System.out.println("Request in FullStudentInfo.jsp is null!");
	}%>
	<%StudentDTO student = (StudentDTO) request.getAttribute("student");%>
	<tr><td><b>Id</b></td>
	<td><b>First name</b></td>
	<td><b>Last name</b></td></tr>
	<tr><td><%= student.getId() %></a></td></td> 
	<td><%= student.getFirstName() %></a></td> 
	<td><%= student.getLastName() %></a></td></tr>
	</table>
	<br><b>Learning subjects:</b></br>
	<table border="5" cellpadding="4" cellspacing="0">
	<%List<SubjectDTO> fullStudentInfoList = (List<SubjectDTO>) request.getAttribute("fullStudentInfoList");%>
	<tr><td><b>Subject id</b></td>
	<td><b>Subject name</b></td></tr>
	<%for (SubjectDTO subject : fullStudentInfoList) {%>
		<tr><td><%= subject.getId() %></td>
		<td><%= subject.getName() %></td></tr>
	<%}%>
	</table>
	<br><b>Marks:</b></br>
	<table border="5" cellpadding="4" cellspacing="0">
	<%List<MarkDTO> marklist = (List<MarkDTO>) request.getAttribute("MarkList");%>
	<tr><td><b>Mark id</b></td>
	<td><b>Subject name</b></td>
	<td><b>Mark</b></td>
	<td><b>Date of receiving</b></td></tr>
	<%for (MarkDTO mark : marklist) {%>
		<tr><td><%= mark.getId() %></td>
		<td><%= mark.getName() %></td>
		<td><%= mark.getMark() %></td>
		<td><%= mark.getDate() %></td></tr>
	<%}%>
	</table>
	<br><a href="servlet?command=showStudents" style="color: #808080" id = "home">Home page</a></br>
</body>
</html>