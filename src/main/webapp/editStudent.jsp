<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<html>
<head>
	<title>Edit student</title>
</head>
<body>
	<jsp:include page="/index.jsp"/>
	<%@ page import="com.course.task.logic.StudentDTO" %>
	<B>Currect student info:</B>
	<table border=5>
	<% if(request.getAttribute("student") == null) {
		System.out.println("Request in editStudent.jsp is null!"); 
	}
	StudentDTO student = (StudentDTO) request.getAttribute("student");%>
	<tr><td><%=student.getId()%></td> 
	<td><%=student.getFirstName()%></td> 
	<td><%=student.getLastName()%></td></tr>
	</table> 
	<form action = "/studapp/servlet"> 
	<input type = "hidden" name = "studentId" value = "<%=student.getId()%>"> 
	<h3>Enter new first name: <input type = "text" name = "firstName" pattern="[a-zA-z0-9]+$" value = <%=student.getFirstName()%>></h3> 
	<h3>Enter new last name: <input type = "text" name = "lastName" pattern="[a-zA-z0-9]+$" value = <%=student.getLastName()%>></h3>
	<button id = "show">Save Changes</button> 
	<input type = "hidden" name = "command" value = "saveChanges"> 
	<input type = "hidden" value = "Home page"/></h3> 
	</form>
	<% if(request.getAttribute("incorrectEnter") != null) {%>
		<p><font size="3" color="#FF6347" face="Arial">First name and last name must contain at least 1 letter</font></p>
	<%}%>
</body>
</html>