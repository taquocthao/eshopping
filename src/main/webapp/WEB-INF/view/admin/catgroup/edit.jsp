<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:url var="catGroupEditUrl" value="/admin/catgroup/edit.html"/>
<c:url var="catGroupListURL" value="/admin/catgroup/list.html"/>

<div class="container-fluid">
    <form:form action="${catGroupEditUrl}" id="catGroupForm" modelAttribute="item" method="post">
        <div class="col-sm-12">
            <!-- Page-Title -->
            <div class="page-title-box">
                <div class="btn-group pull-right">
                    <ol class="breadcrumb hide-phone p-0 m-0">
                        <li class="breadcrumb-item">
                            <a href="<c:url value="/admin/home.html"/>"><fmt:message key="label.home"/></a></li>
                        <li class="breadcrumb-item">
                            <a href="${catGroupListURL}"><fmt:message key="label.catgroup.management"/></a>
                        </li>
                        <li class="breadcrumb-item active">
                            <c:choose>
                                <c:when test="${item.pojo != null && item.pojo.code != null}">
                                    <fmt:message key="label.catgroup.edit"/>
                                </c:when>
                                <c:otherwise> <fmt:message key="label.catgroup.add"/></c:otherwise>
                            </c:choose>
                        </li>
                    </ol>
                </div>
                <h3 class="page-title">
                    <c:choose>
                        <c:when test="${item.pojo != null && item.pojo.code != null}">
                            <fmt:message key="label.catgroup.edit"/>
                        </c:when>
                        <c:otherwise> <fmt:message key="label.catgroup.add"/></c:otherwise>
                    </c:choose>
                </h3>
            </div>
        </div>

        <div class="row">
            <div class="col-12">
                <div class="card mb-5">
                    <div class="card-body">

                        <c:if test="${not empty messageResponse}">
                            <div class="alert alert-${alert} alert-dismissible fade show" role="alert">
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                                <strong>${messageResponse}</strong>
                            </div>
                        </c:if>

                        <%--code--%>
                        <div class="form-group row required">
                            <label for="inputCatGroupCode" class="col-sm-2 col-form-label"><fmt:message key="label.catgroup.code"/></label>
                            <div class="col-sm-3 no-padding-right">
                                <form:input path="pojo.code" id="inputCatGroupCode" type="text" cssClass="form-control"/>
                                <form:errors path="pojo.code" cssClass="error"/>
                            </div>
                        </div>

                         <%--catgroup name--%>
                        <div class="form-group row required">
                            <label for="catGroupName" class="col-sm-2 col-form-label"><fmt:message key="label.catgroup.name"/></label>
                            <div class="col-sm-3 no-padding-right">
                                <form:input path="pojo.name" id="catGroupName" cssClass="form-control"/>
                                <form:errors path="pojo.name" cssClass="error"/>
                            </div>
                        </div>

                        <%--parent name--%>
                        <div class="form-group row">
                            <label for="catGroupParentName" class="col-sm-2 col-form-label"><fmt:message key="label.catgroup.parent.name"/></label>
                            <div class="col-sm-10">
                                <form:select path="pojo.parent.code" cssClass="selectpicker" id="catGroupParentName">
                                    <form:option value=""><fmt:message key="label.choose.catgroup.parent.name"/></form:option>
                                    <form:options items="${catGroupParents}" itemLabel="name" itemValue="code"></form:options>
                                </form:select>
                            </div>
                        </div>

                        <%--status--%>
                        <div class="form-group row required">
                            <label class="col-sm-2 col-form-label"><fmt:message key="label.status"/></label>
                            <div class="col-sm-10">
                                <label>
                                    <input type="radio" name="pojo.status" value="true" <c:if test="${item.pojo.status == null || item.pojo.status == true}">checked</c:if>>
                                    &nbsp;<fmt:message key="label.active"/>
                                </label>&nbsp;&nbsp;&nbsp;
                                <label>
                                    <input type="radio" name="pojo.status" value="false" <c:if test="${item.pojo.status == false}">checked</c:if>>
                                    &nbsp;<fmt:message key="label.deactive"/>
                                </label>&nbsp;&nbsp;&nbsp;
                            </div>
                        </div>

                        <%--description--%>
                        <div class="form-group row">
                            <label for="productDescription" class="col-sm-2 col-form-label"><fmt:message key="label.description"/></label>
                            <div class="col-sm-10">
                                <form:textarea path="pojo.description" id="productDescription" cssClass="form-control"/>
                                <form:errors path="pojo.description" cssClass="error"/>
                            </div>
                        </div>

                        <%-- button save, cancel--%>
                        <div class="form-group row">
                            <div class="col-sm-2"></div>
                            <div class="col-sm-10">
                                <a class="btn btn-danger" href="${catGroupListURL}">
                                    <i class="fa fa-arrow-circle-left" aria-hidden="true"></i>&nbsp;
                                    <fmt:message key="label.cancel"/>
                                </a>
                                <a id="btnSave" class="btn btn-primary text-white">
                                    <i class="fa fa-plus" aria-hidden="true"></i>&nbsp;
                                    <fmt:message key="label.save"/>
                                </a>
                            </div>
                        </div>
                        <form:hidden path="crudaction" id="crudaction"/>
                        <form:hidden path="pojo.catGroupId"/>
                    </div>
                </div>
            </div>
        </div>
    </form:form>
</div>

<script>
    $(document).ready(function () {
        initRichTextEditor();
        $("#btnSave").on("click", function (e) {
            debugger
            e.preventDefault();
            $("#crudaction").val("insert_update");
            $("#catGroupForm").submit();
        })
    })

    function initRichTextEditor() {
        tinymce.init({
            selector: "textarea",
            height: 300,
            plugins: [
                "advlist autolink link image lists charmap print preview hr anchor pagebreak spellchecker",
                "searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking",
                "save table contextmenu directionality emoticons template paste textcolor"
            ],
            toolbar: "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image | print preview media fullpage | forecolor backcolor emoticons",
            style_formats: [
                {title: 'Bold text', inline: 'b'},
                {title: 'Red text', inline: 'span', styles: {color: '#ff0000'}},
                {title: 'Red header', block: 'h1', styles: {color: '#ff0000'}},
                {title: 'Example 1', inline: 'span', classes: 'example1'},
                {title: 'Example 2', inline: 'span', classes: 'example2'},
                {title: 'Table styles'},
                {title: 'Table row 1', selector: 'tr', classes: 'tablerow1'}
            ]
        });
    }

</script>