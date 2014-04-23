<!DOCTYPE>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Pacman - Search</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script src="js/jquery.autocomplete.js"></script>
<script>
	jQuery(function() {
		$("#query").autocomplete("list.jsp");
	});
</script>
</head>
<body>
<div class="container">
	<div class="homepac">
		<div class="row">
			<div class="logo">
				<a href="${pageContext.request.contextPath}/play.jsp">
	    			<img src="${pageContext.request.contextPath}/images/logo.png"/>
				</a>
			</div>	
		</div>
		<div class="row">
			<div class="col-lg-6 col-lg-offset-3">
				<form class="form-group" action="query.do" method="POST">
					<div class="input-group">
		     			 <input type="text" id="query" class="form-control" name="query" required>
					      <span class="input-group-btn">
					        <button class="btn btn-default btn-primary" type="submit" value="Query" name="query" ><i class="glyphicon glyphicon-search glyphicon-search-index"></i></button>
					        <button class="btn btn-default btn-primary" type="submit" value="QueryImage" name="queryimage" onclick="form.action='queryimage.do';" ><i class="glyphicon glyphicon-picture glyphicon-picture-index"></i></button>
					      </span>
		    		</div>
				</form>
			</div>	
		</div>
	</div>
</div>	
	
</body>
</html>