<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:url var="productEditUrl" value="/admin/product/edit.html"/>
<c:url var="productListURL" value="/admin/product.html"/>

<div class="container-fluid">
    <form:form action="${productEditUrl}" modelAttribute="item" enctype="multipart/form-data">
        <div class="col-sm-12">
            <!-- Page-Title -->
            <div class="page-title-box">
                <div class="btn-group pull-right">
                    <ol class="breadcrumb hide-phone p-0 m-0">
                        <li class="breadcrumb-item">
                            <a href="<c:url value="/admin/home.html"/>"><fmt:message key="label.home"/></a></li>
                        <li class="breadcrumb-item">
                            <a href="<c:url value="/admin/product.html"/>"><fmt:message key="label.product.management"/></a>
                        </li>
                        <li class="breadcrumb-item active">
                            <c:choose>
                                <c:when test="${crudaction eq 'edit'}">
                                    <fmt:message key="label.product.edit"/>
                                </c:when>
                                <c:otherwise> <fmt:message key="label.product.add"/></c:otherwise>
                            </c:choose>
                        </li>
                    </ol>
                </div>
                <h3 class="page-title">
                    <c:choose>
                        <c:when test="${crudaction eq 'edit'}">
                            <fmt:message key="label.product.edit"/>
                        </c:when>
                        <c:otherwise> <fmt:message key="label.product.add"/></c:otherwise>
                    </c:choose>
                </h3>
            </div>
        </div>

        <div class="row">
            <div class="col-12">
                <div class="card mb-5">
                    <div class="card-body">

                        <div class="form-group row required">
                            <label for="productCode" class="col-sm-2 col-form-label"><fmt:message key="label.catgroup.name"/></label>
                            <div class="col-sm-10">
                                <form:select path="pojo.catGroup.code" cssClass="selectpicker">
                                    <form:option value=""><fmt:message key="label.choose.catgroup"/></form:option>
                                    <form:options items="${catGroups}" itemLabel="name" itemValue="code"></form:options>
                                </form:select>
                            </div>
                        </div>

                            <%--product code--%>
                        <div class="form-group row">
                            <label for="productCode" class="col-sm-2 col-form-label"><fmt:message key="label.product.code"/></label>
                            <div class="col-sm-2 no-padding-right">
                                <form:input path="pojo.code" id="productCode" cssClass="form-control col-sm-12"/>
                            </div>
                        </div>

                            <%--product name--%>
                        <div class="form-group row required">
                            <label for="productName" class="col-sm-2 col-form-label"><fmt:message key="label.product.name"/></label>
                            <div class="col-sm-5 no-padding-right">
                                <form:input path="pojo.name" id="productName" cssClass="form-control col-sm-12"/>
                                <form:errors path="pojo.name" cssClass="error"/>
                            </div>
                        </div>

                            <%--status--%>
                        <div class="form-group row ">
                            <label class="col-sm-2 col-form-label"><fmt:message key="label.status"/></label>
                            <div class="col-sm-10 col-form-label">
                                <label>
                                    <input type="radio" name="pojo.status" value="true" <c:if test="${item.pojo.status == true}">checked</c:if>>
                                    &nbsp;<fmt:message key="label.active"/>
                                </label>&nbsp;&nbsp;&nbsp;
                                <label>
                                    <input type="radio" name="pojo.status" value="false" <c:if test="${item.pojo.status == false}">checked</c:if>>
                                    &nbsp;<fmt:message key="label.deactive"/>
                                </label>&nbsp;&nbsp;&nbsp;
                            </div>
                        </div>

                            <%--image--%>
                        <div class="form-group row required">
                            <label class="col-sm-2 col-form-label"><fmt:message key="label.image"/></label>
                            <div class="col-sm-10">
                                <div>
                                    <a type="button" class="btn btn-primary" id="buttonUploadImage">
                                        <fmt:message key="label.choose.image"/>
                                    </a>
                                    <input style="opacity: 0; max-width: 0px;" type="file" id="buttonUploadHidden" class="hidden" accept="image/*"/>
                                    <form:hidden path="pojo.image"/>
                                    <div class="preview">
                                        <c:if test="${empty item.pojo.image}">
                                            <br>
                                            <img id="productImage" class="img-fluid imageSizeThumb" src="<c:url value="/img/default-placeholder.png"/>">
                                        </c:if>
                                    </div>
                                </div>
                                <br>
                                <c:choose>
                                    <c:when test="${item.pojo.image != null}">
                                        <a href="<c:url value="${item.pojo.image}"/>" target="_blank">
                                            <img id="productImage" class="img-fluid imageSizeThumb" src="<c:url value="${item.pojo.image}"/> ">
                                        </a>
                                        <input type="hidden" name="pojo.image" value="${item.pojo.image}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <img id="productImage" class="img-fluid imageSizeThumb" src="<c:url value="/img/default-placeholder.png"/>" style ="display: none">
                                    </c:otherwise>
                                </c:choose>
                                <form:errors path="pojo.image" cssClass="error"/>
                            </div>
                        </div>

                        <%--sku--%>
                        <div class="form-group row">
                            <label class="col-sm-2 col-form-label"><fmt:message key="label.sku"/></label>
                            <div class="col-sm-10">
                                <table class="table table-striped text-center">
                                    <thead>
                                        <tr>
                                            <td><fmt:message key="label.image"/></td>
                                            <td><fmt:message key="label.sku.name"/></td>
                                            <td><fmt:message key="label.sku.code"/></td>
                                            <td><fmt:message key="label.status"/> </td>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${item.pojo.sku}" var="sku" varStatus="stt">
                                            <tr>
                                                <td>
                                                    <div>
                                                        <img src="${sku.image}" class="img-fluid imageSizeThumb"/>
                                                    </div>
                                                </td>
                                                <td>
                                                    <input class="form-control" type="text" value="${sku.title}">
                                                </td>
                                                <td>
                                                    <input class="form-control" type="text" value="${sku.skuCode}">
                                                </td>
                                                <td>
                                                    <label>
                                                        <input type="radio" name="pojo.sku[${stt.index}].status" value="true" <c:if test="${item.pojo.sku[stt.index].status == true}">checked</c:if>>
                                                        &nbsp;<fmt:message key="label.active"/>
                                                    </label>&nbsp;&nbsp;&nbsp;
                                                    <label>
                                                        <input type="radio" name="pojo.sku[${stt.index}].status" value="false" <c:if test="${item.pojo.sku[stt.index].status == false}">checked</c:if>>
                                                        &nbsp;<fmt:message key="label.deactive"/>
                                                    </label>&nbsp;&nbsp;&nbsp;
                                                </td>
                                                <table class="table table-bordered text-center">
                                                    <thead>
                                                        <tr>
                                                            <td>code</td>
                                                            <td>size</td>
                                                            <td>width</td>
                                                            <td>height</td>
                                                            <td>original price</td>
                                                            <td>sale price</td>
                                                            <td>active</td>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach items="${sku.skuDimensionDTOs}" var="skuDimension" varStatus="stt2">
                                                            <tr>
                                                                <td>
                                                                    <input type="text" class="form-control" value="${skuDimension.code}" name="pojo.sku[${stt.index}].skuDimensionDTOs[${stt2.index}].code">
                                                                </td>
                                                                <td>
                                                                    <input type="text" class="form-control" value="${skuDimension.size}" name="pojo.sku[${stt.index}].skuDimensionDTOs[${stt2.index}].size">
                                                                </td>
                                                                <td>
                                                                    <input type="text" class="form-control" value="${skuDimension.width}" name="pojo.sku[${stt.index}].skuDimensionDTOs[${stt2.index}].width">
                                                                </td>
                                                                <td>
                                                                    <input type="text" class="form-control" value="${skuDimension.height}" name="pojo.sku[${stt.index}].skuDimensionDTOs[${stt2.index}].height">
                                                                </td>
                                                                <td>
                                                                    <input type="text" class="form-control" value="<fmt:formatNumber value="${skuDimension.originalPrice}"/>" name="pojo.sku[${stt.index}].skuDimensionDTOs[${stt2.index}].originalPrice">
                                                                </td>
                                                                <td>
                                                                    <input type="text" class="form-control" value="<fmt:formatNumber value="${skuDimension.salePrice}"/>" name="pojo.sku[${stt.index}].skuDimensionDTOs[${stt2.index}].salePrice">
                                                                </td>
                                                                <td>
                                                                    ${skuDimension.active}
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
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
                                <a class="btn btn-danger" href="${productListURL}">
                                    <i class="fa fa-arrow-circle-left" aria-hidden="true"></i>&nbsp;
                                    <fmt:message key="label.cancel"/>
                                </a>
                                <a id="btnSave" class="btn btn-primary text-white">
                                    <i class="fa fa-plus" aria-hidden="true"></i>&nbsp;
                                    <fmt:message key="label.save"/>
                                </a>
                            </div>
                        </div>

                        <input type="hidden" name="crudaction" id="crudaction"/>
                    </div>
                </div>
            </div>
        </div>
    </form:form>
</div>

<script>
    $(document).ready(function () {
        initRichTextEditor();
        bindEventButtons();
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

    function bindEventButtons() {
        $("#buttonUploadImage").on("click", function (e) {
            $("#buttonUploadHidden").click();
        })

        $("#buttonUploadHidden").on("change", function (ev) {
            ev.stopPropagation();
            ev.preventDefault();
            var fileElements = ev.target.files;
            if(fileElements && fileElements[0] && FileReader) {
                var reader = new FileReader();
                reader.onload = function (progressEvent) {
                    $("#productImage").attr("src", progressEvent.target.result).css("display", "initial");
                }
                reader.onerror = function(errorEvent) {

                }
                reader.readAsDataURL(fileElements[0]);
            }
        })
    }

</script>