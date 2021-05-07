<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>

<div class="container_fullwidth">
	<div class="container">
		<form:form action="#" modelAttribute="item" >
			<div class="hot-products">
				<c:choose>
					<c:when test="${item.listResult.size() > 0}">
						<div class="row">
						<c:forEach var="product" items="${item.listResult}">

								<div class="col-md-3 col-sm-6">
									<div class="products">
											<%--								<div class="offer">- %20</div>--%>
										<a href="<c:url value="/product/${product.catGroup.name}/${product.code}/detail.html"/>">
											<div class="thumbnail">
												<img src="${product.image}" alt="Product Name" style="width: auto; height: 100%" />
											</div>
										</a>
										<div class="productname">${product.name}</div>
										<c:choose>
											<c:when test="${product.referencePrice.highestPrice != null}">
												<h4 class="price">
													<fmt:formatNumber value="${product.referencePrice.lowestPrice}"/>
													<span> ~ </span>
													<fmt:formatNumber value="${product.referencePrice.highestPrice}"/>
												</h4>
											</c:when>
											<c:otherwise>
												<h4 class="price"><fmt:formatNumber value="${product.referencePrice.lowestPrice}"/></h4>
											</c:otherwise>
										</c:choose>
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