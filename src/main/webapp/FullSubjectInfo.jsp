<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<html>
<head>
	<title>Full subject information</title>
</head>
<body
	<jsp:include page="/index.jsp"/>
	<%@ page import="java.util.List" %>
	<%@ page import="com.course.task.logic.StudentDTO" %>
	<%@ page import="com.course.task.logic.SubjectDTO" %>
	<%@ page import="com.course.task.logic.MarkDTO" %>
	<b>Main subject information:</b>
	<table border="5" cellpadding="4" cellspacing="0">
	<% if(request.getAttribute("subject") == null || request.getAttribute("fullSubjectInfoList") == null ||
			request.getAttribute("MarkList") == null) {
		System.out.println("Request in FullSubjectInfo.jsp is null!");
	}%>
	<%SubjectDTO subject = (SubjectDTO) request.getAttribute("subject");%>
	<tr><td><b>Id</b></td>
	<td><b>Name</b></td></tr>
	<tr><td><%= subject.getId() %></a></td></td> 
	<td><%= subject.getName() %></a></td></tr>
	</table>
	<br><b>Learning subjects:</b></br>
	<table border="5" cellpadding="4" cellspacing="0">
	<%List<StudentDTO> fullSubjectInfoList = (List<StudentDTO>) request.getAttribute("fullSubjectInfoList");%>
	<tr><td><b>Student id</b></td>
	<td><b>Student last name</b></td>
	<td><b>Student first name</b></td></tr>
	<%for (StudentDTO student : fullSubjectInfoList) {%>
		<tr><td><%= student.getId() %></td>
		<td><%= student.getFirstName() %></td>
		<td><%= student.getLastName() %></td></tr>
	<%}%>
	</table>
	<br><b>Marks:</b></br>
	<table border="5" cellpadding="4" cellspacing="0">
	<%List<MarkDTO> marklist = (List<MarkDTO>) request.getAttribute("MarkList");%>
	<tr><td><b>Mark id</b></td>
	<td><b>Student first name</b></td>
	<td><b>Student last name</b></td>
	<td><b>Mark</b></td>
	<td><b>Date of receiving</b></td></tr>
	<%for (MarkDTO mark : marklist) {%>
		<tr><td><%= mark.getId() %></td>
		<td><%= mark.getFirstName() %></td>
		<td><%= mark.getLastName() %></td>
		<td><%= mark.getMark() %></td>
		<td><%= mark.getDate() %></td></tr>
	<%}%>
	</table>
	<br><a href="servlet?command=showStudents" style="color: #808080" id = "home">Home page</a></br>
</body>
</html>