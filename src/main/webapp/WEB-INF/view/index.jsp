<%@ include file="./common/taglib.jsp" %>
<html lang="vi">
<head>
	<title><tiles:getAsString name="title"/></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous"/>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
<%--	<link rel="stylesheet" href="<c:url value="/css/sequence-looptheme.css"/>" type="text/css" media="all"/>--%>
	<link rel="stylesheet" href="<c:url value="/css/style.css"/>">
	<link rel="stylesheet" href="<c:url value="/css/customstyle.css"/>">
	<link href='http://fonts.googleapis.com/css?family=Roboto:400,300,300italic,400italic,500,700,500italic,100italic,100' rel='stylesheet' type='text/css'>
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>

<body>
	<div class="wrapper">
		<tiles:insertAttribute name="header"/>
		<div class="clearfix"></div>
		<tiles:insertAttribute name="body"/>
		<div class="clearfix"></div>
		<tiles:insertAttribute name="footer"/>
		<div id="preloader">
			<div id="loader"></div>
		</div>
	</div>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<%--	<script src="<c:url value="/js/jquery.sequence-min.js"/>"></script>--%>
<%--	<script src="<c:url value="/js/jquery.easing.1.3.js"/>"></script>--%>
<%--	<script src="<c:url value="/js/jquery.carouFredSel-6.2.1-packed.js"/>"></script>--%>
<%--	<script src="<c:url value="/js/script.min.js"/>"></script>--%>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootbox.js/5.5.2/bootbox.js" integrity="sha512-K3MtzSFJk6kgiFxCXXQKH6BbyBrTkTDf7E6kFh3xBZ2QNMtb6cU/RstENgQkdSLkAZeH/zAtzkxJOTTd8BqpHQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
	<script src="<c:url value="/js/global-eshopping-idb.js"/>"></script>
	<script src="<c:url value="/js/global-eshopping.js"/>"></script>

</body>
</html>
