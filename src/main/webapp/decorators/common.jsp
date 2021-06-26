<%@ include file="/WEB-INF/view/common/taglib.jsp" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<!doctype html>
<html lang="vi">
<head>
	<title><decorator:title/></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous"/>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
	<link rel="stylesheet" href="<c:url value="/css/style.css"/>">
	<link rel="stylesheet" href="<c:url value="/css/customstyle.css"/>">
	<link href='http://fonts.googleapis.com/css?family=Roboto:400,300,300italic,400italic,500,700,500italic,100italic,100' rel='stylesheet' type='text/css'>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/chart.js@2.9.4/dist/Chart.min.css">
	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/bs4/dt-1.10.25/fc-3.3.3/fh-3.1.9/r-2.2.9/sc-2.0.4/datatables.min.css"/>
	<!-- bootstrap-select -->
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">
	<%--rich text editor - tinyMCE--%>
	<script src="https://cdn.tiny.cloud/1/bcb0up0h9ehszn1e1zqnj5brm7kak6w0rzwll8e1khsltegs/tinymce/5/tinymce.min.js" referrerpolicy="origin"></script>
</head>

<body>
	<div class="wrapper">
		<jsp:include page="../WEB-INF/view/common/_header.jsp"/>
		<div class="clearfix"></div>
		<decorator:body />
		<div class="clearfix"></div>
		<jsp:include page="../WEB-INF/view/common/_footer.jsp"/>
		<div id="preloader">
			<div id="loader"></div>
		</div>
	</div>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootbox.js/5.5.2/bootbox.js" integrity="sha512-K3MtzSFJk6kgiFxCXXQKH6BbyBrTkTDf7E6kFh3xBZ2QNMtb6cU/RstENgQkdSLkAZeH/zAtzkxJOTTd8BqpHQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
	<script src="<c:url value="/js/global-eshopping-idb.js"/>"></script>
	<script src="<c:url value="/js/global-eshopping.js"/>"></script>
	<script src="<c:url value="/js/jquery.ui.autocomplete.scroll.min.js"/>"></script>
	<script src="https://cdn.jsdelivr.net/npm/chart.js@3.3.2/dist/chart.min.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/v/bs4/dt-1.10.25/fc-3.3.3/fh-3.1.9/r-2.2.9/sc-2.0.4/datatables.min.js"></script>
	<!-- bootstrap-select -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>

</body>
</html>
