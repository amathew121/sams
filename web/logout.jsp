<%-- 
    Document   : logout
    Created on : 21 Jun, 2013, 11:09:51 PM
    Author     : Ashish
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
  if (request.getParameter("logoff") != null) {
    session.invalidate();
    response.sendRedirect("/SamJPA/");
    return;
  }
%>
<html>
<head>
<title>Student Attendance Management System</title>
</head>
<body bgcolor="white">

You are logged in as remote user
<b><%= request.getRemoteUser() %></b>
in session <b><%= session.getId() %></b><br><br>

<%
  if (request.getUserPrincipal() != null) {
%>
    Your user principal name is
    <b><%= request.getUserPrincipal().getName() %></b>
    <br><br>
<%
  } else {
%>
    No user principal could be identified.<br><br>
<%
  }
%>

<%
  String role = request.getParameter("role");
  if (role == null)
    role = "";
  if (role.length() > 0) {
    if (request.isUserInRole(role)) {
%>
      You have been granted role
      <b><%= role %></b><br><br>
<%
    } else {
%>
      You have <i>not</i> been granted role
      <b><%= role %></b><br><br>
<%
    }
  }
%>

To check whether your username has been granted a particular role,
enter it here:
<form method="GET" action='<%= response.encodeURL("logout.jsp") %>'>
<input type="text" name="role" value="<%= role %>">
</form>
<br><br><br>

Log Out
<a href='<%= response.encodeURL("logout.jsp?logoff=true") %>'>here</a>.


</body>
</html>
