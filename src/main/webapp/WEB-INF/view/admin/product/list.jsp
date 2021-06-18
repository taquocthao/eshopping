<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../common/taglib.jsp"%>

<c:url var="formListUrl" value="/admin/product.html"/>

<div class="container-fluid">

    <!-- Page-Title -->
    <div class="row">
        <div class="col-sm-12">
            <div class="page-title-box">
                <div class="btn-group pull-right">
                    <ol class="breadcrumb hide-phone p-0 m-0">
                        <li class="breadcrumb-item">
                            <a href="<c:url value="/admin/home.html"/>"><fmt:message key="label.home"/></a></li>
                        <li class="breadcrumb-item active"><fmt:message key="label.product.management"/></li>
                    </ol>
                </div>
                <h3 class="page-title"><fmt:message key="label.product.management"/></h3>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-12">
            <div class="card mb-5">
                <div class="card-body">
                    <%--search--%>
                    <div class="search-filter">
                        <div class="form-group row">
                            <!--Product code-->
                            <label class="col-sm-6 col-md-1 col-form-label"><fmt:message key="label.product.code"/></label>
                            <div class="col-sm-6 col-md-3">
                                <input id="productCodeSearchInput" class="form-control" type="search"/>
                            </div>
                            <!--Product name-->
                            <label class="col-sm-6 col-md-1 col-form-label"><fmt:message key="label.product.name"/></label>
                            <div class="col-sm-6 col-md-3">
                                <input id="productNameSearchInput" class="form-control" type="search"/>
                            </div>
                            <!--Category-->
                            <label class="col-sm-6 col-md-1 col-form-label"><fmt:message key="label.catgroup.name"/></label>
                            <div class="col-sm-6 col-md-3">
                                <input id="categorySearchInput" class="form-control" type="search"/>
                            </div>
                        </div>
                        <!--status-->
                        <div class="form-group row">
                            <label class="col-md-1 col-form-label"><fmt:message key="label.status"/></label>
                            <div class="col-5 form-inline">
                                <label><input type="radio" name="status" checked>&nbsp;<fmt:message key="label.all"/></label>&nbsp;&nbsp;&nbsp;
                                <label><input type="radio" name="status">&nbsp;<fmt:message key="label.active"/></label>&nbsp;&nbsp;&nbsp;
                                <label><input type="radio" name="status">&nbsp;<fmt:message key="label.deactive"/></label>&nbsp;&nbsp;&nbsp;
                            </div>
                        </div>
                        <div class="form-group text-center">
                            <button id="btnSearch" class="btn btn-sm hg btn-primary waves-effect waves-light text-center ml-2">
                                <i class="fa fa-search"></i> <fmt:message key="label.search"/>
                            </button>
                            <button id="btnDeleteSearch" class="btn btn-sm hg btn-warning waves-effect waves-light text-center ml-2">
                                <i class="fa fa-search"></i> <fmt:message key="label.delete.search"/>
                            </button>
                        </div>
                    </div>
                    <!--Button action-->
                    <div class="row">
                        <div class="col-12 text-right">
                            <!--Add product-->
                            <button class="btn btn-sm btn-primary waves-effect waves-light">
                                <i class="fa fa-plus"></i>
                                &nbsp;<fmt:message key="label.button.add.product"/>
                            </button>&nbsp;
                            <!--Import product-->
                            <button class="btn btn-sm btn-primary waves-effect waves-light">
                                <i class="fa fa-file"></i>
                                &nbsp;<fmt:message key="label.button.import.product"/>
                            </button>&nbsp;
                            <!--Export product-->
                            <button class="btn btn-sm btn-primary waves-effect waves-light">
                                <i class="fa fa-file"></i>
                                &nbsp;<fmt:message key="label.button.export.product"/>
                            </button>&nbsp;
                            <!--Active product-->
                            <button class="btn btn-sm btn-success waves-effect waves-light">
                                <i class="fa fa-check"></i>
                                &nbsp;<fmt:message key="label.button.active"/>
                            </button>&nbsp;
                            <!--Deactive product-->
                            <button class="btn btn-sm btn-danger waves-effect waves-light">
                                <i class="fa fa-ban"></i>
                                &nbsp;<fmt:message key="label.button.deactive"/>
                            </button>
                        </div>
                    </div>
                    <!--table-->
                    <form:form modelAttribute="items" action="${formListUrl}" id="listForm">
                        <div class="dataTables_wrapper no-footer" id="datatable-buttons_wrapper">
                            <div class="row">
                                <div class="col-sm-12">
                                    <display:table name="items.listResult" cellspacing="0" cellpadding="0"
                                                   requestURI="${formListUrl}" defaultsort="8" defaultorder="descending"
                                                   partialList="true" sort="external" size="${items.totalItems}"
                                                   id="tableList" excludedParams="checkList"
                                                   pagesize="${items.maxPageItems}" export="false"
                                                   class="table table-striped table-bordered dataTable no-footer">
                                        <display:column title="" class="text-center table_menu_items"
                                                        headerClass="white_text text-center" style="width:2%" >
                                            <fieldset class='custom-control text-center'>
                                                <input type="checkbox" name="checkList" id="checkbox${tableList_rowNum}">
                                                <label for="checkbox${tableList_rowNum}" class="">&nbsp;</label>
                                            </fieldset>
                                        </display:column>
                                        <display:column headerClass="sorting" class="text-center"
                                                        property="catGroup.name"
                                                        sortName="catGroup.name"
                                                        sortable="true" titleKey="label.catgroup.name"/>
                                        <display:column headerClass="sorting" class="text-center"
                                                        property="code"
                                                        sortName="code"
                                                        sortable="true" titleKey="label.product.code"/>
                                        <display:column headerClass="sorting"
                                                        property="name"
                                                        sortName="name"
                                                        sortable="true" titleKey="label.product.name"/>
                                        <display:column class="text-right"
                                                        sortable="false" titleKey="label.price">
                                            <fmt:formatNumber value="${tableList.referencePrice.lowestPrice}"/>
                                            <c:if test="${tableList.referencePrice.highestPrice != null}">
                                                &nbsp;~&nbsp;<fmt:formatNumber value="${tableList.referencePrice.highestPrice}"/>
                                            </c:if>
                                        </display:column>
                                        <display:column class="text-center" headerClass="sorting text-center"
                                                        sortable="true" titleKey="label.status" sortName="status">
                                            <c:choose>
                                                <c:when test="${tableList.status}">
                                                    <span class="status-active text-success">•</span><fmt:message key="label.active"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="status-active text-danger">•</span><fmt:message key="label.deactive"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </display:column>
                                        <display:column titleKey="label.views" class="text-center">
                                            0
                                        </display:column>
                                        <display:column headerClass="sorting" sortable="true" class="text-center"
                                                        sortName="createdDate" titleKey="label.created-date">
                                            <fmt:formatDate value="${tableList.createdDate}" pattern="dd/MM/yyyy"/>
                                        </display:column>
                                        <display:column titleKey="label.image" class="text-center">
                                            <div class="zoom"><img src="${tableList.image}"></div>
                                        </display:column>
                                        <display:column class="text-center" titleKey="label.action">
                                            <a href="${editUrl}" class="teal-text" data-toggle="tooltip"
                                               data-placement="top" title="<fmt:message key="label.edit"/>">
                                                <i class="fa fa-edit" aria-hidden="true"></i>
                                            </a>
                                            <i class="separator"></i>
                                            <a href="#" class="red-text removeProduct" data-toggle="tooltip"
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