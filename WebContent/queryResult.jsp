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
	<%if(request.getAttribute("did you mean") != null) {%>
	<p><a href="query.do?query=<% out.print(request.getAttribute("did you mean"));%>">Forse cercavi: <% out.print(request.getAttribute("did you mean"));%></a></p>
	<%}%>
	<p>Results:</p>
	<%
	DocumentResult[] documents = (DocumentResult[]) request.getAttribute("results");
		for (DocumentResult doc : documents){
			%><a href="<% out.print("file://" + doc.getPath()); %>" target="_blank">
				 Title: <% out.print(doc.getTitle()); %>
			</a>
			<p> Score: <% out.print(doc.getScore()); %></p>
			<p> Path: <% out.print(doc.getShortPath()); %></p>
			<p> Contenuto: <% 
			out.print(doc.getNear()); 
			%></p>
			
		<%}%>
		
		
	<p>Correlati:</p>
	<%
	String[] correlati = (String[]) request.getAttribute("keywords");
		for (String doc : correlati){
			%>
			<p> Keyword: <% 
			out.print(doc); 
			%></p>
			
		<%}%>
		
		
</body>
</html>