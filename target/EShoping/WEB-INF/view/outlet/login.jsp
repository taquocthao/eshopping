<%@ include file="../common/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- URL -->
<c:url var="formURL" value="/admin/login.html" />

<!DOCTYPE html>
<html >
<head>
	<title><fmt:message key="label.login"/></title>
	<link rel="stylesheet" href='<c:url value="/css/bootstrap.min.css"/>'>
	<style type="text/css">
		body {
			background-image: url("../asset/images/wallpapers.jpg");
		}
	</style>
</head>
<body>
	<div class="container">
		<div class="wrapper-page">
			<div class="card">
				<div class="card-body">
					<div class="">
						<form:form action="${formURL}" method="POST" autocomplete="off">
                            <div class="row form-group">
                                <form:select path="userGroupName" cssClass="form-control">
                                    <form:option value=""><fmt:message key="label.user-group"/></form:option>
                                    <form:options items="${userGroups}" itemLabel="groupName" itemValue="userGroupId" />
                                </form:select>
                            </div>
                            <div class="row form-group">
                                <form:input path="username" cssClass="form-control" placeholder='<fmt:message key="label.username"/>'/>
                            </div>
							<div class="row form-group">
								<form:input path="password" cssClass="form-control" placeholder='<fmt:message key="label.password"/>'/>
							</div>
							<div class="row form-group text-center">
								<a class="btn btn-primary" type="button"><fmt:message key="label.login"/></a>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
<script src="/js/bootstrap.min.js"/>
</body>
</html>