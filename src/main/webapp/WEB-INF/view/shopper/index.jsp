<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ include file="../common/taglib.jsp"%>--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>

<div class="container_fullwidth">
	<div class="container">
		<form:form action="#" modelAttribute="item" >
			<div class="hot-products">
				<c:choose>
					<c:when test="${item.listResult.size() > 0}">
						<div class="row">
						<c:forEach var="productSku" items="${item.listResult}">

								<div class="col-md-3 col-sm-6">
									<div class="products">
											<%--								<div class="offer">- %20</div>--%>
										<div class="thumbnail">
											<a href="#"><img src="${productSku.image}" alt="Product Name" class="image-fluid" /></a>
										</div>
										<div class="productname">${productSku.title}</div>
										<h4 class="price">${productSku.salePrice}</h4>
										<div class="button_group">
											<button class="button add-cart" type="button">Add To Cart</button>
											<button class="button compare" type="button"><i class="fa fa-exchange"></i></button>
											<button class="button wishlist" type="button"><i class="fa fa-heart-o"></i></button>
										</div>
									</div>
								</div>

						</c:forEach>
						</div>
					</c:when>
					<c:otherwise>
						<p>nothing to show</p>
					</c:otherwise>
				</c:choose>

			</div>
			<div class="clearfix"></div>
		</form:form>
	</div>
</div>