<%@ include file="common/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- URL -->
<c:url var="formURL" value="/admin/login.html" />

<!DOCTYPE html>
<html >
<head>
	<title><fmt:message key="label.login"/></title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<style type="text/css">

	</style>
</head>
<body>

	<div id="content">
		<div class="container-fluid"><br />
			<br />
			<div class="row">
				<div class="col-sm-offset-4 col-sm-4">
					<div class="panel panel-default">
						<div class="panel-heading">
<%--							<h1 class="panel-title">--%>
<%--								<i class="fa fa-lock"></i>--%>
<%--							</h1>--%>
						</div>
						<div class="panel-body">
<%--							<c:if test="${messageResource}">--%>
<%--								<div class="alert alert-danger alert-dismissible"><i class="fa fa-exclamation-circle"></i>--%>
<%--									<button type="button" class="close" data-dismiss="alert">&times;</button>--%>
<%--								</div>--%>
<%--							</c:if>--%>

							<form action="/j_spring_security_check" method="post" enctype="multipart/form-data">
								<%--User name--%>
								<div class="form-group">
									<label for="input-username"><fmt:message key="label.username"/></label>
									<div class="input-group"><span class="input-group-addon"><i class="fa fa-user"></i></span>
										<input type="text" id="input-username" class="form-control" name="username" placeholder="<fmt:message key="label.username"/>" />
									</div>
								</div>
								<%--Password--%>
								<div class="form-group">
									<label for="input-password"><fmt:message key="label.password"/></label>
									<div class="input-group">
										<span class="input-group-addon"><i class="fa fa-lock"></i></span>
										<input type="password"id="input-password" class="form-control" name="password" value="" placeholder="<fmt:message key="label.password"/>" />
									</div>

									<span class="help-block"><a href="#"><fmt:message key="label.forgotten.password"/></a></span>
								</div>
								<div class="text-right">
									<button type="submit" class="btn btn-primary"><i class="fa fa-key"></i>&nbsp;<fmt:message key="label.login"/></button>
								</div>

							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>