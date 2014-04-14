<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="it.uniroma3.giw.model.DocumentResult"%>
<%@page import="org.apache.lucene.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Query Results</title>
</head>
<body>
	<p>Forse cercavi: <% request.getAttribute("did you mean");%></p>
	<p>Results:</p>
	<%
	DocumentResult[] documents = (DocumentResult[]) request.getAttribute("results");
		for (DocumentResult doc : documents){
			%><a href="<% out.print(doc.getPath()); %>" >
				 Title: <% out.print(doc.getTitle()); %>
			</a>
			<p> Score: <% out.print(doc.getScore()); %></p>
			<p> Path: <% out.print(doc.getShortPath()); %></p>
			
		<%}%>
</body>
</html>