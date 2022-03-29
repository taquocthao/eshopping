<%--
  Created by IntelliJ IDEA.
  User: QTH02
  Date: 9/19/2021
  Time: 11:59 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:url var="formURL" value="/signup.html"/>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title><fmt:message key="label.sign-up"/></title>

    <!-- Font Icon -->
    <link href="<c:url value="/fonts/material-icon/css/material-design-iconic-font.min.css"/>">
    <!-- Signup css -->
    <link rel="stylesheet" href="<c:url value="/css/signup/style.css"/>">
    <!-- Main css -->

</head>
<body style="background-image: url('/EShoping/img/login/bg-01.jpg');">

<div class="main">

    <section class="signup">
        <div class="container">
            <form:form modelAttribute="item" action="${formURL}" method="post" id="signUpForm" cssClass="signup-form">
                <c:if test="${not empty messageResponse}">
                    <div class="alert alert-${alert} alert-dismissible fade show" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <strong>${messageResponse}</strong>
                    </div>
                </c:if>

                <input type="hidden" name="crudaction" id="crudaction">
                <div class="signup-content">
                    <h2 class="form-title"><fmt:message key="label.create.account"/></h2>
                    <div class="form-group">
                        <input type="text" class="form-input" name="pojo.fullName" id="name" value="${item.pojo.fullName}" placeholder="<fmt:message key="label.fullname.placeholder"/>"/>
                        <form:errors path="pojo.fullName" cssClass="error"/>
                    </div>
                    <div class="form-group">
                        <input type="number" class="form-input" name="pojo.phoneNumber" id="phoneNumber" value="${item.pojo.phoneNumber}" placeholder="<fmt:message key="label.phone"/>"/>
                        <form:errors path="pojo.phoneNumber" cssClass="error"/>
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-input" name="pojo.username" id="username" value="${item.pojo.username}" placeholder="<fmt:message key="label.username"/>"/>
                        <form:errors path="pojo.username" cssClass="error"/>
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-input" name="pojo.password" id="password" value="${item.pojo.password}" placeholder="<fmt:message key="label.password" />"/>
                        <span toggle="#password" class="zmdi zmdi-eye field-icon toggle-password"></span>
                        <form:errors path="pojo.password" cssClass="error"/>
                    </div>
                    <div class="form-group">
                        <input type="password" class="form-input" name="re_password" id="re_password" value="${item.re_password}" placeholder="<fmt:message key="label.re-password.placeholder"/>"/>
                        <form:errors path="re_password" cssClass="error"/>
                    </div>
                    <div class="form-group">
                        <input type="checkbox" id="agreeTerm" class="agree-term" style="display: inline-block"/>
                        <span>I agree all statements in <a href="#" class="term-service">Terms of service</a></span>
                    </div>
                    <div class="form-group">
                        <input type="submit" id="submitForm" class="form-submit" value="<fmt:message key="label.sign-up"/>" disabled>
                    </div>

                    <p class="loginhere">
                        <fmt:message key="label.already.account"/> <a href="<c:url value="/login.html"/>" class="loginhere-link"><fmt:message key="label.login.here"/></a>
                    </p>
                </div>
            </form:form>
        </div>
    </section>

</div>

<!-- JS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js" integrity="sha512-3P8rXCuGJdNZOnUx/03c1jOTnMn3rP63nBip5gOP2qmUh5YAdVAvFZ1E+QLZZbC1rtMrQb+mah3AfYW11RUrWA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script>
    $(document).ready(function () {
        $(".toggle-password").click(function() {
            $(this).toggleClass("zmdi-eye zmdi-eye-off");
            var input = $($(this).attr("toggle"));
            if (input.attr("type") == "password") {
                input.attr("type", "text");
            } else {
                input.attr("type", "password");
            }
        });

        bindEvents();
    });

    function bindEvents() {
        eventPasswordInput();
        eventRePasswordInput();
        eventSubmit();
        eventAgree();
    }

    function eventSubmit() {
        $("#submitForm").on("click", function (e) {
            e.preventDefault();
            $("#crudaction").val("insert-update");
            $("#signUpForm").submit();
        });
    }

    function eventPasswordInput() {
        $("#password").on("input", function () {
            $("input[name='re_password']").text("");
        });
    }

    function eventRePasswordInput() {
        $("#re_password").on("input", function () {
            $("input[name='re_password']").text("");
        });
    }

    function eventAgree() {
        $("#agreeTerm").on("click", function () {
            if($(this).is(':checked')) {
                $("#submitForm").prop("disabled", false);
            } else {
                $("#submitForm").prop("disabled", true);
            }
        });
    }

</script>
</body>
</html>