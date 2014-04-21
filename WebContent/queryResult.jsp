<%@page import="org.apache.lucene.search.ScoreDoc"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="it.uniroma3.giw.model.DocumentResult"%>
<%@page import="org.apache.lucene.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Query Results</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
</head>
<body >
	<div class="container voffset2	">
		<div  class="col-md-10">
		
		<form action="query.do" method="POST">
			<div class="input-group">
     			 <input type="text" class="form-control" value="<% out.print(request.getAttribute("query")); %>" name="query" required >
			      <span class="input-group-btn">
			        <button class="btn btn-default btn-primary" type="submit" value="Query" name="query" ><i class="glyphicon glyphicon-search glyphicon-search-result"></i></button>
			      </span>
    		</div>
		</form>
		
		
		
			<%if(request.getAttribute("did you mean") != null) {%>
			<p class="voffset2"><a href="query.do?query=<% out.print(request.getAttribute("did you mean"));%>"><strong>Forse cercavi: <% out.print(request.getAttribute("did you mean"));%></strong></a></p>
			<%}%>
			
			<%
			DocumentResult[] documents = (DocumentResult[]) request.getAttribute("results");
				if(documents.length == 0){
					%> <p>0 Results found!!!</p> <br></br> <%
				}
				else{
				    %>
				    <br></br>
				    <%
				}
				%>
				<div>
					<ul class="list-unstyled">
						<%
						for (DocumentResult doc : documents){
							%>
							<li>
							<div class="bs-example">
								<h4>
								<a href="<% out.print("file:///" + doc.getPath()); %>" target="_blank">
									 <% out.print(doc.getTitle()); %>
								</a>
								</h4>
								<p> Score: <% out.print(doc.getScore()); %></p>
								<div>
									<h6>
										<font color="green">
											<b>
												<% out.print(doc.getPath()); %>
											</b>
										</font>
									</h6>
								</div>
								<div>
									<a href="query.do?query=<% out.print(doc.getMoreLikeThis()); %>" >
									 Similar
									</a>
								</div>
							</div>
							<div class="highlight">
								<p>
								<% 
							for(String near : doc.getNear())
							out.print(near + "..."); 
							%>
							</div>
							</li>
							
						<%}%>
					</ul>
				</div>
				
			<div class="text-center">
				<ul class="pagination">
		
					<%
					Integer pages = (Integer) request.getAttribute("totalPages");
					String startString = request.getParameter("start");
					
					Integer start;
					if( startString == null){
					    start = 1;
					}else{
					   start = Integer.valueOf(request.getParameter("start"));
					}
					
					Integer end = start + 5;
					
					if(end > pages){
					    end = pages;
					}
					
					Integer realStart = 1;
					
					if(start > 5){
						realStart = start - 6;
					}
					
					for(int i=realStart ; i<= end; i++ ){
					%>
						<%if(i != start){ %>
							<li><a href="query.do?query=<% out.print(request.getAttribute("query"));%>&start=<% out.print(i);%>">
							<%
								out.print(i);
							%></a></li>
							
							<%}
						else{ %>
							<li class="active"><a>
							<%
								out.print(i);
							%></a></li>
							<%} %>
					<%
					}
					%>
				</ul>
			</div>
			
		</div>	
		
		<div class="col-md-offset-10">
			<%
			String[] correlati = (String[]) request.getAttribute("keywords");
			if(correlati != null){%>
			<div class="highlight-inverted">
				<p class="text-center"><strong>Keywords</strong></p>
			</div>
			<div class="bs-example">
				<%
				for (String keyword : correlati){
					%>
					<p><a href="query.do?query=<% out.print(keyword);%>"><% out.print(keyword);%></a></p>
					
				<%}
				}%>
			</div>
		</div>
	</div>
</body>
</html>