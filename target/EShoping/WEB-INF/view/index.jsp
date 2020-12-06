<%@ include file="./common/taglib.jsp" %>
<html>
<head>
	<title><tiles:getAsString name="title"/></title>
	<link rel="stylesheet" href='<c:url value="/css/bootstrap.min.css"/>'>
</head>
<body>
	<div class="container">
		<tiles:insertAttribute name="header"/>
		<tiles:insertAttribute name="menu"/>
		<tiles:insertAttribute name="body"/>
		<tiles:insertAttribute name="footer"/>
	</div>
	
</body>
</html>
