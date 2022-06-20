<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="taglib.jsp" %>

<c:set var="role" value="SHOPPER" />
<security:authorize access="hasAuthority('ADMIN')">
	<c:set var="role" value="ADMIN" />
</security:authorize>

<c:url var="searchProductUrl" value="/ajax/product/search.json"/>
<!-- Footer -->
<div class="header">
	<div class="container-fluid">
		<div class="row">
			<%--logo--%>
			<div class="col-md-2 col-sm-2">
				<div class="logo">
				<c:choose>
					<c:when test="${role eq 'SHOPPER'}">
						<a href="<c:url value="/home.html"/>">
							<img src="<c:url value="/img/logo.png"/>" alt="Ú clothing">
						</a>
					</c:when>
					<c:when test="${role eq 'ADMIN'}">
						<a href="<c:url value="/admin/home.html"/>">
							<img src="<c:url value="/img/logo.png"/>" alt="Ú clothing">
						</a>
					</c:when>
				</c:choose>

				</div>
			</div>
			<%--right menu--%>
			<div class="col-md-10 col-sm-10">
				<div class="header_top">
					<div class="row mt-2">
						<!--search product-->
							<c:choose>
								<c:when test="${role eq 'SHOPPER'}">
									<div class="col-md-7">
										<div class="input-group">
											<input class="form-control" id="searchProduct" placeholder="<fmt:message key="label.search"/>" type="search" name="searchProduct">
											<div class="input-group-append">
												<a href="javascript:void(0)" class="btn btn-search-home">
													<i class="fa fa-search" style="line-height: inherit"></i>
												</a>
											</div>
										</div>
									</div>
								</c:when>
								<c:otherwise>
									<!--Shop address-->
									<div class="col-md-7 shop-address">
									  <span>1135/41/30 Huỳnh Tấn Phát, phường Phú Thuận, Quận 7, TP. Hồ Chí Minh</span>
									</div>
								</c:otherwise>
							</c:choose>



						<!--user information-->
						<div class="col-md-5">
							<ul class="usermenu">
								<security:authorize access="isAuthenticated()">
									<c:if test="${role eq 'ADMIN'}">
										<li class="user-menu"><i class="fa fa-bell"></i></li>
									</c:if>
									<li class="user-menu"><a href="#" class="log"><security:authentication property="name"/></a></li>
								</security:authorize>
								<security:authorize access="!isAuthenticated()">
									<li><a href="<c:url value="/login.html"/>" class="log"><fmt:message key="label.login"/></a></li>
									<li><a href="<c:url value="/signup.html"/>" class="reg"><fmt:message key="label.sign-up"/></a></li>
								</security:authorize>
							</ul>
						</div>
					</div>
				</div>
				<div class="clearfix"></div>
				<div class="header_bottom">
					<c:choose>
						<c:when test="${role eq 'SHOPPER'}">
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
						</c:when>
						<c:otherwise>
							<!--navigation Admin-->
							<nav class="navbar navbar-expand-sm navbar-dark">
								<button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navCategory" aria-controls="navbarNav">
									<span class="sr-only">Toggle navigation</span>
									<span class="fa fa-bars"></span>
								</button>
								<div class="navbar-collapse collapse">
									<ul class="nav navbar-nav">
										<li class="nav-item active"><a class="nav-link" href="javascript:void(0)">Trang chủ</a></li>
										<li class="nav-item dropdown">
											<a href="#" class="dropdown-toggle nav-link" data-toggle="dropdown">Quản lý và vận hành</a>
											<div class="dropdown-menu">
												<ul class="mega-menu-links">
													<li><a href="<c:url value="/admin/order/list.html"/>">Quản lý đơn hàng</a></li>
													<li><a href="javascript:void(0)">Quản lý khuyến mãi</a></li>
													<li><a href="<c:url value="/admin/product.html"/>">Quản lý sản phẩm</a></li>
													<li><a href="<c:url value="/admin/catgroup/list.html"/>">Quản lý danh mục sản phẩm</a></li>
													<li><a href="javascript:void(0)">Quản lý khách hàng</a></li>
													<li><a href="javascript:void(0)">Quản lý quà tặng</a></li>
												</ul>
											</div>
										</li>
										<li class="nav-item dropdown">
											<a href="#" class="dropdown-toggle nav-link" data-toggle="dropdown">Quản lý kinh doanh</a>
											<div class="dropdown-menu mega-menu">
												<div class="row">
													<div class="col-md-6 col-sm-6">
														<ul class="mega-menu-links">
															<li><a href="javascript:void(0)">Báo cáo doanh thu</a></li>
															<li><a href="javascript:void(0)">Báo cáo tồn kho</a></li>
														</ul>
													</div>
												</div>
											</div>
										</li>
										<li class="nav-item"><a class="nav-link" href="<c:url value="/admin/order/pos.html"/>" target="_blank">POS</a></li>
									</ul>
								</div>
							</nav>
						</c:otherwise>
					</c:choose>
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

		eventSearchProduct();
	});
	function eventSearchProduct() {
		if('${role}' === 'SHOPPER') {
			$("#searchProduct").autocomplete({
				minLength: 1,
				maxShowItems: 5,
				autofocus: true,
				source: function (request, response) {
					searchProducts(request.term.trim(), response);
				},
				open: function() {
					$("ul.ui-menu").width($(this).innerWidth())
				},
				focus: function (event, ui) {
					event.preventDefault();
				},
				select: function (event, ui) {
					event.preventDefault();
					showSpinner();
					window.location.href = "/EShoping/product/"+ ui.item.catGroupName +"/"+ ui.item.code +"/detail.html";
				},
				close: function () {
					$(this).select();
				}
			}).data("ui-autocomplete")._renderItem = function (ul, item) {
				let row = '';
				if(item.isExist) {
					row = "<div class='item-product d-flex flex-wrap'> " +
							"<div class='item-left mr-3'>" +
							"<div class='image-item'>" +
							"<img src='"+ "/EShoping/" + item.image +"' class='img-fluid' width='50px' height='50px'/>" +
							"</div>" +
							"</div>" +
							"<div class='item-right'>" +
							"<div class='mb-2'><span class='title-item'> " + item.productName + "</span></div>" +
							"<div><span class='label-price'>" +
							formatNumber(item.referencePrice) +
							"</span></div>" +
							"</div>" +
							"</div>";
				} else {
					row = "<div class='d-flex justify-content-center align-items-center'><div class='p-2'>" + item.label+ "</div></div>";
				}
				return $("<li class='item-product'>").append(row).appendTo(ul);
			}
		}
	}


	 function searchProducts(inputQuery, callback) {
		$.ajax({
			url: '${searchProductUrl}',
			data: {
				query: inputQuery,
			},
			method: "get",
			contentType: "application/json"
		}).done(function (response) {
			console.log("response", response);
			if(response.result) {
				callback($.map(response.products, function (item) {
					let price = item.referencePrice.highestPrice != null ?
							item.referencePrice.lowestPrice + " - " + item.referencePrice.highestPrice : item.referencePrice.lowestPrice + "";
					return {
						productName : item.name,
						image: item.image,
						referencePrice: price,
                        catGroupName: item.catGroup.name,
                        code: item.code,
						isExist : true
					}
				}));
			} else {
				callback([[{label: 'Không tìm thấy sản phẩm', isExist: false}]]);
			}
		});
	}

</script>