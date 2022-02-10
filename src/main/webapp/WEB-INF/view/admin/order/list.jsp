<%--
  Created by IntelliJ IDEA.
  User: QTH02
  Date: 12/28/2021
  Time: 3:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../common/taglib.jsp"%>

<c:url var="formListUrl" value="/admin/order/list.html"/>

<div class="container-fluid">
    <!-- Page-Title -->
    <div class="row">
        <div class="col-sm-12">
            <div class="page-title-box">
                <div class="btn-group pull-right">
                    <ol class="breadcrumb hide-phone p-0 m-0">
                        <li class="breadcrumb-item">
                            <a href="<c:url value="/admin/home.html"/>"><fmt:message key="label.home"/></a></li>
                        <li class="breadcrumb-item active"><fmt:message key="label.order.management"/></li>
                    </ol>
                </div>
                <h3 class="page-title"><fmt:message key="label.order.management"/></h3>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-12">
            <div class="card mb-5">
                <div class="card-body">

                    <c:if test="${not empty messageResponse}">
                        <div class="alert alert-${alert} alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <strong>${messageResponse}</strong>
                        </div>
                    </c:if>

                    <form:form modelAttribute="items" action="${formListUrl}" id="listForm">
                        <form:hidden path="crudaction" id="crudaction"/>
                        <%--search--%>
                        <div class="search-filter mb-3">
                            <div>
                                <div class="row m-t-10 ml-1 m-b-15 text-left">
                                    <%-- all --%>
                                    <div class="border border-primary btn mr-1 text-left" id="all">
                                        <div><p class="text-uppercase"><fmt:message key="label.all"/></p>
                                            <span style="font-size: 24px;">${items.total}</span>&nbsp;
                                            <fmt:message key="label.orders"/>
                                        </div>
                                    </div>
                                    <%-- waiting for confirm --%>
                                    <div class=" btn btn-warning m-l-15 mr-1 text-left" id="waitForConfirm">
                                        <div><p class="text-uppercase"><fmt:message key="order.status.waiting.confirm"/></p>
                                            <span style="font-size: 24px;">${items.totalWaitingForConfirm}</span>&nbsp;
                                            <fmt:message key="label.orders"/></div>
                                    </div>'
                                    <%-- waiting for picking --%>
                                    <div class="btn btn-primary m-l-15 mr-1 text-left" id="waitForPick">
                                        <div><p class="text-uppercase"><fmt:message key="order.status.waiting.pick"/></p>
                                            <span style="font-size: 24px;">${items.totalPicking}</span>&nbsp;
                                            <fmt:message key="label.orders"/></div>
                                    </div>
                                    <%-- delivering --%>
                                    <div class=" btn btn-info m-l-15 mr-1 text-left" id="delivering">
                                        <div><p class="text-uppercase"><fmt:message key="order.status.delivering"/></p>
                                            <span style="font-size: 24px;">${items.totalDelivering}</span>&nbsp;
                                            <fmt:message key="label.orders"/></div>
                                    </div>
                                    <%-- success --%>
                                    <div class="btn btn-success m-l-15 mr-1 text-left" id="success">
                                        <div><p class="text-uppercase"><fmt:message key="order.status.success"/></p>
                                            <span style="font-size: 24px;">${items.totalSuccess}</span>&nbsp;
                                            <fmt:message key="label.orders"/></div>
                                    </div>
                                    <%-- cancel --%>
                                    <div class="btn btn-danger m-l-15 mr-1 text-left" id="cancel">
                                        <div><p class="text-uppercase"><fmt:message key="order.status.cancel"/></p>
                                            <span style="font-size: 24px;">${items.totalCancel}</span>&nbsp;
                                            <fmt:message key="label.orders"/></div>
                                    </div>
                                    <%-- return item --%>
                                    <div class="btn btn-secondary m-l-15 mr-1 text-left" id="return">
                                        <div><p class="text-uppercase"><fmt:message key="order.status.return"/></p>
                                            <span style="font-size: 24px;">${items.totalReturn}</span>&nbsp;
                                            <fmt:message key="label.orders"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="search-filter">
                            <div class="form-group row">
                                <!--customer name-->
                                <label class="col-sm-6 col-md-1 col-form-label"><fmt:message key="label.customer.name"/></label>
                                <div class="col-sm-6 col-md-3">
                                    <form:input path="pojo.customer.user.fullName" cssClass="form-control"/>
                                    <form:errors path="pojo.customer.user.fullName" cssClass="error"/>
                                </div>
                                <!--customer phone-->
                                <label class="col-sm-6 col-md-1 col-form-label"><fmt:message key="label.phone"/></label>
                                <div class="col-sm-6 col-md-3">
                                    <form:input path="pojo.customer.user.phoneNumber" cssClass="form-control"/>
                                    <form:errors path="pojo.customer.user.phoneNumber"/>
                                </div>
                            </div>
                            <!--status-->
<%--                            <div class="form-group row">--%>
<%--                                <label class="col-md-1 col-form-label"><fmt:message key="label.status"/></label>--%>
<%--                                <div class="col-5 form-inline">--%>
<%--                                    <form:select path=""--%>
<%--                                </div>--%>
<%--                            </div>--%>
                            <!--button search-->
                            <div class="form-group text-center">
                                <a id="btnSearch" href="#" class="btn btn-sm hg btn-primary waves-effect waves-light text-center ml-2">
                                    <i class="fa fa-search"></i><fmt:message key="label.search"/>
                                </a>
                            </div>
                        </div>

                        <!--table-->
                        <div class="dataTables_wrapper no-footer" id="datatable-buttons_wrapper">
                            <div class="row">
                                <div class="col-sm-12">
                                    <display:table name="items.listResult" cellspacing="0" cellpadding="0"
                                                   requestURI="${formListUrl}" defaultsort="6" defaultorder="descending"
                                                   partialList="true" sort="external" size="${items.totalItems}"
                                                   id="tableList" excludedParams="checkList"
                                                   pagesize="${items.maxPageItems}" export="false"
                                                   class="table table-striped table-bordered dataTable no-footer">

                                        <display:column title="<fieldset class='custom-control custom-checkbox'>
                                                <input type='checkbox' id='checkAll' class='custom-control-input'>
                                                <label for='checkAll' class='custom-control-label'>&nbsp;</label>
                                            </fieldset>" class="text-center table_menu_items" headerClass="white_text " style="width:2%">
                                            <fieldset class='custom-control custom-checkbox'>
                                                <input type="checkbox" name="checkList" id="checkbox${tableList_rowNum}"
                                                       value="${tableList.orderOutletId}" class="checkbox_user custom-control-input">
                                                <label for="checkbox${tableList_rowNum}" class="custom-control-label">&nbsp;</label>
                                            </fieldset>
                                        </display:column>

                                        <%--code--%>
                                        <display:column headerClass="sorting" class="text-center"
                                                        property="code"
                                                        sortName="code"
                                                        sortable="true" titleKey="label.order.code"/>
                                        <%-- customer name--%>
                                        <display:column class="text-left"
                                                        property="customer.user.name"
                                                        sortName="name"
                                                        sortable="true" titleKey="label.customer.name"/>
                                        <%--customer phone--%>
                                        <display:column property="customer.user.phoneNumber"
                                                        class="text-center"
                                                        titleKey="label.phone"/>

                                        <%--quantity--%>
                                        <display:column class="text-center" headerClass="text-center" titleKey="label.quantity"/>


                                        <%--created date--%>
                                        <display:column headerClass="sorting" sortable="true" class="text-center"
                                                        sortName="createdDate" titleKey="label.created-date">
                                            <fmt:formatDate value="${tableList.createdDate}" pattern="dd/MM/yyyy"/>
                                        </display:column>


                                        <%--status--%>
                                        <display:column headerClass="sorting" class="usergroup_info"
                                                        sortName="status"
                                                        style="width:8%"
                                                        sortable="true" titleKey="label.status">
                                            <c:choose>
                                                <c:when test="${tableList.status == WebConstants.WAITING_FOR_CONFIRMATION}">
                                                    <span class="badge badge-warning badge-pill"><fmt:message
                                                            key="${WebConstants.WAITING_FOR_CONFIRMATION_LABEL}"/></span>
                                                </c:when>
                                                <c:when test="${tableList.status == WebConstants.WAITING_FOR_PICKING}">
                                                    <span class="badge badge-primary  badge-pill"><fmt:message
                                                            key="${WebConstants.WAITING_FOR_PICKING_LABEL}"/></span>
                                                </c:when>
                                                <c:when test="${tableList.status == WebConstants.SUCCESS}">
                                                    <span class="badge badge-success badge-pill"><fmt:message
                                                            key="${WebConstants.SUCCESS_LABEL}"/></span>
                                                </c:when>
                                                <c:when test="${tableList.status == WebConstants.RETURN}">
                                                    <span class="badge badge-secondary badge-pill">
                                                        <fmt:message key="${WebConstants.RETURN_LABEL}"/></span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span style="" class="badge badge-danger badge-pill"><fmt:message
                                                            key="${WebConstants.CANCELED_LABEL}"/></span>
                                                </c:otherwise>
                                            </c:choose>
                                        </display:column>

                                        <display:column class="text-center" titleKey="label.action">
                                            <a href="${catGroupEditUrl}?pojo.code=${tableList.code}" class="teal-text btn-update" data-toggle="tooltip"
                                               data-placement="top" title="<fmt:message key="label.edit"/>">
                                                <i class="fa fa-edit" aria-hidden="true"></i>
                                            </a>
                                            <i class="separator"></i>
                                            <a href="#" class="red-text removeCatGroup" data-toggle="tooltip"
                                               data-placement="top" title="<fmt:message key="label.delete"/>">
                                                <i class="fa fa-trash-o" aria-hidden="true"></i>
                                            </a>
                                        </display:column>
                                    </display:table>
                                </div>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
