<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglib.jsp"%>

<div class="wrapper">
    <div class="container_fullwidth">
        <div class="container">
            <div class="row">
                <%--Product details--%>
                <div class="products-details">
                    <%--product image--%>
                    <div class="preview_image">
                        <div class="preview-small">
                            <img src="${product.image}" alt="product image" onerror="this.onerror=null;this.src='<c:url value="/img/default-placeholder.png"/>'"/>
                        </div>
                        <div class="thum-image">
                            <ul id="gallery_01" class="prev-thum">
                                <c:forEach items="${product.sku}" var="productSku">
                                    <li>
                                        <a href="#" data-image="<c:url value="${productSku.image}"/>" data-zoom-image="<c:url value="${productSku.image}"/>">
                                            <img src="<c:url value="${productSku.image}"/>" alt="${productSku.title}">
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                            <a class="control-left" id="thum-prev" href="javascript:void(0);">
                                <i class="fa fa-chevron-left"></i>
                            </a>
                            <a class="control-right" id="thum-next" href="javascript:void(0);">
                                <i class="fa fa-chevron-right"></i>
                            </a>
                        </div>
                    </div>

                    <%--Product details--%>
                    <div class="products-description">
                        <%-- Product name --%>
                        <h5 class="name">
                            ${product.name}
                        </h5>

                        <%-- Review --%>
                        <p>
                            <span class="fa fa-star star-checked"></span>
                            <span class="fa fa-star star-checked"></span>
                            <span class="fa fa-star star-checked"></span>
                            <span class="fa fa-star"></span>
                            <span class="fa fa-star"></span>
                            <a class="review_num" href="#">02 Review(s)</a>
                        </p>
                        <%--Avaiable--%>
                        <p>
                            <fmt:message key="label.availability"/>:
                            <span class=" light-red">
                                In Stock
                            </span>
                        </p>
                        <hr class="my-border">

                        <%-- Price --%>
                        <div class="price">
                            <span><fmt:message key="label.price"/></span>
                            <span class="new_price"><fmt:formatNumber value="${product.referencePrice.lowestPrice}"/></span>
                        </div>
                        <hr class="my-border">

<%--                        <div class="">--%>
<%--                            <span><fmt:message key="label.color"/></span>--%>
<%--                            <c:choose>--%>
<%--                                <c:when test="${product.}"></c:when>--%>
<%--                                <c:otherwise>--%>
<%--                                    <span><fmt:message key="label.color.default"/></span>--%>
<%--                                </c:otherwise>--%>
<%--                            </c:choose>--%>
<%--                        </div>--%>

                        <%--Quantity--%>
                        <div class="qty">
                            <span><fmt:message key="label.quantity" /></span>
                            <div class="quantity-group form-inline">
                                <button class="btn btn-outline-dark quantity-minus"><i class="fa fa-minus"></i></button>
                                <input type="text" id="inputQuantity" class="quantity-input" value="1"/>
                                <button class="btn btn-outline-dark quantity-plus"><i class="fa fa-plus"></i></button>
                            </div>
                        </div>
                        <div class="clearfix"></div>

                        <%--Button add to cart--%>
                        <div style="margin-top: 1rem">
                            <button class="btn btn-primary"> <fmt:message key="label.button.add-to-cart"/> </button>
                            <button class="btn btn-danger"> <fmt:message key="label.button.buy-now"/> </button>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                </div>
                <div class="clearfix"></div>

                <%--Tab description, review--%>
                <div class="tab-box">
                    <div id="tabnav">
                        <ul>
                            <li>
                                <a href="#Description">
                                    <fmt:message key="label.button.description"/>
                                </a>
                            </li>
                            <li>
                                <a href="#Reviews">
                                    <fmt:message key="label.button.review"/>
                                </a>
                            </li>
                        </ul>
                    </div>
                    <div class="tab-content-wrap">
                        <div class="tab-content" id="Description">
                            <c:choose>
                                <c:when test="${product.description != null}">
                                    ${product.description}
                                </c:when>
                                <c:otherwise>
                                    <p><fmt:message key="label.no.data"/></p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="tab-content" id="Reviews">
                            <p><fmt:message key="label.no.data"/></p>
                        </div>
                    </div>
                </div>
                <div class="clearfix"></div>

                <%--Related product--%>
                <div id="productsDetails" class="hot-products">
                    <h3 class="title">
                        <fmt:message key="label.related.product"/>
                    </h3>
                    <div class="control">
                        <a id="prev_hot" class="prev" href="#">
                            &lt;
                        </a>
                        <a id="next_hot" class="next" href="#">
                            &gt;
                        </a>
                    </div>
                    <ul id="hot">
                        <li>
                            <div class="row">
                                <c:forEach var="relatedProduct" items="${product.relatedProducts}">
                                    <div class="col-md-4 col-sm-4">
                                        <div class="products">
                                            <div class="offer">
                                                - %20
                                            </div>
                                            <div class="thumbnail">
                                                <img src="<c:url value="${relatedProduct.image}"/>" alt="${relatedProduct.name}">
                                            </div>
                                            <div class="productname">
                                                    ${relatedProduct.name}
                                            </div>
                                            <h4 class="price">
                                                    ${relatedProduct.referencePrice.lowestPrice}
                                                <c:if test="${relatedProduct.referencePrice.highestPrice != null}">
                                                    ~ ${relatedProduct.referencePrice.highestPrice}
                                                </c:if>
                                            </h4>
<%--                                            <div class="button_group">--%>
<%--                                                <button class="button add-cart" type="button">--%>
<%--                                                    Add To Cart--%>
<%--                                                </button>--%>
<%--                                                <button class="button compare" type="button">--%>
<%--                                                    <i class="fa fa-exchange">--%>
<%--                                                    </i>--%>
<%--                                                </button>--%>
<%--                                                <button class="button wishlist" type="button">--%>
<%--                                                    <i class="fa fa-heart-o">--%>
<%--                                                    </i>--%>
<%--                                                </button>--%>
<%--                                            </div>--%>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="clearfix"></div>
            </div>
        </div>
    </div>
</div>