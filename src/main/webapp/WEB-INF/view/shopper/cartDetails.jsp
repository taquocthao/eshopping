<%--
  Created by IntelliJ IDEA.
  User: taquo
  Date: 5/30/2021
  Time: 1:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglib.jsp"%>

<div class="wrapper">
    <div class="container_fullwidth">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <%--Receiver address--%>
                    <div class="delivery-address-wrapper">
                        <h4 class="text-warning mb-4"><i class="fa fa-map-marker-alt"></i><fmt:message key="label.delivery.address"/></h4>
                        <div class="delivery-address">
                            <ul class="deliveries">
                                <li class="address">
                                    <span class="font-weight-bold">Hồ Thị Thủy Tiên</span>&nbsp;<span class="font-weight-bold">0938322737</span>
                                    &nbsp;&nbsp;<span>1247 Huỳnh Tấn Phát, Quận 7, Tp.Hồ Chí Minh</span>
                                </li>
                            </ul>
                        </div>
                    </div><hr class="my-border">
                    <%--Cart items--%>
                    <div class="cart-items-wrapper">
                        <h4 class="text-warning mb-4"><fmt:message key="label.product"/></h4>
                        <div class="product-items">
                            <div class="table-product-items">
                            </div>
                        </div>
                    </div><hr class="my-border">
                    <%--payment method--%>
                    <div class="payment-methods-wrapper">
                        <h4 class="text-warning mb-4"><fmt:message key="label.payment-methods"/></h4>
                        <div class="payment-methods">
                            <div class="row">
                                <div class="m-1">
                                    <button class="btn btn-outline-secondary">Airpay</button>
                                </div>
                                <div class="m-1">
                                    <button class="btn btn-outline-secondary">Momo</button>
                                </div>
                                <div class="m-1">
                                    <button class="btn btn-outline-secondary">Chuyển khoản</button>
                                </div>
                                <div class="m-1">
                                    <button class="btn btn-outline-secondary">Thanh toán khi nhận hàng</button>
                                </div>
                            </div>
                        </div>
                    </div><hr class="my-border">
                    <div class="overview">
                        <div class="row">
                            <div class="col-4"></div>
                            <div class="col-4"></div>
                            <div class="col-4">
                                <div class="row">
                                    <div class="mr-auto">Tổng tiền hàng</div><span>22222</span>
                                </div>
                                <div class="row">
                                    <div class="mr-auto">Phí vận chuyển</div><span>22222</span>
                                </div>
                                <div class="row">
                                    <div class="mr-auto">Tổng thanh toán</div><span>22222</span>
                                </div>
                            </div>
                        </div>
                    </div><hr class="my-border">
                    <!--Button Checkout-->
                    <div class="text-right">
                        <button class="btn btn-danger"><fmt:message key="label.button.order"/></button>
                    </div>
                </div>
            </div>
            <div class="hidden">
                <input type="hidden" id="labelDelete" value="<fmt:message key='label.delete'/>">
                <input type="hidden" id="defaultImage" value="<c:url value="/img/default-placeholder.png"/>"/>
            </div>
        </div>
    </div>
</div>
<script>


</script>
