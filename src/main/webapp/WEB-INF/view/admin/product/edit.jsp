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
                                <form:input path="pojo.code" id="productCode" cssClass="form-control col-sm-12" readonly="true"/>
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
                                    <input type="radio" name="pojo.status" value="true" <c:if test="${item.pojo.status == true || item.pojo.status == null}">checked</c:if>>
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
<%--                                    <div class="preview">--%>
<%--                                        <c:if test="${empty item.pojo.image}">--%>
<%--                                            <br>--%>
<%--                                            <img id="productImage" class="img-fluid imageSizeThumb" src="<c:url value="/img/default-placeholder.png"/>">--%>
<%--                                        </c:if>--%>
<%--                                    </div>--%>
                                </div>
                                <br>
                                <c:choose>
                                    <c:when test="${item.pojo.image != null}">
                                        <a href="<c:url value="${item.pojo.image}"/>" target="_blank">
                                            <img id="productImage" class="img-fluid imageSizeThumb" src="<c:url value="${item.pojo.image}"/>" onerror="this.error;this.src='<c:url value="/img/default-placeholder.png"/>'">
                                        </a>
                                        <input type="hidden" name="pojo.image" value="${item.pojo.image}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <img id="productImage" class="img-fluid imageSizeThumb" src="<c:url value="/img/default-placeholder.png"/>">
                                    </c:otherwise>
                                </c:choose>
                                <form:errors path="pojo.image" cssClass="error"/>
                            </div>
                        </div>

                        <%--sku--%>
                        <div class="form-group row required">
                            <label class="col-sm-2 col-form-label"><fmt:message key="label.sku"/></label>
                            <div class="col-sm-10">
                                <table class="table text-center" id="tableProductSku">
                                    <thead>
                                        <tr>
                                            <td style="width: 20%"><fmt:message key="label.image"/></td>
                                            <td style="width: 30%"><fmt:message key="label.sku.name"/></td>
                                            <td style="width: 20%"><fmt:message key="label.sku.code"/></td>
                                            <td style="width: 20%"><fmt:message key="label.status"/> </td>
                                            <td style="width: 10%"><fmt:message key="label.action"/></td>
                                        </tr>
                                    </thead>
                                    <tbody></tbody>
                                </table>
                                <div class="sku-item">
                                    <c:forEach items="${item.pojo.sku}" var="sku" varStatus="stt">
                                        <div class="table-sku">
                                            <table class="table text-center">
                                                <tbody>
                                                    <tr>
                                                        <td style="width: 20%">
                                                            <div>
                                                                <img src="${sku.image}" class="img-fluid imageSizeThumb" onerror="this.error;this.src='<c:url value="/img/default-placeholder.png"/>'"/>
                                                            </div>
                                                        </td>
                                                        <td style="width: 30%">
                                                            <input class="form-control" type="text" value="${sku.title}" <fmt:message key="label.product.sku.input.place-holder"/>>
                                                        </td>
                                                        <td style="width: 20%">
                                                            <input class="form-control" type="text" value="${sku.skuCode}" readonly>
                                                        </td>
                                                        <td style="width: 20%">
                                                            <label>
                                                                <input type="radio" name="pojo.sku[${stt.index}].status" value="true" <c:if test="${item.pojo.sku[stt.index].status == true}">checked</c:if>>
                                                                &nbsp;<fmt:message key="label.active"/>
                                                            </label>&nbsp;&nbsp;&nbsp;
                                                            <label>
                                                                <input type="radio" name="pojo.sku[${stt.index}].status" value="false" <c:if test="${item.pojo.sku[stt.index].status == false}">checked</c:if>>
                                                                &nbsp;<fmt:message key="label.deactive"/>
                                                            </label>&nbsp;&nbsp;&nbsp;
                                                        </td>
                                                        <td>
                                                            <%-- delete --%>
                                                            <a href="javascript:void(0)" class="red-text btnDeleteSku" data-toggle="tooltip"
                                                               data-placement="top" title="<fmt:message key="label.delete"/>">
                                                                <i class="fa fa-trash-o" aria-hidden="true"></i>
                                                            </a>&nbsp;
                                                            <i class="separator"></i>&nbsp;
                                                            <%-- add --%>
                                                            <a href="javascript:void(0)"  class="text-primary btnAddSkuDimension"
                                                               data-toggle="tooltip" data-placement="top" title="<fmt:message key="label.add.product-dimension"/>">
                                                                <i class="fa fa-plus" aria-hidden="true"></i>&nbsp;
                                                            </a>
                                                        </td>

                                                    </tr>
                                                </tbody>
                                            </table>
                                            <table class="table table-bordered text-center table-sku-dimension">
                                                <thead>
                                                    <tr>
                                                        <td style="width: 5%">STT</td>
                                                        <td style="width: 15%"><fmt:message key="label.product.dimension.code"/></td>
                                                        <td style="width: 10%"><fmt:message key="label.size"/></td>
                                                        <td style="width: 10%"><fmt:message key="label.width"/></td>
                                                        <td style="width: 10%"><fmt:message key="label.height"/></td>
                                                        <td style="width: 15%" class="text-right"><fmt:message key="label.original-price"/></td>
                                                        <td style="width: 15%" class="text-right"><fmt:message key="label.sale-price"/></td>
                                                        <td style="width: 10%"><fmt:message key="label.action"/></td>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${sku.skuDimensionDTOs}" var="skuDimension" varStatus="stt2">
                                                        <tr>
                                                            <td>${stt2.index + 1}</td>
                                                                <%-- Dimension code --%>
                                                            <td>
                                                                <input type="text" class="form-control" value="${skuDimension.code}" name="pojo.sku[${stt.index}].skuDimensionDTOs[${stt2.index}].code" readonly>
                                                            </td>
                                                                <%-- Size --%>
                                                            <td>
                                                                <input type="text" class="form-control" value="${skuDimension.size}" name="pojo.sku[${stt.index}].skuDimensionDTOs[${stt2.index}].size">
                                                            </td>
                                                                <%-- width --%>
                                                            <td>
                                                                <input type="text" class="form-control" value="${skuDimension.width}" name="pojo.sku[${stt.index}].skuDimensionDTOs[${stt2.index}].width">
                                                            </td>
                                                                <%-- height --%>
                                                            <td>
                                                                <input type="text" class="form-control" value="${skuDimension.height}" name="pojo.sku[${stt.index}].skuDimensionDTOs[${stt2.index}].height">
                                                            </td>
                                                                <%-- original price --%>
                                                            <td>
                                                                <input type="text" class="form-control" value="<fmt:formatNumber value="${skuDimension.originalPrice}"/>" name="pojo.sku[${stt.index}].skuDimensionDTOs[${stt2.index}].originalPrice">
                                                            </td>
                                                                <%-- sale price --%>
                                                            <td>
                                                                <input type="text" class="form-control" value="<fmt:formatNumber value="${skuDimension.salePrice}"/>" name="pojo.sku[${stt.index}].skuDimensionDTOs[${stt2.index}].salePrice">
                                                            </td>
                                                                <%-- action --%>
                                                            <td>
                                                                <%-- delete --%>
                                                                <a href="javascript:void(0)" class="red-text" data-toggle="tooltip"
                                                                   data-placement="top" title="<fmt:message key="label.delete"/>">
                                                                    <i class="fa fa-trash-o" aria-hidden="true"></i>
                                                                </a>&nbsp;
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-sm-2"></div>
                            <div class="col-sm-10">
                                <a class="btn btn-primary" href="javascript:addSku()"><fmt:message key="label.button.add.sku"/></a>
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

<div>
    <input type="hidden" id="labelActive" value="<fmt:message key="label.active"/>"/>
    <input type="hidden" id="labelDeactive" value="<fmt:message key="label.deactive"/>"/>
    <input type="hidden" id="labelDelete" value="<fmt:message key="label.delete"/>"/>
    <input type="hidden" id="labelAddDimension" value="<fmt:message key="label.add.product-dimension"/>"/>
    <input type="hidden" id="labelInputColorProduct" value="<fmt:message key="label.product.sku.input.place-holder"/>"/>
    <input type="hidden" id="defaultImage" value="<c:url value="/img/default-placeholder.png"/>"/>
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
        });

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
        });
        bindEventDeleteSku();
        bindEventAddSkuDimension();
    }

    function addSku() {
        var countSku = $(".table-sku").length;
        if(!countSku) {
            countSku = 0;
        }
        let item = " <div class='table-sku'>" +
            "           <table class='table text-center'>" +
            "               <tr>" +
            "                   <td style='width: 20%;'>" +
            "                       <div>" +
            "                           <img src='"+ $("#defaultImage").val() +"' alt='image' class='img-fluid imageSizeThumb' />" +
            "                       </div>" +
            "                   </td>" +
            "                   <td style='width: 30%;'>" +
            "                       <input class='form-control' type='text' placeholder='"+ $("#labelInputColorProduct").val() +"'>" +
            "                   </td>" +
            "                   <td style='width: 20%;'>" +
            "                       <input class='form-control' type='text' readonly>" +
            "                   </td>" +
            "                   <td style='width: 20%;'>" +
            "                   <label>" +
            "                      <input type='radio' value='true' name='pojo.sku["+ countSku +"].status' checked>&nbsp;"  + $("#labelActive").val() +
            "                    </label>&nbsp;&nbsp;&nbsp;" +
            "                    <label>" +
            "                       <input type='radio' value='false' name='pojo.sku["+ countSku +"].status'>&nbsp;" + $("#labelDeactive").val() +
            "                     </label>&nbsp;&nbsp;&nbsp;" +
            "                   </td>" +
            "                   <td style='width: 10%;'>" +
            "                       <a href='javascript:void(0)' class='red-text btnDeleteSku' data-toggle='tooltip'" +
            "                           data-placement='top' title='"+ $("#labelDelete").val() +"'>" +
            "                               <i class='fa fa-trash-o' aria-hidden='true'></i>" +
            "                       </a>&nbsp;" +
            "                       <i class='separator'></i>&nbsp;" +
            "                       <a href='javascript:void(0)' class='text-primary btnAddSkuDimension' data-toggle='tooltip'" +
            "                           data-placement='top' title='"+ $("#labelAddDimension").val() +"'>" +
            "                           <i class='fa fa-plus' aria-hidden='true'></i>" +
            "                     </a>&nbsp;" +
            "                   </td>" +
            "               </tr>" +
            "           </table>" +
            "       </div>";

        $(".sku-item").append(item);
        bindEventDeleteSku();
        bindEventAddSkuDimension();
    }

    function bindEventAddSkuDimension() {
        $(".btnAddSkuDimension").click(function (e) {
            addSkuDimension(e);
        })
    }

    function bindEventDeleteSku() {
        $(".btnDeleteSku").unbind("click").on("click", function (e) {
            e.preventDefault();
            deleteSku(e);
        })
    }

    function deleteSku(e) {
        bootbox.confirm("<fmt:message key="label.confirm.delete"/>", function (yes) {
            if(yes) {
                console.log(e);
                e.target.closest(".table-sku").remove();
            }
        })
    }

    function addSkuDimension(e) {
        console.log("hit");
        let item = "<tr>" +
            "           <td>" + 1 + "</td>" +
            "           <td>" +
            "                <input type='text' class='form-control' name='' readonly>" +
            "           </td>" +
            "           <td>" +
            "                <input type='text' class='form-control' name=''" +
            "           </td>" +
            "           <td>" +
            "                <input type='text' class='form-control' name=''" +
            "           </td>" +
            "           <td>" +
            "               <input type='text' class='form-control' name=''" +
            "           </td>" +
            "           <td>" +
            "               <input type='text' class='form-control' name=''" +
            "           </td>" +
            "           <td>" +
            "               <input type='text' class='form-control' name=''" +
            "           </td>" +
            "           <td>" +
            "               <a href='javascript:void(0)' class='red-text' data-toggle='tooltip'" +
            "                  data-placement='top' title='"+ $("#labelDelete").val() +"'>" +
            "                    <i class='fa fa-trash-o' aria-hidden='true'></i>" +
            "               </a>&nbsp;" +
            "           </td>" +
            "       </tr>";
        let table_sku = e.target.closest(".table-sku");
        let table_sku_dimension = $(table_sku).find("div.table-sku-dimension");
        if(!table_sku_dimension) {
            let tableTemp = "<table class='table table-bordered text-center table-sku-dimension'>" +
                "         <thead>" +
                "               <tr>" +
                "                   <td style='width: 5%'>STT</td>" +
                "                   <td style='width: 15%'>code</td>" +
                "                   <td style='width: 10%'>size</td>" +
                "                   <td style='width: 10%'>width</td>" +
                "                   <td style='width: 10%'>height</td>" +
                "                   <td style='width: 15%' class='text-right'>original-price</td>" +
                "                   <td style='width: 15%' class='text-right'>sale price</td>" +
                "                   <td style='width: 10%'>action</td>" +
                "                </tr>" +
                "         </thead>" +
                "         <tbody></tbody>" +
                "</table>";

            table_sku_dimension = $(table_sku).append(tableTemp);
        }
        $(table_sku_dimension).append(item);
    }

</script>