<%--
  Created by IntelliJ IDEA.
  User: taquo
  Date: 06/12/2020
  Time: 6:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/taglib.jsp"%>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Login page</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
</head>
<body>

<div class="container">
    <div class="wrapper">
        <div class="form w-50">
            <form action="<c:url value="/perform_login"/>" method="post">
            <h2>Đăng nhập</h2>
            <div>
                <div class="form-group">
                    <input class="form-control" type="text" name="username" id="inputUsername" placeholder="Tên đăng nhập"/>
                </div>
                <div class="form-group">
                    <input class="form-control" type="password" name="password" id="inputPassword" placeholder="Mật khẩu"/>
                </div>
                <input type="hidden" name="remember-me" value="true">
                <button type="submit" class="btn btn-primary">Đăng nhập</button>
                <label><input type="checkbox"><a href="#">Quên mật khẩu?</a></label>
            </div>
            <span>HOẶC</span>
            <div>
                <div class="form-group">
                    <button>Facebook</button>
                </div>
                <div class="form-group">
                    <button>Google</button>
                </div>
                <div>
                    <p>Bạn mới biết đến Ú? <a href="#">Đăng ký</a></p>
                </div>
            </div>
            </form>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>
