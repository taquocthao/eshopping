<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../common/taglib.jsp"%>
<%@ page trimDirectiveWhitespaces="true" %>

<c:url var="formListUrl" value="/admin/catgroup/list.html"/>
<c:url var="catGroupEditUrl" value="/admin/catgroup/edit.html"/>
<c:url var="ajaxActiveCatGroupUrl" value="/ajax/admin/catgroup/active.html"/>
<c:url var="ajaxExportCatGroupUrl" value="/ajax/admin/catgroup/export.html"/>

<div class="container-fluid">
    <!-- Page-Title -->
    <div class="row">
        <div class="col-sm-12">
            <div class="page-title-box">
                <div class="btn-group pull-right">
                    <ol class="breadcrumb hide-phone p-0 m-0">
                        <li class="breadcrumb-item">
                            <a href="<c:url value="/admin/home.html"/>"><fmt:message key="label.home"/></a></li>
                        <li class="breadcrumb-item active"><fmt:message key="label.catgroup.management"/></li>
                    </ol>
                </div>
                <h3 class="page-title"><fmt:message key="label.catgroup.management"/></h3>
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
                        <div class="search-filter">
                            <div class="form-group row">
                                <!--Catgroup code-->
                                <label class="col-sm-6 col-md-1 col-form-label"><fmt:message key="label.catgroup.code"/></label>
                                <div class="col-sm-6 col-md-3">
                                    <input id="productCodeSearchInput" class="form-control" name="pojo.code" type="search" value="${items.pojo.code}"/>
                                </div>
                                <!--catgroup name-->
                                <label class="col-sm-6 col-md-1 col-form-label"><fmt:message key="label.catgroup.name"/></label>
                                <div class="col-sm-6 col-md-3">
                                    <input id="productNameSearchInput" class="form-control" name="pojo.name" type="search" value="${items.pojo.name}"/>
                                </div>
                            </div>
                            <!--status-->
                            <div class="form-group row">
                                <label class="col-md-1 col-form-label"><fmt:message key="label.status"/></label>
                                <div class="col-5 form-inline">
                                    <label><input type="radio" name="pojo.status" value="" <c:if test="${items.pojo.status == null}">checked</c:if>>&nbsp;<fmt:message key="label.all"/></label>&nbsp;&nbsp;&nbsp;
                                    <label><input type="radio" name="pojo.status" value="true" <c:if test="${items.pojo.status == true}">checked</c:if>>&nbsp;<fmt:message key="label.active"/></label>&nbsp;&nbsp;&nbsp;
                                    <label><input type="radio" name="pojo.status" value="false" <c:if test="${items.pojo.status == false}">checked</c:if>>&nbsp;<fmt:message key="label.deactive"/></label>&nbsp;&nbsp;&nbsp;
                                </div>
                            </div>
                            <!--button search-->
                            <div class="form-group text-center">
                                <a id="btnSearch" href="#" class="btn btn-sm hg btn-primary waves-effect waves-light text-center ml-2">
                                    <i class="fa fa-search"></i><fmt:message key="label.search"/>
                                </a>
                            </div>
                        </div>
                        <!--Button action-->
                        <div class="row">
                            <div class="col-12 text-right">
                                <!--Add catgroup-->
                                <a class="btn btn-sm btn-primary waves-effect waves-light" id="btnAddCatGroup" href="${catGroupEditUrl}">
                                    <i class="fa fa-plus"></i>
                                    &nbsp;<fmt:message key="label.button.add.catgroup"/>
                                </a>&nbsp;
                                <!--Import catgroup-->
                                <button class="btn btn-sm btn-primary waves-effect waves-light">
                                    <i class="fa fa-file"></i>
                                    &nbsp;<fmt:message key="label.button.import.catgroup"/>
                                </button>&nbsp;
                                <!--Export catgroup-->
                                <button class="btn btn-sm btn-primary waves-effect waves-light" id="btnExport">
                                    <i class="fa fa-file"></i>
                                    &nbsp;<fmt:message key="label.button.export.catgroup"/>
                                </button>&nbsp;
                                <!--Active catgroup-->
                                <button class="btn btn-sm btn-success waves-effect waves-light" id="btnActive">
                                    <i class="fa fa-check"></i>
                                    &nbsp;<fmt:message key="label.button.active"/>
                                </button>&nbsp;
                                <!--Deactive catgroup-->
                                <button class="btn btn-sm btn-danger waves-effect waves-light" id="btnDeActive">
                                    <i class="fa fa-ban"></i>
                                    &nbsp;<fmt:message key="label.button.deactive"/>
                                </button>
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
                                                       value="${tableList.catGroupId}" class="checkbox_user custom-control-input">
                                                <label for="checkbox${tableList_rowNum}" class="custom-control-label">&nbsp;</label>
                                            </fieldset>
                                        </display:column>

                                        <%--code--%>
                                        <display:column headerClass="sorting" class="text-center"
                                                        property="code"
                                                        sortName="code"
                                                        sortable="true" titleKey="label.catgroup.code"/>
                                        <%--name--%>
                                        <display:column class="text-center"
                                                        property="name"
                                                        sortName="name"
                                                        sortable="true" titleKey="label.catgroup.name"/>
                                        <%--parent--%>
                                        <display:column property="parent.name"
                                                        class="text-center"
                                                        titleKey="label.catgroup.parent.name"/>

                                        <%--status--%>
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

                                        <%--created date--%>
                                        <display:column headerClass="sorting" sortable="true" class="text-center"
                                                        sortName="createdDate" titleKey="label.created-date">
                                            <fmt:formatDate value="${tableList.createdDate}" pattern="dd/MM/yyyy"/>
                                        </display:column>

                                        <%--image--%>
                                        <display:column titleKey="label.image" class="text-center">
                                            <div class="zoom"><img src="${tableList.image}"></div>
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
<script>
    $(document).ready(function () {
        // reset crudaction
        $("#crudaction").val('');

        // event search
        $("#btnSearch").click(function (e) {
            e.preventDefault();
            $("#listForm").submit();
        });

        // event active
        $("#btnActive").click(function (e) {
            e.preventDefault();
            let checkedBox = $("input[name='checkList']:checked");
            if(checkedBox.length <= 0) {
                bootbox.alert("<fmt:message key="message.warning.choose.catgroup"/>");
                return;
            }
            bootbox.confirm('<fmt:message key="message.confirm.active"/>', function (ok) {
                if(ok) {
                    $("#crudaction").val("active");
                    $("#listForm").submit();
                }
            });

        });

        // event deActive
        $("#btnDeActive").click(function (e) {
            e.preventDefault();
            let checkedBox = $("input[name='checkList']:checked");
            if(checkedBox.length <= 0) {
                bootbox.alert("<fmt:message key="message.warning.choose.catgroup"/>");
                return;
            }
            bootbox.confirm('<fmt:message key="message.confirm.deactive"/>', function (ok) {
                if(ok) {
                    $("#crudaction").val("deactive");
                    $("#listForm").submit();
                }
            });

        });

        $('#btnExport').click(function (e) {
            e.preventDefault();
            showSpinner()
            $.ajax({
                url: '${ajaxExportCatGroupUrl}',
                method: 'POST',
                data: $('#listForm').serialize(),
            }).done(function (result) {
                hideSpinner();
                console.log(result);
                window.location.replace = result;
            })
        });

    })
</script>