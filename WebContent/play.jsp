<!DOCTYPE>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <title>HTML5 Pacman</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pacman.css">
    
</head>

<body>
  
  <div id="pacman"></div>
  <script src="${pageContext.request.contextPath}/js/pacman.js"></script>
  <script src="${pageContext.request.contextPath}/js/modernizr-1.5.min.js"></script>
<div class="container"> 
  <script>

    var el = document.getElementById("pacman");

    if (Modernizr.canvas && Modernizr.localstorage && 
        Modernizr.audio && (Modernizr.audio.ogg || Modernizr.audio.mp3)) {
      window.setTimeout(function () { PACMAN.init(el, "./"); }, 0);
    } else { 
      el.innerHTML = "Sorry, needs a decent browser<br /><small>" + 
        "(firefox 3.6+, Chrome 4+, Opera 10+ and Safari 4+)</small>";
    }
  </script>
  
  <div class="row">
			<div class="col-lg-6 col-lg-offset-3">
				<form class="form-group" action="query.do" method="POST">
					<div class="input-group">
		     			 <input type="text" class="form-control" name="query" value="pacman" required>
					      <span class="input-group-btn">
					        <button class="btn btn-default btn-primary" type="submit" value="Query" name="query" ><i class="glyphicon glyphicon-search glyphicon-search-index"></i></button>
					        <button class="btn btn-default btn-primary" type="submit" value="QueryImage" name="queryimage"  onclick="form.action='queryimage.do';"  ><i class="glyphicon glyphicon-picture glyphicon-picture-index"></i></button>
					      </span>
		    		</div>
				</form>
			</div>	
		</div>
</div>
</body>
</html>