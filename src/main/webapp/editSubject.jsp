<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<html>
<head>
	<title>Edit subject</title>
</head>
<body>
	<jsp:include page="/index.jsp"/>
	<%@ page import="com.course.task.dto.SubjectDTO" %>
	<B>Currect subject info:</B>
	<table border=5>
	<% if( request.getAttribute("subject") == null) {
		System.out.println("Request in editSubject.jsp is null!"); 
	}
	SubjectDTO subject = (SubjectDTO) request.getAttribute("subject"); %>
	<tr><td><%=subject.getId()%></td> 
	<td><%=subject.getName()%></td></tr>
	</table> 
	<form action = "/studapp/servlet">
	<input type = "hidden" name = "subjectId" value = "<%=subject.getId()%>"> 
	<h3>Enter new subject name: <input type = "text" name = "subjectName" pattern="[a-zA-z0-9]+$" value = <%=subject.getName()%>></h3> 
	<button id = "show">Save Changes</button> 
	<input type = "hidden" name = "command" value = "saveChanges"> 
	<input type = "hidden" value = "Home page"/></h3> 
	</form>
	<% if(request.getAttribute("incorrectEnter") != null) {%>
		<p><font size="3" color="#FF6347" face="Arial">Subject name must contain at least 1 letter</font></p>
	<%}%>
</body>
</html>