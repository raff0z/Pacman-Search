<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="it.uniroma3.giw.model.DocumentResult"%>
<%@page import="org.apache.lucene.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Query Results</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/docs.css">
</head>
<body >
	<div class="container">
	
	<form class="form-inline" action="query.do" method="POST">
		
		<div class="form-group">
				<input class="form-control" type="text" placeholder="Inserisci la query" name="query" />
				<input class="btn btn-primary btn-xs" type="submit" value="Query" name="query" />
		</div>
		
	</form>
	
	
		<%if(request.getAttribute("did you mean") != null) {%>
		<p><a href="query.do?query=<% out.print(request.getAttribute("did you mean"));%>">Forse cercavi: <% out.print(request.getAttribute("did you mean"));%></a></p>
		<%}%>
		<p>Results:
		<%
		DocumentResult[] documents = (DocumentResult[]) request.getAttribute("results");
			if(documents.length == 0){
				out.print('0');
			}
			%>
			</p>
			<div>
				<ul class="list-unstyled">
					<%
					for (DocumentResult doc : documents){
						%>
						<li>
						<div class="bs-example">
							<h4>
							<a href="<% out.print("file://" + doc.getPath()); %>" target="_blank">
								 <% out.print(doc.getTitle()); %>
							</a>
							</h4>
<%-- 							<p> Score: <% out.print(doc.getScore()); %></p> --%>
							<div>
								<h6>
									<font color="green">
										<b>
											<% out.print(doc.getPath()); %>
										</b>
									</font>
								</h6>
							</div>
						</div>
						<div class="highlight">
							<p>
							<% 
						for(String near : doc.getNear())
						out.print(near + "..."); 
						%>
						</div>
						</p>
						<br></br>
						</li>
						
					<%}%>
				</ul>
			</div>
			
			
		<%
		String[] correlati = (String[]) request.getAttribute("keywords");
		if(correlati != null){%>
			<p>Correlati:</p>
			<%
			for (String doc : correlati){
				%>
				<p> Keyword: <% 
				out.print(doc); 
				%></p>
				
			<%}
			}%>
			
	</div>
</body>
</html>