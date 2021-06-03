<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="./taglib.jsp" %>
<!-- Footer -->
<div class="header">
	<div class="container">
		<div class="row">
			<%--logo--%>
			<div class="col-md-2 col-sm-2">
				<div class="logo">
					<a href="<c:url value="/home.html"/>">
						<img src="<c:url value="/img/logo.png"/>" alt="Ú clothing">
					</a>
				</div>
			</div>
			<%--right menu--%>
			<div class="col-md-10 col-sm-10">
				<div class="header_top">
					<div class="row mt-2">
						<!--search product-->
						<div class="col-md-7">
							<div class="input-group">
								<input class="form-control" placeholder="<fmt:message key="label.search"/>" type="search" name="searchProduct">
								<div class="input-group-append">
									<a href="javascript:void(0)" class="btn btn-search-home">
										<i class="fa fa-search" style="line-height: inherit"></i>
									</a>
								</div>
							</div>

						</div>

						<!--user information-->
						<div class="col-md-5">
							<ul class="usermenu">
								<security:authorize access="isAuthenticated()">
									<li><a href="#" class="log"><security:authentication property="name"/></a></li>
								</security:authorize>
								<security:authorize access="!isAuthenticated()">
									<li><a href="<c:url value="/login.html"/>" class="log">Login</a></li>
									<li><a href="<c:url value="/register.html"/>" class="reg">Register</a></li>
								</security:authorize>
							</ul>
						</div>
					</div>
				</div>
				<div class="clearfix"></div>
				<div class="header_bottom">
					<!-- shopping cart -->
					<ul class="option cart">
						<li class="option-cart">
							<a href="#" class="cart-icon">cart <span class="cart_no"></span></a>
							<ul class="option-cart-item"></ul>
							<input type="hidden" id="imageRemove" value="<c:url value="/img/remove.png"/>">
						</li>
					</ul>

					<!-- navigation bar -->
					<nav class="navbar navbar-expand-sm navbar-dark">
						<button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navCategory" aria-controls="navbarNav">
							<span class="sr-only">Toggle navigation</span>
							<span class="fa fa-bars"></span>
						</button>
						<div class="navbar-collapse collapse">
							<ul class="nav navbar-nav">
								<li class="nav-item active"><a class="nav-link" href="javascript:void(0)">Tất cả sản phẩm</a></li>
								<li class="nav-item dropdown">
									<a href="#" class="dropdown-toggle nav-link" data-toggle="dropdown">Thời trang nam</a>
									<div class="dropdown-menu">
										<ul class="mega-menu-links">
											<li><a href="javascript:void(0)">Tất cả sản phẩm nam</a></li>
											<li><a href="javascript:void(0)">Áo nam</a></li>
											<li><a href="javascript:void(0)">Quần nam</a></li>
										</ul>
									</div>
								</li>
								<li class="nav-item dropdown">
									<a href="#" class="dropdown-toggle nav-link" data-toggle="dropdown">Thời trang nữ</a>
									<div class="dropdown-menu mega-menu">
										<div class="row">
											<div class="col-md-6 col-sm-6">
												<ul class="mega-menu-links">
													<li><a href="javascript:void(0)">Tất cả sản phẩm nữ</a></li>
													<li><a href="javascript:void(0)">Áo nữ</a></li>
													<li><a href="javascript:void(0)">Váy nữ</a></li>
												</ul>
											</div>
										</div>
									</div>
								</li>
								<li class="nav-item"><a class="nav-link" href="javascript:void(0)">Feedback</a></li>
							</ul>
						</div>
					</nav>
				</div>
			</div> <%--end right menu--%>
		</div>
	</div>
</div>
<div class="hidden-value">
	<input type="hidden" id="msgCartEmpty" value="<fmt:message key="label.cart.empty"/>">
	<input type="hidden" id="pathRequestShoppingCart" value="<c:url value="/shopping-cart/details.html"/>">
</div>
<script>
	$(document).ready(function () {
		$("ul.cart a.cart-icon").on("click", function (e) {
			e.preventDefault();
			let totalItem = $("span.cart_no").text();
			if(!totalItem || Number(totalItem) === 0) {
				notification($("#msgCartEmpty").val(), false);
				return;
			}
			window.location.href = $("#pathRequestShoppingCart").val();
		});
	});
</script>