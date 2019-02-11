<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<html>
<head>
	<title>All Students</title>
	<style>
		a { 
			text-decoration: none;
		} 
	</style>
</head>
<body link="#000000" vlink="#000000">
	<jsp:include page="/index.jsp"/>
	<%@ page import="java.util.*" %>
	<%@ page import="com.course.task.logic.*" %>
	<h1>Students list:</h1>
	<table border="5" cellpadding="4" cellspacing="0">
	<% if(request.getAttribute("StudentsList") == null) {
		System.out.println("Request in showAllStudents.jsp is null!");
	}%>
	<tr><td><b>Id</b></td>
	<td><b>First name</b></td>
	<td><b>Last name</b></td>
	<b><th colspan="3">Students functions</th></b></tr>
	<%List<StudentDTO> list = (List<StudentDTO>) request.getAttribute("StudentsList");
	for (StudentDTO s : list) {%>
		<tr><td><a href="servlet?studentId=<%= s.getId() %>&command=fullStudentInfo"><%= s.getId() %></a></td></td> 
		<td><a href="servlet?studentId=<%= s.getId() %>&command=fullStudentInfo"><%= s.getFirstName() %></a></td> 
		<td><a href="servlet?studentId=<%= s.getId() %>&command=fullStudentInfo"><%= s.getLastName() %></a></td>
		<form action = "/studapp/servlet">
		<input type = "hidden" name = "studentId" value = "<%= s.getId() %>">
		<input type = "hidden" name = "command" value = "editStudent"/>
		<td><input type = "submit" value = "Edit student"/></td>
		</form>
		<form action = "/studapp/servlet">
		<input type = "hidden" name = "studentId" value = "<%= s.getId() %>">
		<input type = "hidden" name = "command" value = "deleteStudent"/>
		<td><input type = "submit" value = "Delete student"/></td>
		</form>
		<form action = "/studapp/servlet">
		<input type = "hidden" name = "studentId" value = "<%= s.getId() %>">
		<input type = "hidden" name = "command" value = "selectSubjectToAssign"/>
		<td><input type = "submit" value = "Assign subject for this student"/></td></tr>
	</form>
	<%}%>
	</table>
</body>
</html>