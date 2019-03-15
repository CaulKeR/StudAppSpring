<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<html>
<head>
	<title>All subjects</title>
	<style>
		a { 
			text-decoration: none;
		} 
	</style>
</head>
<body link="#000000" vlink="#000000">
	<jsp:include page="/index.jsp"/>
	<%@ page import="java.util.List" %>
	<%@ page import="com.course.task.dto.SubjectDTO" %>
	<h1>Subjects list</h1>
	<table border="5" cellpadding="4" cellspacing="0">
	<% if(request.getAttribute("SubjectsList") == null) {
		System.out.println("Request in showAllSubjects.jsp is null!");
	}%>
	<tr><td><b>Id</b></td>
	<td><b>Name</b></td>
	<b><th colspan="2">Subjects functions</th></b></tr>
	<%List<SubjectDTO> list = (List<SubjectDTO>) request.getAttribute("SubjectsList");
	for (SubjectDTO s : list) {%>
	<tr><td><a href="servlet?subjectId=<%= s.getId() %>&command=fullSubjectInfo"><%= s.getId() %></a></td></td> 
		<td><a href="servlet?subjectId=<%= s.getId() %>&command=fullSubjectInfo"><%= s.getName() %></a></td> 
		<form action = "/studapp/servlet">
		<input type = "hidden" name = "subjectId" value = "<%= s.getId() %>">
		<input type = "hidden" name = "command" value = "editSubject"/>
		<td><input type = "submit" value = "Edit subject"/></td>
		</form>
		<form action = "/studapp/servlet">
		<input type = "hidden" name = "subjectId" value = "<%= s.getId() %>">
		<input type = "hidden" name = "command" value = "deleteSubject"/>
		<td><input type = "submit" value = "Delete subject"/></td></tr>
	</form>
	<%}%>
	</table>
	<br><a href="servlet?command=showStudents" style="color: #808080" id = "home">Home page</a></br>
</body>
</html>