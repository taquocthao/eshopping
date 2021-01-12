<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="./taglib.jsp" %>
<div class="header">
	<div class="container">
		<div class="row">
			<div class="col-md-2 col-sm-2">
				<div class="logo"><a href="<c:url value="/home.html"/>"><img src="<c:url value="/img/logo.png"/>" alt="Ú clothing"></a></div>
			</div>
			<div class="col-md-10 col-sm-10">
				<div class="header_top">
					<div class="row mt-2">
						<div class="col-md-7">
							<div class="row">
								<div class="col-10">
									<input class="form-control" placeholder="Enter your search term..." type="search" name="search">
								</div>
								<div class="col-2">
									<input class="btn btn-primary" type="submit" value="Tìm kiếm">
								</div>
							</div>
						</div>
						<div class="col-md-5">
							<ul class="usermenu">
								<li><a href="checkout.html" class="log">Login</a></li>
								<li><a href="checkout2.html" class="reg">Register</a></li>
							</ul>
						</div>
					</div>
				</div>
				<div class="clearfix"></div>
				<div class="header_bottom">
					<ul class="option">
						<li class="option-cart">
							<a href="#" class="cart-icon">cart <span class="cart_no">02</span></a>
							<ul class="option-cart-item">
								<li>
									<div class="cart-item">
										<div class="image"><img src="images/products/thum/products-01.png" alt=""></div>
										<div class="item-description">
											<p class="name">Lincoln chair</p>
											<p>Size: <span class="light-red">One size</span><br>Quantity: <span class="light-red">01</span></p>
										</div>
										<div class="right">
											<p class="price">$30.00</p>
											<a href="#" class="remove"><img src="images/remove.png" alt="remove"></a>
										</div>
									</div>
								</li>
								<li>
									<div class="cart-item">
										<div class="image"><img src="images/products/thum/products-02.png" alt=""></div>
										<div class="item-description">
											<p class="name">Lincoln chair</p>
											<p>Size: <span class="light-red">One size</span><br>Quantity: <span class="light-red">01</span></p>
										</div>
										<div class="right">
											<p class="price">$30.00</p>
											<a href="#" class="remove"><img src="images/remove.png" alt="remove"></a>
										</div>
									</div>
								</li>
								<li><span class="total">Total <strong>$60.00</strong></span><button class="checkout" onClick="location.href='checkout.html'">CheckOut</button></li>
							</ul>
						</li>
					</ul>
					<!-- navigation bar -->
					<nav class="navbar navbar-expand">

						<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navCategory">
							<span class="sr-only">Toggle navigation</span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
						</button>

						<div id="navCategory" class="navbar-collapse collapse">
							<ul class="navbar-nav">
								<li class="nav-item active dropdown">
									<a href="#" class="dropdown-toggle" data-toggle="dropdown">Home</a>
									<div class="dropdown-menu">
										<ul class="mega-menu-links">
											<li><a href="index.html">home</a></li>
											<li><a href="home2.html">home2</a></li>
											<li><a href="home3.html">home3</a></li>
											<li><a href="productlitst.html">Productlitst</a></li>
											<li><a href="productgird.html">Productgird</a></li>
											<li><a href="details.html">Details</a></li>
											<li><a href="cart.html">Cart</a></li>
											<li><a href="checkout.html">CheckOut</a></li>
											<li><a href="checkout2.html">CheckOut2</a></li>
											<li><a href="contact.html">contact</a></li>
										</ul>
									</div>
								</li>
								<li class="nav-item"><a class="nav-link" href="productgird.html">men</a></li>
								<li class="nav-item"><a class="nav-link" href="productlitst.html">women</a></li>
								<li class="nav-item dropdown">
									<a href="#" class="dropdown-toggle" data-toggle="dropdown">Fashion</a>
									<div class="dropdown-menu mega-menu">
										<div class="row">
											<div class="col-md-6 col-sm-6">
												<ul class="mega-menu-links">
													<li><a href="productgird.html">New Collection</a></li>
													<li><a href="productgird.html">Shirts & tops</a></li>
													<li><a href="productgird.html">Laptop & Brie</a></li>
													<li><a href="productgird.html">Dresses</a></li>
													<li><a href="productgird.html">Blazers & Jackets</a></li>
													<li><a href="productgird.html">Shoulder Bags</a></li>
												</ul>
											</div>
											<div class="col-md-6 col-sm-6">
												<ul class="mega-menu-links">
													<li><a href="productgird.html">New Collection</a></li>
													<li><a href="productgird.html">Shirts & tops</a></li>
													<li><a href="productgird.html">Laptop & Brie</a></li>
													<li><a href="productgird.html">Dresses</a></li>
													<li><a href="productgird.html">Blazers & Jackets</a></li>
													<li><a href="productgird.html">Shoulder Bags</a></li>
												</ul>
											</div>
										</div>
									</div>
								</li>
							</ul>
						</div>
					</nav>

				</div>
			</div>
		</div>
	</div>
</div>