<!DOCTYPE>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Pacman - Search</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
</head>
<body>
<div class="container">
	<div class="row">
		<div class="col-lg-6 col-lg-offset-3">
			<form class="form-group" action="query.do" method="POST">
				<div class="input-group">
	     			 <input type="text" class="form-control" name="query" required>
				      <span class="input-group-btn">
				        <button class="btn btn-default btn-primary" type="submit" value="Query" name="query" >Go!</button>
				      </span>
	    		</div>
			</form>
		</div>	
	</div>
</div>	
	
</body>
</html>