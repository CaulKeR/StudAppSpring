<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<meta charset = "utf-8">
		<title>Second task</title>
		<style>
			.menu ul {
			  padding: 0;
			  background: #E4EFD1;
			}
			.menu li {
			  display: inline-block;
			}
			.menu a {
			  padding: 1em;
			  display: block;
			  color: #74924C;
			  border-right: 1px solid #BAD78B;
			  background: rgba(186, 215, 139, .3);
			}
			.menu a:hover {background: #BAD78B}
		</style>
	</head>
	<body>
		<nav class="menu">
		  <ul>
			<li><a href="/studapp/servlet?command=showStudents">Home</a></li>
			<li><a href="/studapp/servlet?command=showSubjects">Show all subjects</a></li>
		  </ul>
		</nav>
	</body>
</html>