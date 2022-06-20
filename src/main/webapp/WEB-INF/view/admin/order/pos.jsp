<%--
  Created by IntelliJ IDEA.
  User: taquo
  Date: 5/28/2022
  Time: 5:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../common/taglib.jsp"%>

<!doctype html>
<html lang="en">
<head>
    <title><fmt:message key="label.title.pos"/></title>
    <meta charset="UTF-8">
    <meta name="viewport"content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
    <link href='http://fonts.googleapis.com/css?family=Roboto:400,300,300italic,400italic,500,700,500italic,100italic,100' rel='stylesheet' type='text/css'>
    <%--  jquery  --%>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <%--  jquery ui --%>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <!-- bootstrap-select -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">
    <%--point of sale css--%>
    <link rel="stylesheet" href="<c:url value="/css/point-of-sale.css"/>" />
    <%--scrolling-tabs--%>
    <link rel="stylesheet" href="<c:url value="/css/jquery-scrolling-tabs/jquery.scrolling-tabs.css"/>" />

</head>
<body>
<div class="master-wrap h-100">
    <div class="container-fluid">
        <!--header-->
        <div class="row">
            <div class="head col-12">
                <div class="navbar-outlet">
                <div class="row">
                    <div class="col-sm-8">
                        <div class="row">
                            <%--search product box--%>
                            <div class="col-sm-6 search-product-nav">
                                <div class="form-group mt-2 ml-2">
                                    <input type="search" id="searchProductBox" class="form-control"
                                           placeholder="<fmt:message key="label.pos.search.product"/> (F1)">
                                </div>
                            </div>
                            <%--scrollable nav tab--%>
                            <div class="col-sm-6 invoice-list-nav">
                                <ul class="nav nav-tabs border-bottom-0 mt-2" role="tablist">
                                    <li role="presentation" id="navItemAdd" class="nav-item">
                                        <a id="btnAddTab" class="nav-link" href="#" aria-selected="false">
                                            <i class="fa fa-plus text-price-medium text-white"></i>
                                        </a>
                                    </li>
                                </ul>
                            </div>

                        </div>
                    </div>
                    <%--column right navbar--%>
                    <div class="col-sm-4">
                        <div class="row no-gutters">
                            <div id="connectionStatus"></div>
                            <ul class="nav common-nav-tabs nav-icon-info ml-auto online-menu">
                                <li class="nav-item">
                                    <a id="btnListHotKey" href="javascript:void(0);" class="nav-link" title="<fmt:message key="label.pos.title.list-hot-key" />">
                                        <i class="fa fa-info"></i>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            </div>
         </div>
        <!--end header-->
        <!--Tab panes-->
        <div class="content h-100" id="contentPage">
            <div class="card h-100">
                <div id="tabContent" class="card-body">
                    <div class="row h-100">
                        <%--column left--%>
                        <div class="col-left col-sm-9">
                            <div class="wrap-content-left">
                                <%--product-cart--%>
                                <div class="product-cart"></div>
                            </div>
                            <!-- message -->
                            <div class="alert-message">
                                <p class="content-message">
                                    <span>(<i class="fa fa-bell"></i>)</span>&nbsp;
                                    <span id="contentMessage" class="font-weight-bold"></span>
                                </p>
                            </div>
                        </div> <%--end column left--%>

                        <%--column right--%>
                        <div class="col-right col-sm-3">
                            <div class="card h-100">
                                <div class="card-body">
                                    <%--saler row--%>
                                    <div class="sale-user mb-2">
                                        <div class="form-inline">
                                            <div class="form-group col-sm-6 p-0">
                                        <span>
                                            <i class="fa fa-user-circle"></i>
                                            <span class="border-bottom">Admin</span>
                                        </span>
                                            </div>
                                            <div class="form-group col-sm-6 p-0">
                                                <div class="ml-auto">
                                                    <c:set var="currentDate" value="<%= new java.util.Date() %>" />
                                                    <span class="mr-2 text-secondary border-bottom" id="labelCurrentDate">
                                                <fmt:formatDate value="${currentDate}" dateStyle="short"/>
                                                </span>
                                                        <span class="text-secondary border-bottom" id="labelCurrentTime">
                                                    <fmt:formatDate value="${currentDate}" type="TIME" timeStyle="short"/>
                                                </span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <%--content column right--%>
                                    <div class="col-right-container">
                                        <div class="col-right-inside">
                                            <div>
                                                <div class="mb-3">
                                                    <div class="form-note input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text addon"><i class="fa fa-search"></i></span>
                                                        </div>
                                                        <input type="text" id="searchCustomerBox" name="pojo.node" class="form-control"
                                                               placeholder="<fmt:message key="label.pos.search.customer"/> (F3)"/>
                                                        <div class="input-group-append btn" id="btnRemoveCustomer">
                                                            <span class="input-group-text addon btn-outline-danger"><i class="fa fa-times"></i></span>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="mb-3 delivery-address">
                                                    <div class="form-note input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text addon"><i class="fa fa-address-book"></i></span>
                                                        </div>
                                                        <input type="text" id="deliveryAddress" name="pojo.node" class="form-control address"
                                                               placeholder="<fmt:message key="label.pos.delivery-address"/> (F4)" data-addressid="0"/>
                                                        <input type="hidden" id="deliveryLongitude" class="long_0">
                                                        <input type="hidden" id="deliveryLatitude" class="lat_0">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="card">
                                                <div class="row">
                                                    <div class="card-body navbar-outlet m-0 text-center text-uppercase">
                                                        <span class="w-100" style="color: white"><fmt:message key="label.pos.invoices.information"/></span>
                                                    </div>
                                                </div>
                                            </div>
                                            <hr class="mt-0">
                                            <%--payment component--%>
                                            <div class="payment-component">
                                                <div class="payment-component-child">
                                                    <%--Total price--%>
                                                    <div class="form-group row">
                                                        <div class="form-label col-md-7">
                                                            <span><fmt:message key="label.pos.total.price"/></span>&nbsp;
                                                            <span id="totalItem" class="badge badge-info"></span>
                                                        </div>
                                                        <div class="form-output col-md-5 text-right">
                                                            <span id="totalOriginalPrice" class="text-price-medium">0</span>
                                                        </div>
                                                    </div>

                                                    <%--delivery fee--%>
                                                    <div class="delivery-fee hide">
                                                        <div class="form-group row">
                                                            <label class="col-sm-6"><fmt:message key="label.delivery.fee"/></label>
                                                            <div class="col-sm-6 text-right">
                                                                <span id="deliveryFee">0</span>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <%--Discount--%>
                                                    <div class="form-group row">
                                                        <div class="col-md-7">
                                                            <span><fmt:message key="label.pos.special.discount"/></span>&nbsp;
                                                        </div>
                                                        <div class="form-output col-md-5 text-right">
                                                            <input type="text" id="inputDiscountByCash" class="form-control text-danger text-right pr-0" placeholder="0 (F6)"/>
                                                        </div>
                                                    </div>

                                                    <%-- loyalty point --%>
                                                    <div class="form-group row">
                                                        <div id="lblLoyaltyInfor" class="form-label col-md-7 click-able-promotion">
                                                            <span><fmt:message key="label.pos.loyalty-point"/></span>&nbsp;
                                                        </div>
                                                        <div class="form-output col-md-5 text-right">
                                                            <span id="totalLoyaltyPoint">0</span>
                                                        </div>
                                                    </div>



                                                    <div class="delivery-method hide"></div>
                                                    <input type="hidden" id="deliveryMethod">

                                                    <%--Customer have to pay--%>
                                                    <div class="form-group row">
                                                        <label class="col-sm-6 col-form-label font-weight-bold"><fmt:message key="label.pos.customer.must.pay"/></label>
                                                        <div class="form-output col-sm-6 text-right">
                                                            <span id="moneyCustomerMustPay" class="text-primary text-price-lg">0</span>
                                                        </div>
                                                    </div>

                                                    <input type="hidden" id="selectPayment" value="COD">
                                                </div>
                                            </div>
                                        </div>


                                        <div class="col-right-inside mb-3">
                                            <div class="form-note input-group">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text addon"><i class="fa fa-pencil"></i></span>
                                                </div>
                                                <input type="text" id="noteBox" name="pojo.node" class="form-control" placeholder="<fmt:message key="label.pos.note"/> (F7)"/>
                                            </div>
                                        </div>
                                        <%--button sale offline--%>
                                        <div class="wrap-button mt-2">
                                            <div class="mb-2">
                                                <button id="btnPaySubmit" class="btn btn-success btn-pay rounded-0"><fmt:message key="label.pos.pay"/></button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div> <%--end column right--%>
                    </div>
                </div>
            </div>
        </div>
        <!--End tab panes-->
        <div id="modalShowHotkey" class="modal">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <%--modal header--%>
                    <div class="modal-header">
                        <h5 class="modal-title"><fmt:message key="label.pos.title.list-hot-key"/></h5>
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <%--modal body--%>
                    <div class="modal-body"></div>
                    <%--modal footer--%>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="label.close"/></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<section>
    <%--  label, message  --%>
    <input type="hidden" id="invoiceName" value="<fmt:message key="label.pos.invoices"/>"/>
    <input type="hidden" id="messageCartEmpty" value="<fmt:message key="label.pos.message-empty-cart"/>"/>
    <input type="hidden" id="messageWarningDeleteTab" value="<fmt:message key="label.pos.message-warning-delete-tab"/>"/>
    <input type="hidden" id="messageErrorOccur" value="<fmt:message key="label.pos.message-error-occur"/>"/>
    <input type="hidden" id="messageNoProductsFound" value="<fmt:message key="label.pos.message-no-product-found"/>"/>

    <%--url--%>
    <c:url var="searchProductUrl" value="/ajax/admin/sku/dimension/search.json"/>
    <input type="hidden" id="searchProductUrl" value="${searchProductUrl}">
</section>


<%--script--%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootbox.js/5.5.2/bootbox.js" integrity="sha512-K3MtzSFJk6kgiFxCXXQKH6BbyBrTkTDf7E6kFh3xBZ2QNMtb6cU/RstENgQkdSLkAZeH/zAtzkxJOTTd8BqpHQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="<c:url value="/js/global-eshopping.js"/>"></script>
<!-- bootstrap-select -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>
<%--scrolling tabs --%>
<script src="<c:url value="/js/jquery-scrolling-tabs/jquery.scrolling-tabs.js"/>"></script>
<%--indexes DB --%>
<script src="<c:url value="/js/global-eshopping-idb.js"/>"></script>
<%--point of sales --%>
<script src="<c:url value="/js/point-of-sale.js"/>"></script>
</body>
</html>

