<%@page import="it.uniroma3.giw.model.AutoComplete"%>
<%@page import="org.apache.lucene.search.ScoreDoc"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="it.uniroma3.giw.model.DocumentResult"%>
<%@page import="org.apache.lucene.*" %>
<% AutoComplete autoComplete = new AutoComplete(); %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Query Results</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script src="js/jquery.autocomplete.js"></script>
<script>
	jQuery(function() {
		$("#query").autocomplete("list.jsp");
	});
</script>
</head>
<body >
	<div class="container voffset2	">
		<div  class="col-md-10">
		
		<form action="query.do" method="POST">
			<div class="input-group">
     			 <input type="text" id="query" class="form-control" value="<% out.print(request.getAttribute("query")); %>" name="query" required >
			      <span class="input-group-btn">
			        <button class="btn btn-default btn-primary" type="submit" value="Query" name="query" ><i class="glyphicon glyphicon-search glyphicon-search-result"></i></button>
			        <button class="btn btn-default btn-primary" type="submit" value="QueryImage" name="queryimage"  onclick="form.action='queryimage.do';"  ><i class="glyphicon glyphicon-picture glyphicon-picture-result"></i></button>
			      </span>
    		</div>
		</form>
		
			<%if(request.getAttribute("did you mean") != null) {%>
			<p class="voffset2"><a href="query.do?query=<% out.print(request.getAttribute("did you mean"));%>"><strong>Forse cercavi: <% out.print(request.getAttribute("did you mean"));%></strong></a></p>
			<%}%>
			
			<%
			DocumentResult[] documents = (DocumentResult[]) request.getAttribute("results");
				if(documents.length == 0){
					%> <p><strong>0 Results found!!!</strong></p> <br></br> <%
				}
				else{
				    %>
				    <p><strong><% out.print(request.getAttribute("total docs")); %> results found</strong></p>
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
								<img src="${pageContext.request.contextPath}/documents/images/<% out.print(doc.getFileName()); %>" height="250" width="250" />
								<div>
									<h6>
										<font color="green">
											<b>
												<% out.print(doc.getShortPath()); %>
											</b>
										</font>
									</h6>
								</div>
								
							</div>
							</li>
							
						<%}%>
					</ul>
				</div>
				
			<div class="text-center">
				<ul class="pagination">
		
					<%
					String startString = request.getParameter("start");
					
					Integer start;
					if( startString == null){
					    start = 1;
					}else{
					   start = Integer.valueOf(request.getParameter("start"));
					}
					
					Integer realStart = (Integer) request.getAttribute("real start paging");					
					Integer end = (Integer) request.getAttribute("end paging");
					
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
		
		
	</div>
</body>
</html>