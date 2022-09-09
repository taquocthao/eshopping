//=========================Global variable=========================
// Message from bundle file
var messageNoProductsFound = $("#messageNoProductsFound").val();
var messageProductCustomerUnpaid = $("#messageCustomerUnpaid").val();
var messageErrorOccur = $("#messageErrorOccur").val();
var messageNoResultFound = $('#noResultFound').val();
var messageCartEmpty = $('#messageCartEmpty').val();
var messageCheckConnection = $('#messageCheckConnection').val();
var msgCannotUpdateUnitPirce = $('#msgCannotUpdateUnitPirce').val();
var messageAddCustomerSuccess = $('#messageAddCustomerSuccess').val();
var linkedWithInventory = $("#linkedWithInventory").val();
var messageNotDeliveryAddressYet = $("#messageNotDeliveryAddressYet").val();
var messageErrorTimeOut = $("#messageErrorTimeOut").val();
var messageWarningDeleteTab = $("#messageWarningDeleteTab").val();
var priceType = $("#priceType").val();

// URL
var syncInvoice2ServerUrl = $('#syncInvoice2ServerUrl').val();
var saveOrUpdateOrderOutlet2ServerUrl = $('#saveOrUpdateOrderOutlet2ServerUrl').val();
var getCustomerInforUrl = $('#getCustomerInforUrl').val();
var deleteOrderOutletUrl = $('#deleteOrderOutletUrl').val();
var addNewCustomerUrl = $('#addNewCustomerUrl').val();
var linkedWithInventoryUrl = $("#linkedWithInventoryUrl").val();
var searchProductUrl = $("#searchProductUrl").val();

// Element
var searchProductBox = $("#searchProductBox");
var searchCustomerBox = $("#searchCustomerBox");
var keysAccept = ['ArrowLeft', 'ArrowRight', 'Delete', 'Backspace', 'F1', 'F2', 'F3', 'F4', 'F5', 'F6', 'F7', 'F8', 'F9', 'F10'];

$(document).ready(function () {

    $.fn.openIndexedDB().then(() => {
        refreshPage();
        bindEvents();
    });

    setInterval(function () {
        let today = new Date();
        let dd = String(today.getDate()).padStart(2, '0');
        let mm = String(today.getMonth() + 1).padStart(2, '0');
        let yyyy = today.getFullYear();
        let hour = String(today.getHours()).padStart(2, '0');
        let minutes = String(today.getMinutes()).padStart(2, '0');
        $("#labelCurrentDate").text(dd + '/' + mm + '/' + yyyy);
        $("#labelCurrentTime").text(hour + ':' + minutes);
    }, 30000)

});

async function refreshPage() {
    // 1. navigation tab invoice list
    let orderIdOpenning = 1;
    const orderOutlets = await this.getListOrderOfOutletOffline();
    if(orderOutlets && orderOutlets.length > 0) {
        // render nav
        renderAllInvoicesNavtab(orderOutlets);
        // find open invoice;
        orderIdOpenning = $(".invoice-list-nav ul li.active a").attr('href').split("#invoice")[1];
        if(!orderIdOpenning){
            orderIdOpenning = 1;
        }
    } else {
        // create default invoice
        const orderOutletDefault = await this.createOrderOutletDefault(1);
        renderAllInvoicesNavtab(orderOutletDefault);
    }
    // 2. content page
    loadOrderContent(orderIdOpenning);
}


function getListOrderOfOutletOffline() {
    return $.fn.getAllObjectStored("OrderOutlet");
}

/**
 * create new OrderOutlet default.
 * @param invoiceId
 * @returns {Promise<OrderOutlet>} list OrderOutlet
 */
function createOrderOutletDefault( invoiceId ) {
    let orderOutletDefault =  {
                                orderOutletId : null,
                                invoiceNumber : invoiceId,
                                customerId: null,
                                totalPrice: 0,
                                totalItem: 0,
                                status: 'SAVE_AS_DRAFT',
                                code: null,
                                createdDate: Date.now(),
                                note: null,
                                loyaltyPoint: 0,
                                totalOriginalPrice : 0,
                                totalOutletDiscountPrice : 0,
                                totalPromotionDiscountPrice : 0,
                                isOpen : true
                            };
    return $.fn.saveObjectsToIndexedDB("OrderOutlet", [orderOutletDefault]);
}

function renderAllInvoicesNavtab( invoices ) {
    $(".invoice-list-nav").empty().append("<ul class=\"nav nav-tabs border-bottom-0 mt-2\" role=\"tablist\">\n" +
        "<li role=\"presentation\" id=\"navItemAdd\" class=\"nav-item\">\n" +
        "<a id=\"btnAddTab\" class=\"nav-link\" href=\"#\" aria-selected=\"false\">\n" +
        "<i class=\"fa fa-plus text-price-medium text-white\"></i>\n" +
        "</a>\n" +
        "</li>\n" +
        "</ul>");
    var btnAddInvoice = $("#navItemAdd");
    if(invoices.length > 0) {
        var count = 0;
        var countInvoices =  invoices.length;
        invoices.forEach(function (invoice, index) {
            var activeClass = "";
            if(invoice.isOpen) {
                activeClass = "active";
                count = 0;
            } else {
                count++;
            }
            if(count === countInvoices) {
                activeClass = "active";
                invoice.isOpen = true;
                $.fn.updateObjectStored("OrderOutlet", invoice);
            }
            btnAddInvoice.before(renderNavTab(invoice.invoiceNumber, activeClass));
        });
    }
    bindingEventNavTabScrolling();
    bindingEventAddTab();
}

function bindEvents() {
    bindingEventAddTab();
    bindingEventUpdateProductQuantity();
    bindingEventUpdateUnitPrice();
    bindingEventOnSearchProduct();
    bindingEventOnSearchCustomer();
    bindingEventInputFocus();
    renderButtonRemoveCustomer();
    bindingEventShowPopupAddNewCustomer();
    bindingEventOnBlurQuantityBox();
    bindingEventAcceptKeyForInput();
    onSelectDeliveryMethod();
    // initGoogleAddressSearch();
    // bindEventViewPromotions();
    // bindEventViewLoyaltyOutletEvents();
    // bindEventInputPromotionCode()
    bindEventRemoveCustomer();
    bindEventHotKey();

    $("#btnPaySubmit").on("click", function (e) {
        e.preventDefault();
        printReceipt(true);
    });

    // prevent submit form when enter key down in input
    $('form input:not([type="tel"])').keydown(function(e) {
        if (e.keyCode == 13 || e.which == 13) {
            e.preventDefault();
            return false;
        }
    });

    // focus search product box
    searchProductBox.select();
}

function bindEventHotKey() {
    $("#btnListHotKey").on("click", function (e) {
        e.preventDefault();
        $("#modalShowHotkey .modal-body").html(renderPanelHotKey());
        $("#modalShowHotkey").modal('show');
    });
}

function bindEventRemoveCustomer() {
    $("#btnRemoveCustomer").on("click", function (e) {
        e.preventDefault();
        var invoiceNumber = $(".invoice-list-nav ul li.active a").attr('href').split("#invoice")[1];
        $.fn.getOrderOutletByInoviceNumber(invoiceNumber, function (orderOutlet) {
            if(orderOutlet !== null && orderOutlet !== undefined) {
                orderOutlet.customerId = null;
                $.fn.updateOrderOutlet(orderOutlet, function () {
                    loadOrderContent(invoiceNumber, false, false);
                });
            }
        });
    });
}

function bindingEventAddTab() {
    $("#btnAddTab").on("click", function (e) {
        e.preventDefault();
        addTab();
    });
}

/**
 * Event add tab (Invoice)
 */
function addTab() {
    $("div.invoice-list-nav li.nav-item a").removeClass("active show");
    // hide currently tab
    $("div.invoice-list-nav li.nav-item").removeClass("active");
    // find the right invoice ID in nav tab
    var invoiceNumber = renderInvoiceNumber();
    addNewInvoiceOffline(invoiceNumber);
}

async function switchTab(invoiceNumber) {
    const orderOutlets = await $.fn.getAllObjectStored("OrderOutlet");
    for(const orderOutlet of orderOutlets) {
        orderOutlet.isOpen = false;
        if(invoiceNumber == orderOutlet.invoiceNumber) {
            orderOutlet.isOpen = true;
        }
        await $.fn.updateObjectStored("OrderOutlet", orderOutlet);
    }
    $("div.invoice-list-nav li.nav-item a").removeClass("active show");
    // hide currently tab
    $("div.invoice-list-nav li.nav-item").removeClass("active");
    $(`#navItem${invoiceNumber}`).addClass("active");
    $("a[href='#invoice" + invoiceNumber +"']").addClass("active show");
    loadOrderContent(invoiceNumber, false, false);
}

function bindingEventOnBlurQuantityBox() {
    $(".quantity-item-box").blur(function (e) {
        e.preventDefault();
        var quantity = e.target.value;
        if(!quantity) {
            quantity = 1;
        }
        var skuDimensionId = e.target.dataset.skudimensionid;
        var invoiceNumber = $(".invoice-list-nav ul li.active a").attr('href').split("#invoice")[1];
        updateQuantity(formatStringToNumber(quantity), skuDimensionId, invoiceNumber);
    });
}


//===========================================Private function======================================
// key-down event - for catch hot key
$(document).keydown(function (event) {
    var key = event.which || event.keyCode;
    bindingHotKeyEventKeyDown(event, key);
});

function renderPanelHotKey() {
    return "<div class='form-group'>" +
                "<div class='row'>" +
                    "<div class='col-3'><p class='hot-key font-weight-bold'> F1 </p></div>" +
                    "<div class='col-9'><p class='desciption'>" + $("#labelAddNewInvoice").val() + "</p></div>" +
                "</div>" +
                "<div class='row'>" +
                    "<div class='col-3'><p class='hot-key font-weight-bold'> F2 </p></div>" +
                    "<div class='col-9'><p class='desciption'>" + $("#labelFindProduct").val() + "</p></div>" +
                "</div>" +
                "<div class='row'>" +
                    "<div class='col-3'><p class='hot-key font-weight-bold'> F3 </p></div>" +
                    "<div class='col-9'><p class='desciption'>" + $("#labelFocusQuantity").val() + "</p></div>" +
                "</div>" +
                "<div class='row'>" +
                    "<div class='col-3'><p class='hot-key font-weight-bold'> F4 </p></div>" +
                    "<div class='col-9'><p class='desciption'>" + $("#labelFindCustomer").val() + "</p></div>" +
                "</div>" +
                "<div class='row'>" +
                    "<div class='col-3'><p class='hot-key font-weight-bold'> F5 </p></div>" +
                    "<div class='col-9'><p class='desciption'>" + $("#labelDiscountByPercent").val() + "</p></div>" +
                "</div>" +
                "<div class='row'>" +
                    "<div class='col-3'><p class='hot-key font-weight-bold'> F6 </p></div>" +
                    "<div class='col-9'><p class='desciption'>" + $("#labelDiscountByCash").val() + "</p></div>" +
                "</div>" +
                "<div class='row'>" +
                    "<div class='col-3'><p class='hot-key font-weight-bold'> F7 </p></div>" +
                    "<div class='col-9'><p class='desciption'>" + $("#labelFocustNote").val() + "</p></div>" +
                "</div>" +
                "<div class='row'>" +
                    "<div class='col-3'><p class='hot-key font-weight-bold'> F8 </p></div>" +
                    "<div class='col-9'><p class='desciption'>" + $("#labelCustomerPaid").val() + "</p></div>" +
                "</div>" +
                "<div class='row'>" +
                    "<div class='col-3'><p class='hot-key font-weight-bold'> F9 </p></div>" +
                    "<div class='col-9'><p class='desciption'>" + $("#labelPrintPayment").val() + "</p></div>" +
                "</div>" +
                "<div class='row'>" +
                    "<div class='col-3'><p class='hot-key font-weight-bold'> F10 </p></div>" +
                    "<div class='col-9'><p class='desciption'>" + $("#labelPrintPickList").val() + "</p></div>" +
                "</div>" +
                "<div class='row'>" +
                    "<div class='col-3'><p class='hot-key font-weight-bold'> F11 </p></div>" +
                    "<div class='col-9'><p class='desciption'>" + $("#labelFullScreenMode").val() + "</p></div>" +
                "</div>"
        "</div>";
}

function bindingEventShowPopupAddNewCustomer() {
    $("#btnShowPopUpAddCustomer").on("click", function (e) {
        e.preventDefault();
        if(navigator.onLine) {
            addNewCustomer();
        } else {
            bootbox.alert(messageCheckConnection);
        }
    });

}

function bindingEventAddCustomer() {
    $("#btnAddCustomer").unbind('click').on("click", function (e) {
        e.preventDefault();
        if(navigator.onLine) {
            $("#crudaction").val('insert');
            if ($('.credit').is(':checked')){

                $('.credit').val("true");
                var limitCredit = $('#creditLimitInCustomer');
                var creditWarning = $("creditWarningInCustomer");

                limitCredit.val(formatStringToNumber(limitCredit.val()));
                creditWarning.val(formatStringToNumber(creditWarning.val()));
            } else {
                $('#terms').val("false");
            }

            var homeAddress = $("#addressHome");
            var workplaceAddress = $("#addressWorkplace");

            if(!homeAddress.val()) {
                $("#addressNameHome").remove();
                homeAddress.remove();
            }

            if(!workplaceAddress.val()) {
                $("#addressNameWorkplace").remove();
                workplaceAddress.remove();
            }

            addNewCustomer();
        } else {
            bootbox.alert(messageCheckConnection);
        }
    });
}

function addNewCustomer() {
    var formData = $('#addCustomerForm')[0];
    var data = new FormData(formData);
    $.ajax({
        url: addNewCustomerUrl,
        method: 'POST',
        data: data,
        timeout: 60000,
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        cache: false,
        error: function () {
            notification('error', messageErrorOccur, false);
        }
    }).done(function (respond) {
        var modalAddCustomer = $("#modalAddCustomer");
        $("#modalAddCustomer .modal-body").html(respond);
        modalAddCustomer.modal("show");

        var resultAddCustomer = $("#resultAddCustomer").val();
        if(resultAddCustomer === 'true') {
            resultAddCustomer = 'false';
            var customerId = $("#newCustomerId").val();
            modalAddCustomer.find(".modal-body").empty();
            modalAddCustomer.modal('hide');
            // save or update customer in indexedDB
            if(customerId) {
                getCustomerFromServer(customerId, globalOutletId, function (customer) {
                    if(customer) {
                        $.fn.updateData2Customer(customer, function (customerSaved) {
                            if(customerSaved) {
                                var invoiceNumber = $(".invoice-list-nav ul li.active a").attr('href').split("#invoice")[1];
                                $.fn.addCustomer2OrderOutletOffline(customerSaved.customerId, invoiceNumber, function (result) {
                                    if(result) {
                                        loadOrderContent(invoiceNumber, false, false);
                                    } else {
                                        searchCustomerBox.val("");
                                        notification('error', messageErrorOccur, false);
                                    }
                                });
                                notification('success', messageAddCustomerSuccess, false);
                            } else {
                                notification('error', messageErrorOccur, false);
                            }
                        });
                    } else {
                        notification('error', messageErrorOccur, false);
                    }
                });
            }
        }
        bindingEventAddCustomer();
    });
}


// create nav tab scrollable
function bindingEventNavTabScrolling() {
    /*enable scrolling tab nav*/
    $(".invoice-list-nav .nav-tabs").scrollingTabs('refresh');
    $(".invoice-list-nav .nav-tabs").scrollingTabs({
        bootstrapVersion: 4,
        cssClassLeftArrow: 'fa fa-chevron-left',
        cssClassRightArrow: 'fa fa-chevron-right'
    });
}

// format number in input customer pay
function bindingEventFormatCurrencyForInputCustomerPay() {
    var inputTarget = $("#inputCustomPaid");
    inputTarget.unbind("input").on("input", function (e) {
        e.preventDefault();
        var inputValue = inputTarget.val();
        if(inputValue.length <= 0) {
            $("#labelRefund").text(0);
            return
        }
        inputTarget.val(realtimeInputFormartNumber(inputValue, true, false));
        calculateRefund();
    });
}

function bindingEventFormatCurrencyInputDiscount() {
    var inputTarget = $("#inputDiscountByCash");
    inputTarget.unbind("input").on("input", function (e) {
        e.preventDefault();
        var inputValue = inputTarget.val();
        if(inputValue.length <= 0) {
            inputValue = 0;
            inputTarget.val('');
        }
        inputTarget.val(realtimeInputFormartNumber(inputValue, true));
        // convert cash to percent
        var totalOriginalPrice = formatStringToNumber($("#totalOriginalPrice").text());
        var newDiscountPercent = (formatStringToNumber(inputValue) * 100) / totalOriginalPrice;

        if(inputValue === 0) {
            $("#inputDiscountByPercent").val('');
        } else {
            $("#inputDiscountByPercent").val(formatNumber(newDiscountPercent));
        }
        calculateTotalPrice();
    });
    inputTarget.blur(function (e) {
        e.preventDefault();
        if(!inputTarget.val()) {
            inputTarget.val('');
        }
    });
}

function bindingEventFormatPercentInputDiscount() {
    var inputTarget = $("#inputDiscountByPercent");
    inputTarget.unbind("input").on("input", function (e) {
        e.preventDefault();
        var inputValue = inputTarget.val();
        if(inputValue.length <= 0) {
            inputValue = 0;
            inputTarget.val('');
        }
        // convert percent to number
        var percent = formatStringToNumber(inputValue);
        var totalOriginalPrice = formatStringToNumber($("#totalOriginalPrice").text());
        var newDiscountPrice = (percent * totalOriginalPrice)/100;
        if(inputValue === 0) {
            $("#inputDiscountByCash").val('');
        } else {
            $("#inputDiscountByCash").val(formatNumber(newDiscountPrice));
        }
        calculateTotalPrice();
    });
    inputTarget.blur(function (e) {
        e.preventDefault();
        if(!inputTarget.val()) {
            inputTarget.val('');
        }
    });
}

function bindingEventInputFocus() {
    // get select value when focused input
    $("input").focus(function () {
        $(this).select();
    });
}

function renderButtonRemoveCustomer() {

    if(searchCustomerBox.val() !== "" && searchCustomerBox.val().length > 0) {
        // hide button add
        $("#btnShowPopUpAddCustomer").hide();
        // show button remove
        $("#btnRemoveCustomer").show();

    } else {
        // show button add
        $("#btnShowPopUpAddCustomer").show();
        // hide button remove
        $("#btnRemoveCustomer").hide();
    }
    //
    searchCustomerBox.on("blur" ,function () {
       searchCustomerBox.val(renderCustomerNameInSearchBox($("#customerName").val(), $("#pricingName").val()));
    });
}

function renderCustomerWithPhoneNumber(postCode, phoneNumber){
    var result = "";
    if(postCode != null){
        result = "(" + postCode + ")";
    }
    if(phoneNumber != null){
        result += phoneNumber;
    }
    return result;
}


function renderInvoiceNumber() {
    var prevItem = $("#navItemAdd").prev();
    if(prevItem.length >= 0) {
        // get id from prev item
        var id = prevItem.children().attr("href").replace(/[\D\s\\._\-]+/g,"").trim();
        return parseInt(id, 10) + 1;
    }
    return 1;
}

function bindingEventUpdateProductQuantity() {
    var quantityBox = $(".quantity-item-box");
    quantityBox.on("keypress", function (e) {
        var quantity = e.target.value;
        var charCode = (e.which) ? e.which : e.keyCode;
        if (charCode > 31 && (charCode < 48 || charCode > 57)){return false;}
        if(formatStringToNumber(quantity) < -2147483648 || formatStringToNumber(quantity) > 2147483647) {
            return false;
        }
        e.target.value = quantity;
        if(charCode === 13) {
            e.preventDefault();
            var skuDimensionId = e.target.dataset.skudimensionid;
            var invoiceNumber = $(".invoice-list-nav ul li.active a").attr('href').split("#invoice")[1];
            updateQuantity(formatStringToNumber(quantity), skuDimensionId, invoiceNumber);
        }
    });
}

function bindingEventUpdateUnitPrice() {
    var unitPriceBox = $(".unit-price");
    unitPriceBox.on("keydown", function (e) {
        var code = e.keyCode || e.which;
        if (code == 13) {
            e.preventDefault();
            var price = formatStringToNumber(e.target.value);
            var skuId = e.target.dataset.skuid;
            $.fn.getProductOutletSkuById(skuId, function (callbackProductSku) {
                var invoiceNumber = $(".invoice-list-nav ul li.active a").attr('href').split("#invoice")[1];
                if(callbackProductSku !== null && callbackProductSku !== undefined) {
                    var originalPrice = callbackProductSku.originalPrice;
                    if( (originalPrice > 0 && originalPrice <= price ) || originalPrice === 0) {
                        updatePriceProductOrderItem(invoiceNumber, skuId, price, originalPrice);
                    } else {
                        loadOrderContent(invoiceNumber, false, true);
                        notification('error', msgCannotUpdateUnitPirce, false);
                    }
                }
            });
        }
    });
    unitPriceBox.on("input", function (e) {
        var price = e.target.value;
        e.target.value = realtimeInputFormartNumber(price, true);
    });
}

function updatePriceProductOrderItem(invoiceNumber, skuId, price, salePrice) {
    $.fn.getOrderItemByInvoiceNumberAndSkuId(invoiceNumber, skuId, function (orderItem) {
        if(orderItem) {
            var discountPrice = (salePrice - price);
            if(discountPrice < 0) {
                 discountPrice = 0;
            }
            orderItem.discountPrice = discountPrice;
            orderItem.salePrice = price;
            orderItem.applyPromotion = false;
            $.fn.updateData2ProductOrderItem(orderItem, function (updatedOrderItem) {
                if(updatedOrderItem) {
                    loadOrderContent(invoiceNumber, false, true);
                }
            })
        }
    });
}

/**
 * Event search product
 */
function bindingEventOnSearchProduct() {
    searchByPressKey();
    // searchByBarCodeScanner();
}

function searchByPressKey() {
    searchProductBox.on({
        keypress: function () {
            searchProductBox.autocomplete("enable");
        }
    });
    searchProductBox.autocomplete({
        minLength: 1,
        maxShowItems: 15,
        autoFocus: true,
        source: function (request, respond) {
            ajaxSearchProduct(request.term.trim(), respond);
        },
        focus: function (event, ui) {
            event.preventDefault();
        },
        select: function(event, ui) {
            event.preventDefault();
            if(!ui.item.isExist) {
                return false;
            }
            searchProductBox.val(ui.item.skuCode);
            if(linkedWithInventory && ui.item.quantity < 1){
                bootbox.alert(messageNoProductsFound);
            } else {
                let invoiceNumber = $(".invoice-list-nav ul li.active a").attr('href').split("#invoice")[1];
                let discountPrice = 0;
                let salePrice = ui.item.salePrice;
                let productOrderItem = {
                    productOrderItemId : null,
                    orderOutletId : null,
                    invoiceNumber: invoiceNumber,
                    skuId : ui.item.skuId,
                    skuCode: ui.item.skuCode,
                    skuTitle: ui.item.title,
                    skuDimensionId: ui.item.skuDimensionId,
                    skuDimensionCode: ui.item.code,
                    size: ui.item.size,
                    productName: ui.item.productName,
                    quantity : 1,
                    salePrice: salePrice,
                    discountPrice: discountPrice,
                    image: ui.item.image,
                    barCode: ui.item.barCode,
                    catGroup: ui.item.catGroupId,
                    catGroupName: ui.item.catGroupName,
                    brandId : null,
                    applyPromotion : false
                };
                addOrUpdateProduct2CartOffline(productOrderItem).then(() => {
                    loadOrderContent(invoiceNumber, false, false)
                });
            }
        },
        close: function (event, ui) {
            $(this).select();
        }
    }).data("ui-autocomplete")._renderItem = function (ul, item) {
        let row = '';
        if(item.isExist) {
            row = "<div class='item-product d-flex flex-wrap'> " +
                "<div class='item-left mr-3'>" +
                "<div class='image-item'>" +
                "<img src='"+ "/EShoping" + item.image +"' class='img-fluid' width='50px' height='50px'/>" +
                "</div>" +
                "</div>" +
                "<div class='item-right'>" +
                "<div class='mb-2'><span class='title-item'> " + item.productName + "</span></div>" +
                "<div class='mb-2'><span class='title-item'> " + item.title + "</span>&nbsp;-&nbsp;<span class='title-item'> " + item.size + "</span></div>" +
                "<div><span class='label-price'>" +
                formatNumber(item.salePrice) +
                "</span></div>" +
                "</div>" +
                "</div>";
        } else {
            row = "<div class='d-flex justify-content-center align-items-center'><div class='p-2'>" + item.label+ "</div></div>";
        }
        return $("<li class='item-product'>").append(row).appendTo(ul);
    }
}

/**
 * Search product from database
 * @param inputQuery
 * @param callBack
 */
async function ajaxSearchProduct(inputQuery, callBack) {
    $.ajax({
        url: searchProductUrl,
        method: "GET",
        contentType: 'application/json',
        data: {
            query : inputQuery
        },
    }).done(function (response) {
        if(response.result) {
            callBack($.map(response.products, function (item) {
                return {
                    productName : item.sku.product.name,
                    title:  item.sku.title,
                    size: item.size,
                    image: item.sku.image,
                    salePrice: item.salePrice,
                    skuId: item.sku.productSkuId,
                    skuCode: item.sku.skuCode,
                    skuDimensionId: item.productSkuDimensionId,
                    catGroupId: item.sku.product.catGroup.catGroupId,
                    catGroupName: item.sku.product.catGroup.name,
                    code: item.code,
                    barCode: item.barCode,
                    isExist : true
                }
            }));
        } else {
            callBack([{label: messageNoProductsFound, isExist: false}]);
        }
    });

}

/**
 * Add product to cart in offline
 */
async function addOrUpdateProduct2CartOffline(productOrderItem) {
    let invoiceNumber = productOrderItem.invoiceNumber;
    let skuDimensionId = productOrderItem.skuDimensionId;
    let quantity = productOrderItem.quantity;
    let productOrderItemDB = await $.fn.getOrderItemByInvoiceNumberAndSkuId(invoiceNumber, skuDimensionId);
    if(productOrderItemDB) { // exists order item
        productOrderItem = productOrderItemDB;
        productOrderItem.quantity += quantity;
        await $.fn.updateObjectStored("ProductOrderItem", productOrderItem);
    } else {
        await $.fn.saveObjectStored("ProductOrderItem", productOrderItem);
    }

}

function bindingEventOnSearchCustomer() {
    searchCustomerBox.autocomplete({
        minLength: 1,
        maxShowItems: 6,
        autoFocus: true,
        source:function (request, respond) {
            searchCustomerFromIndexedDB(request, respond);
        },
        focus: function (event, ui) {
            event.preventDefault();
            if(ui.item.val === 0 ){
                $("ul.ui-autocomplete li.ui-menu-item").removeClass("ui-state-focus");
            }
        },
        select: function(event, ui) {
            event.preventDefault();
            if(ui.item.val === 0) {
                return false;
            }
            var invoiceNumber = $(".invoice-list-nav ul li.active a").attr('href').split("#invoice")[1];
            $.fn.addCustomer2OrderOutletOffline(ui.item.customerId, invoiceNumber, function (result) {
                if(result) {
                    if(navigator.onLine && $("#selectSaleChanel").val() === "online" && ui.item.address) {
                        $("#deliveryAddress").val(ui.item.address);
                        findAllDeliveryMethods($("input[name='deliveryMethod']:checked").val(), function () {
                            loadOrderContent(invoiceNumber, false, false, false);
                        });
                    } else {
                        loadOrderContent(invoiceNumber, false, false);
                    }
                } else {
                    searchCustomerBox.val("");
                    notification('error', messageErrorOccur, false);
                }
            });
        },
        close: function (e, ui) {
            $(this).val();
            var customerName = $("#customerName").val();
            if(customerName === 'CURRENT_CUSTOMER') {
                customerName = $("#currentCustomerLabel").val();
            }
            $(this).val(customerName);
            $(this).select();
        }
    }).data("ui-autocomplete")._renderItem = function (ul, item){
        var stringBuilder = '';
        if(item.val === 1) {

            stringBuilder = "<div class='item-customer'> " +
                "<div class='d-flex'>" +
                "<div class='flex-fill'><p>" + item.fullname + "</p></div>" +
                "<div class='flex-fill text-right'><span class='text-muted'>" + renderCustomerWithPhoneNumber(item.postCode, item.phoneNumber) + "</span></div>" +
                "</div>";
            if (item.address != null) {
                stringBuilder += "<div>" + item.address + "</div>";
            }

            stringBuilder += "</div>";
        } else {
            stringBuilder = "<div class='d-flex justify-content-center align-items-center'><div class='p-2'>" + item.label+ "</div></div>";
        }
        return $("<li class='item-customer'>").append(stringBuilder).appendTo(ul);
    }
}

function renderCustomerNameInSearchBox(fullname, pricingName) {
    var stringBuilder = "";
    if(fullname) {
        stringBuilder += fullname;
    }
    if(pricingName) {
        stringBuilder += (" - " + pricingName);
    }
    return stringBuilder;
}

/**
 * Search customer from offline
 * @param request
 * @param respond
 */
function searchCustomerFromIndexedDB(request, respond) {
// Select from IndexedDB
    return $.fn.retrieveObjectsCustomer("CustomerInfor", ["customerName", "phoneNumber"], request.term.trim(), function(callbackObjects){
        if(!callbackObjects.length) {
            respond([{label: messageNoResultFound, val: 0}]);
        } else {
            respond($.map(callbackObjects, function (item) {
                return {
                    loyaltyMemberId: item.loyaltyMemberId,
                    customerId: item.customerId,
                    fullname: item.customerName,
                    creditLimit: item.creditLimit,
                    creditWarning: item.creditWarning,
                    totalDebt: item.totalDebt,
                    term: item.term,
                    point: item.point,
                    phoneNumber: item.phoneNumber,
                    birthday: item.birthday,
                    status: item.status,
                    postCode: item.postCode,
                    address: item.address,
                    pricingName : item.pricingName,
                    val: 1
                }
            }));
        }
    });
}

/**
 * Add invoice when offline mode
 * @param invoiceNumber {number} invoice number (1, 2, 3,...)
 */
async function addNewInvoiceOffline(invoiceNumber) {
    // 1. update all orderOutlet open = false
    const orderOutlets = await $.fn.getAllObjectStored("OrderOutlet");
    for(const orderOutlet of orderOutlets) {
        orderOutlet.isOpen = false;
        if(invoiceNumber == orderOutlet.invoiceNumber) {
           continue;
        }
        await $.fn.updateObjectStored("OrderOutlet", orderOutlet);
    }
    // 2.create new OrderOutlet in indexedDB
    var resutlCreate = createOrderOutletDefault(invoiceNumber);
    if(resutlCreate) {
        // 3. load new content page.
        refreshPage();
    } else {
        notification('error', messageErrorOccur, false);
    }
}

function renderNavTab(invoiceNumber, active) {
    return "<li id='navItem"+invoiceNumber +"' class='nav-item "+ active +"' role='presentation'>" +
        "<a class='nav-link "+ active + "' aria-selected='true' role='tab' data-toggle='tab' href='#invoice"+ invoiceNumber +"'" +
        "onclick='switchTab("+ invoiceNumber +")'>"
        + renderInvoiceName(invoiceNumber) + "&nbsp;&nbsp;" +
        "<span onclick='removeTab("+ invoiceNumber +")'>&times;</span>" +
        "</a>" +
        "</li>";
}

function renderInvoiceName(invoiceNumber) {
    return $("#invoiceName").val() + " " + invoiceNumber;
}

// generate code for OrderOutlet
function generateOrderOutletCode() {
    var currentDate = new Date();

    return currentDate.getDate() + "" + (currentDate.getMonth() + 1)  + "" +
        currentDate.getFullYear() + "" + currentDate.getHours() + "" + currentDate.getMinutes() + "" + currentDate.getSeconds();
}

function removeTab(invoiceNumber) {

    bootbox.confirm({
        message: messageWarningDeleteTab + "&nbsp;<strong>" + renderInvoiceName(invoiceNumber) + "</strong>",
        buttons: {
            confirm: {
                label: 'Yes',
                className: 'btn-primary'
            },
            cancel: {
                label: 'No',
                className: 'btn-danger'
            }
        },
        callback: function (yes) {
            if(yes) {
                deleteInvoiceOffline(invoiceNumber);
            }
        }
    });

}

/**
 * Delete invoice both local and server.
 * @param invoiceNumber
 */
async function deleteInvoiceOffline(invoiceNumber) {
    const [resultDeleteOrderOutlet, resultDeleteOrderItem] = await Promise.all([$.fn.deleteObjectStore("OrderOutlet", "invoiceNumber", invoiceNumber)
                                                                                ,$.fn.deleteObjectStore("ProductOrderItem", "invoiceNumber", invoiceNumber)])
    if(resultDeleteOrderOutlet && resultDeleteOrderItem) {
        refreshPage();
    } else {
        bootbox.alert(messageErrorOccur);
    }
}


/**
 * Load invoice information from indexedDB
 * @param invoiceNumber {number} number of invoice ~ orderoutletId
 * @param focusRow {boolean} focus first item in products cart
 * @param isUpdatePrice {boolean} update unit price from retailer
 * @param focusPromotionCode {boolean} focus input promotion code
 * @param isNotChangeAddress {boolean} change delivery address when add customer to invoice
 */
async function loadOrderContent(invoiceNumber, focusRow, isUpdatePrice, focusPromotionCode, isNotChangeAddress) {
    // 1.get OrderOutlet
    let orderOutlet = await $.fn.getObjectStoredWithIndex("OrderOutlet", "invoiceNumber", invoiceNumber);
    if(orderOutlet) {
        //2. get customer
        let customer = null;
        // 3. shopping cart list
        let productOrderItems = await $.fn.getProductOrderItems( invoiceNumber);
        renderCartItems(orderOutlet, customer, productOrderItems, focusRow);
    } else {
        bootbox.alert(messageErrorOccur);
    }
}

/**
 * Render list product order item with offline mode.
 * @param orderOutlet
 * @param orderItems array productOrderItem
 * @param focusRow
 * @param resultPromotion {object} optional
 * @param loyaltyOutletEvents {array} optional
 * @param orderProvide {object} optional
 */
function renderCartItems(orderOutlet, customer, orderItems, focusRow, focusPromotionCode, resultPromotion, loyaltyOutletEvents, orderProvide, callback) {
    var table = "";
    var totalItem = 0;
    var totalPrice = 0;
    var totalOriginalPrice = 0;
    var totalStoreDiscountPrice = 0;
    var totalPromotionDiscountPrice = 0;
    var deliveryPrice = formatStringToNumber($("#deliveryFee").text());
    var totalAmountOff = 0;
    var totalFixPriceDiscount = 0;
    var voucherDiscount = 0;
    var totalShippingFixPrice = 0; // amount
    var totalShippingDiscount = 0;
    var rewardProductDiscountValue = 0;
    var subtotalPrice = 0;

    $("#totalLoyaltyPoint").text(0);

    if(orderItems && orderItems.length > 0) {
        table = "<table id='productCart' class='table table-striped table-hover'>";
        table += "<thead><tr>" +
                    "<th><strong></strong></th>" +
                    "<th><strong>"+ $("#labelSkuDimensionCode").val() +"</strong></th>" +
                    "<th><strong>"+ $("#labelProductName").val() +"</strong></th>" +
                    "<th><strong>"+ $("#labelSkuTitle").val() +"</strong></th>" +
                    "<th><strong>"+ $("#labelSize").val() +"</strong></th>" +
                    "<th class='text-center'><strong>"+ $("#labelQuantity").val() +"</strong></th>" +
                    "<th></th>" +
                    "<th class='text-center'><strong>"+ $("#labelSalePrice").val() +"</strong></th>" +
                    "<th class='text-center'><strong>"+ $("#labelTotalPrice").val() +"</strong></th>" +
                "</tr></thead>";
        table += "<tbody>";
        var row = "";

        orderItems.reverse().forEach(function (orderItem, index) {

            // calculate total price
            totalItem++;
            totalOriginalPrice += Number(((orderItem.salePrice + orderItem.discountPrice) * orderItem.quantity).toFixed(2));
            totalStoreDiscountPrice += Number((orderItem.discountPrice * orderItem.quantity).toFixed(2));

            // render row
            row += "<tr>" +
                "<td class='align-middle'>" +
                "<span class='text-price-medium text-danger'>" +
                "<i class='fa fa-trash'></i>" +
                "</span>" +
                "</td>" +
                "<td class='align-middle'>" + orderItem.skuDimensionCode + "</td>" +
                "<td class='align-middle'>" + generateOrderItemName(orderItem.skuDimensionId, orderItem.productName, null, null, customer) + "</td>" +
                "<td class='align-middle'>" + orderItem.skuTitle + "</td>" +
                "<td class='align-middle'>" + orderItem.size + "</td>" +
                "<td class='align-middle' width='140px'>" +
                "<div class='input-group'>" +
                "<button class='btn btn-light btn-sm text-muted btn-change-quantity btn-decrease' type='button'" +
                " onclick=\"decreaseQuantity(" + index + "," + orderItem.skuDimensionId + ", " + orderItem.invoiceNumber + ")\">" +
                "<i class='fa fa-angle-down text-price-medium'></i></button>" +
                "<input type='tel' id='boxQuantity" + index + "' class='form-control text-center quantity-item-box'" +
                " value='" + formatNumber(orderItem.quantity, true) + "' data-skuDimensionId='" + orderItem.skuDimensionId + "'/>" +
                "<button class='btn btn-light btn-sm text-muted btn-change-quantity btn-increase' type='button'" +
                " onclick=\"increaseQuantity(" + index + "," + orderItem.skuDimensionId + "," + orderItem.invoiceNumber + ")\"><i class='fa fa-angle-up text-price-medium'></i></button>" +
                "</div>" +
                "</td>" +
                "<td></td>" +
                "<td width='7%' class='text-center text-right unit-price align-middle'>" + formatNumber(orderItem.salePrice) + "</td>" +
                "<td class='text-right align-middle font-weight-bold total-price'>" +
                "<div class='form-group'>" +
                "<div style='padding: 6px 0'>" +
                formatNumber(orderItem.salePrice * orderItem.quantity) +
                "</div>" +
                "</div>" +
                "</td>" +
                "</tr>";
            totalPromotionDiscountPrice += (totalAmountOff + totalFixPriceDiscount + totalShippingDiscount + totalShippingFixPrice);
            subtotalPrice = totalOriginalPrice - (totalAmountOff + totalFixPriceDiscount);
            totalPrice = Number((totalOriginalPrice - totalStoreDiscountPrice - totalPromotionDiscountPrice - rewardProductDiscountValue - voucherDiscount + deliveryPrice).toFixed(2));
        })
        table += (row + "</tbody></table>");
        deliveryPrice = formatStringToNumber($("#deliveryFee").text());
        totalPrice += deliveryPrice;

        orderOutlet.totalOriginalPrice = totalOriginalPrice;
        orderOutlet.totalItem = totalItem;
        orderOutlet.totalStoreDiscountPrice = totalStoreDiscountPrice;
        orderOutlet.totalPrice = totalPrice;
    } else { // empty cart
        table = "<div class='text-center align-items-center'>" +
                "   <p class='text-danger'>"+ messageCartEmpty +"</p>" +
                "</div>";
    }

    $(".product-cart").html(table);
    $("#totalOriginalPrice").text(formatNumber(totalOriginalPrice));
    $("#totalItem").text(totalItem);
    $("#moneyCustomerMustPay").text(formatNumber(totalPrice));
    bindingEventOnBlurQuantityBox();
    bindingEventInputFocus();
    bindingEventUpdateProductQuantity();
    bindingEventUpdateUnitPrice();
    searchProductBox.val('');
    if(focusRow && !focusPromotionCode) {
        $("#boxQuantity0").select();
    } else {
        searchProductBox.select();
    }
}

function generateOrderItemName(skuId, productName, productsPromotion, promotionBloked, customer) {
    var stringBuilder = '';
    // if(!promotionBloked && productsPromotion.length > 0 && !productsPromotion[0].skuExclude.includes(skuId)) {
    //     var hasPromotion = false;
    //     var stringBuilderPromotion = '<div class="product-promotion-avaiable">';
    //     productsPromotion.forEach(function(productPromotion) {
    //
    //         if(!customer || (productPromotion.userIds != null && productPromotion.userIds.includes(customer.userId)) // none customer or includes in promotion
    //             || (productPromotion.customerGroups.length === 0 && productPromotion.userIds == null && !productPromotion.firstOrder)  // apply for all
    //             || productPromotion.customerGroups.includes(customer.customerGroup)
    //             || (productPromotion.firstOrder && customer.numberOfOrder <= 0)) { // includes customer group
    //
    //             stringBuilderPromotion += '<div class="row"><p>' + productPromotion.promotionName + '</p></div>';
    //             hasPromotion = true;
    //         }
    //     });
    //     stringBuilderPromotion += '</div>';
    //
    //     if(hasPromotion) {
    //         stringBuilder += "<span class='label-promotion' data-toggle='tooltip' data-placement='right' data-html='true' title='" + stringBuilderPromotion + "'>" +
    //             "<i class='fa fa-star text-success'></i>" +
    //             "</span>&nbsp;"
    //     }
    // }
    stringBuilder += productName;

    return stringBuilder;
}

/**
 * Calculate the total amount of money owed by the customer
 */
function calculateCreditAvaiable(creditLimit, totalDebt) {
    return creditLimit - totalDebt;
}

/**
 * Delete product order item.
 * @param productId {number} id of productOrderItem, optional for online
 * @param orderOutletId {number}
 * @param invoiceNumber {number} optional for offline
 * @param skuid {number} id productOutletSku, optional for offline
 */
function deleteProduct(productId, orderOutletId, invoiceNumber, skuid) {
    $.fn.deleteProductOrderItem(skuid, invoiceNumber, function (callbackValue) {
        if(callbackValue) {
            loadOrderContent(invoiceNumber, false, true);
        } else {
            notification('error', messageErrorOccur, false);
        }
    });
}

/**
 *  Decrease quantity order item
 * @param boxIndex
 * @param skuDimensionId optional for offline
 * @param invoiceNumber optional for offline
 */
function decreaseQuantity(boxIndex, skuDimensionId, invoiceNumber) {
    var currentQuantity = $("#boxQuantity" + boxIndex).val();
    currentQuantity = formatStringToNumber(currentQuantity);
    var quantity = currentQuantity - 1;
    if(quantity <= 0) {
        return;
    }
    updateQuantity(quantity, skuDimensionId, invoiceNumber);
}

/**
 * Increase quantity
 * @param boxIndex
 * @param skuId
 * @param invoiceNumber
 */
function increaseQuantity(boxIndex, skuDimensionId, invoiceNumber) {
    var currentQuantity = $("#boxQuantity" + boxIndex).val();
    currentQuantity = formatStringToNumber(currentQuantity);
    var quantity = currentQuantity + 1;
    if(quantity <= 0) {
        return;
    }
    updateQuantity(quantity, skuDimensionId, invoiceNumber);
}

/**
 * Update quantity
 * @param quantity
 * @param skuDimensionId
 * @param invoiceNumber
 */
function updateQuantity(quantity, skuDimensionId, invoiceNumber){
    // TODO: check quantity of product in inventory
    $.fn.getOrderItemByInvoiceNumberAndSkuId(invoiceNumber, skuDimensionId).then(orderItem => {
        if(orderItem) {
            orderItem.quantity = quantity;
            $.fn.updateObjectStored("ProductOrderItem", orderItem).then((sucess, failure) => {
                if(!sucess || failure) {
                    bootbox.alert(messageErrorOccur);
                }
            });
        } else {
            bootbox.alert(messageErrorOccur);
        }
    }).finally(function () {
        loadOrderContent(invoiceNumber, false, false, false, true);
        searchProductBox.select();
    });


}

function bindingHotKeyEventKeyDown(event, key) {
    switch (key) {
        case 112:{ // F1
            event.preventDefault();
            addTab();
            break;
        }
        case 113 : { // F2
            event.preventDefault();
            searchProductBox.select();
            break
        }
        case 114 : { // F3
            event.preventDefault();
            $("#boxQuantity0").focus();
            break
        }
        case 115: { // 115 -> F4
            searchCustomerBox.focus();
            break;
        }
        case 116: { // -> F5
            event.preventDefault();
            $("#inputDiscountByPercent").focus();
            break;
        }
        case 117: { // -> F6
            event.preventDefault();
            $("#inputDiscountByCash").focus();
            break;
        }
        case 118: { // -> F7
            event.preventDefault();
            $("#noteBox").focus();
            break;
        }
        case 119:{ // 119 -> F8
            event.preventDefault();
            $("#inputCustomPaid").focus();
            break;
        }
        case 120:{ // F9
            event.preventDefault();
            printReceipt(true);
            break;
        }
        case 122:{ // 122 -> F11
            openFullscreen(document.documentElement);
            break;
        }
    }
}

function openFullscreen(element) {
    if (element.requestFullscreen) {
        element.requestFullscreen();
    } else if (element.mozRequestFullScreen) { /* Firefox */
        element.mozRequestFullScreen();
    } else if (element.webkitRequestFullscreen) { /* Chrome, Safari & Opera */
        element.webkitRequestFullscreen();
    } else if (element.msRequestFullscreen) { /* IE/Edge */
        element.msRequestFullscreen();
    }
}

/**
 * Load products default when resize product list
 */
function loadDefaulttPanel() {
    loadProduct2PanelOffline(0, 0, 12, 1);
}

function bindingEventAddProductInPanel() {
    $("a.card-link-product").on("click", function (e, element) {
        e.preventDefault();
        var productOutletSkuId = $(this).attr('href');
        var invoiceNumber = $(".invoice-list-nav ul li.active a").attr('href').split("#invoice")[1];
        var skuCode = $(this).attr('data-skucode');
        $.fn.getProductOutletSkuByCode(skuCode, function (productSku) {
            if(productSku) {
                var discountPrice = 0;
                var salePrice = productSku.listPrice;
                var offerPrice = productSku.offerPrice;
                if(offerPrice > 0) {
                    discountPrice = -(Number((offerPrice  - salePrice ).toFixed(2)));
                    salePrice = offerPrice;
                }
                var productOrderItem = {
                    productOrderItemId : null,
                    orderOutletId : null,
                    invoiceNumber: invoiceNumber,
                    skuId : productSku.productOutletSkuId,
                    skuCode: productSku.skuCode,
                    skuName: productSku.skuName,
                    productName: productSku.productName,
                    quantity : 1,
                    salePrice: salePrice,
                    discountPrice: discountPrice,
                    barCode: productSku.barCode,
                    catGroup: productSku.catGroupId,
                    brandId: productSku.brandId,
                    promotionBlocked: productSku.promotionBlocked,
                    applyPromotion : true
                };
                addOrUpdateProduct2CartOffline(productOrderItem, true);
            }
        });
    })
}

function bindingEventPagination() {
    $("#btnGoToPrevPage").on('click', function (e) {
        e.preventDefault();
        goToPrevPage();
    });
    $("#btnGoToNextPage").on('click', function (e) {
        e.preventDefault();
        goToNextPage();
    });
}

function goToNextPage() {
    var currentPage = parseInt($("#labelCurrentPage").text(), 10) + 1;
    var totalPage = parseInt($("#labelTotalPages").text(), 10);
    if(currentPage <= totalPage) {
        var subCatGroupId = $("ul.nav-tabs-subcat li a.active").attr("href");
        var topCatGroupId = $("ul.nav-tabs-cat li a.active").attr("href");
        loadProduct2PanelOffline(topCatGroupId , subCatGroupId, 12, currentPage);
    }
}

function goToPrevPage() {
    var currentPage = parseInt($("#labelCurrentPage").text(), 10);
    if(currentPage > 1) {
        currentPage--;
        var subCatGroupId = $("ul.nav-tabs-subcat li a.active").attr("href");
        var topCatGroupId = $("ul.nav-tabs-cat li a.active").attr("href");
        loadProduct2PanelOffline(topCatGroupId , subCatGroupId, 12, currentPage);
    }
}

function loadProduct2PanelOffline(topCatId, subCatId, limit, page) {
    //1 get all top catgroup
    $.fn.getAllTopCatGroup(function (topCatGroups) {
        //2 get sub catgroup base on top-catgroup
        if(topCatId === 0) {
            topCatId = topCatGroups[0].catGroupId;
        }
        $.fn.getSubCatGroupBaseOnTopCat(topCatId, function (subCatGroups) {
            if(subCatGroups !== null && subCatGroups !== undefined) {
                //3. get product base on top-catGroup and sub-catGroup
                if(subCatId === 0) {
                    subCatId = subCatGroups[0].catGroupId;
                }
                $.fn.getProductBaseOnSubNTopCatGroup(subCatId, limit, page, function (products) {
                    $.fn.getTotalProductItemsBaseCatGroup(subCatId, function (totalItem) {
                        var totalPage = Math.ceil(totalItem / limit);
                        renderProduct2PanelOffline(topCatGroups, topCatId, subCatGroups, subCatId, products, page, totalPage);
                    });
                });
            }
        });
    });
}

function loadSubCatNProduct2PanelBaseTopCat(topCatId) {
    loadProduct2PanelOffline(topCatId, 0, 12, 1);
}

function loadProduct2PanelBaseSubCat(subCatId) {
    var topCatGroupId = $("ul.nav-tabs-cat li a.active").attr("href");
    loadProduct2PanelOffline(topCatGroupId, subCatId, 12, 1);
}

/**
 * Render product to panel.
 * @param topCats
 * @param topCatId
 * @param subCats
 * @param subCatId
 * @param products
 * @param curentPage
 * @param totalPage
 */
function renderProduct2PanelOffline(topCats, topCatId, subCats, subCatId, products, curentPage, totalPage) {
    var stringBuilder = "<div class=\"top-cat-group clearfix mb-2 mt-3\">\n" +
                            "<ul class=\"nav nav-tabs border-bottom-0 nav-tabs-cat mb-1\" role=\"tablist\">";
    //top-cats
    if(topCats !== null && topCats.length > 0) {
        topCats.forEach(function (topCat, index) {
            if(topCatId === 0 || topCatId.toString() === topCat.catGroupId.toString()) {
                stringBuilder += "<li class=\"nav-item\" role=\"presentation\">\n" +
                                      "<a class=\"nav-link active\" href='"+ topCat.catGroupId +"' onclick='loadSubCatNProduct2PanelBaseTopCat(" + topCat.catGroupId +")' role=\"tab\" data-toggle=\"tab\">"+ topCat.name +"</a>\n" +
                                 "</li>";
            } else {
                stringBuilder += "<li class=\"nav-item\" role=\"presentation\">\n" +
                                    "<a class=\"nav-link\" href='"+ topCat.catGroupId +"' onclick='loadSubCatNProduct2PanelBaseTopCat(" + topCat.catGroupId +")' role=\"tab\" data-toggle=\"tab\">"+ topCat.name +"</a>\n" +
                                 "</li>";
            }
        });
    } else {
        stringBuilder += "<li></li>";
    }
    stringBuilder += "</ul></div>\n"; // end ul top-cat
    //sub-cats
    stringBuilder += "<div class=\"sub-cat-group clearfix mb-2\">\n" +
        "<ul class=\"nav nav-tabs border-bottom-0 nav-tabs-subcat mb-1\" role=\"tablist\">\n";

    if(subCats !== null && subCats.length > 0) {
        subCats.forEach(function (subCat, index) {
            if(subCatId === 0 || subCatId.toString() === subCat.catGroupId.toString()) {
                if(subCatId === 0) {
                    stringBuilder += "<li class=\"nav-item\" role=\"presentation\">\n" +
                                            "<a class=\"nav-link active\" href='" + 0 + "' onclick='loadProduct2PanelBaseSubCat(" + subCat.catGroupId + ")' role=\"tab\" data-toggle=\"tab\">All</a>\n" +
                                     "</li>";
                } else {
                    stringBuilder += "<li class=\"nav-item\" role=\"presentation\">\n" +
                                            "<a class=\"nav-link active\" href='"+ subCat.catGroupId +"' onclick='loadProduct2PanelBaseSubCat("+subCat.catGroupId+")' role=\"tab\" data-toggle=\"tab\">"+ subCat.name +"</a>\n" +
                                    "</li>";
                }
            } else {
                stringBuilder += "<li class=\"nav-item\" role=\"presentation\">\n" +
                                    "<a class=\"nav-link\" href='"+ subCat.catGroupId +"' onclick='loadProduct2PanelBaseSubCat(" + subCat.catGroupId +")' role=\"tab\" data-toggle=\"tab\">"+ subCat.name +"</a>\n" +
                                "</li>";
            }
        });
    } else {
        stringBuilder += "<li></li>";
    }

    stringBuilder += "</ul></div>\n"; // end ul sub-cat

    stringBuilder += "<div class=\"row mb-1\">\n" +
                         "<div class=\"col-12 text-right\">\n" +
                            "<span class=\"number-page mr-2\"><span id=\"labelCurrentPage\">"+ curentPage +"</span>/<span id=\"labelTotalPages\">"+ totalPage +"</span></span>\n" +
                            "<span title=\"label.pos.page.previous\" id=\"btnGoToPrevPage\" class=\"mr-2 btn-pagination\">\n" +
                                "<i class=\"fa fa-chevron-left\"></i>\n" +
                            "</span>\n" +
                            "<span title=\"label.pos.page.next\" id=\"btnGoToNextPage\" class=\"mr-2 btn-pagination\">\n" +
                                "<i class=\"fa fa-chevron-right\"></i>\n" +
                            "</span>\n" +
                        "</div>\n" +
                    "</div>";

    stringBuilder += "<div class=\"product-list-content\">\n" +
                         "<div class=\"row\">";
    // products
    if(products !== null && products.length > 0) {
        products.forEach(function (product, index) {
            stringBuilder += "<div class=\"card custom-col-card m-1\">\n" +
                                "<a href='"+ product.productOutletSkuId +"' class=\"card-link-product\" data-skucode='"+product.skuCode+"'>\n" +
                                    "<div class='card-body'>" +
                                    // "<div class=\"card-body product-img pb-0\">\n" +
                                    //     "<img src='\images\default-product-img.jpg\' class='card-img-top img-fluid' alt='product'>" +
                                    // "</div>\n" +
                                    "<div class=\"card-body product-card-body pb-0\">\n" +
                                        "<p class=\"product-card-text-sm card-text mb-0\">"+ product.productName +"</p>\n" +
                                        "<p class=\"product-card-text-sm font-weight-bold mb-0\">"+ formatNumber(product.listPrice) +"</p\n" +
                                        "<p class=\"product-card-text-sm text-muted mb-1\"><del>" + formatNumber((product.listPrice + product.offerPrice)) + "</del></p>\n" +
                                    "</div>\n" +
                                    "</div>" +
                                "</a>\n" +
                            "</div>";
        });
    } else  {
        stringBuilder += "<div></div>";
    }

    stringBuilder += "</div></div>\n";

    $("#productListContent").html(stringBuilder);
    $("#labelTotalPages").text($("#hiddenTotalPages").val());
    $("#labelCurrentPage").text($("#hiddenCurrentPage").val());

    bindingEventNavTabScrollingCatGroup();
    bindingEventPagination();
    bindingEventAddProductInPanel();
}

/**
 * Print invoice
 * @param processPayment {Boolean} process to payment and close invoice
 */
async function printReceipt(processPayment) {

    let resultValidate = await validateBeforePrint();
    if(resultValidate) {
        // show payment method
        const payments = await ajaxGetAllPaymentMethod();
        if(payments) {
            renderAllPaymentMethod(payments);
        } else {
            bootbox.alert($("#messagePaymentNotFound").val());
        }
    }  else {
        bootbox.alert(result);
    }
}

function ajaxGetAllPaymentMethod() {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: $("#getAllPaymentsMethodUrl").val(),
            method: "GET",
            contentType: 'application/json',
        }).done(function (response) {
            if(response && response.result) {
                resolve(response.payments);
            } else {
                reject(new Error(messageErrorOccur));
            }
        });
    });
}

function renderAllPaymentMethod(payments) {
    var stringBuilder = "";
    payments.forEach(function (payment) {
        stringBuilder += "<div class='card card-payment m-b-15 btn' data-paymentcode='"+ payment.code +"'>" +
                            "<div class='card-body'>" +
                                "<div class='form-group'><p class='card-text'>"+ payment.value +"</p></div>" +
                            "</div>" +
                        "</div>"
    });
    $("#modalChoosePayment .modal-body").html(stringBuilder);
    $("#modalChoosePayment").modal('show');
    bindEventChoosePaymentMethod();
}

function bindEventChoosePaymentMethod() {
    $(".card-payment").on("click", function() {
        var payment = $(this).data("paymentcode");
        $("#selectPayment").val(payment);
        $("#modalChoosePayment").modal('hide');
        showOrderInformationToPayment();
    })
}

function showOrderInformationToPayment(customer) {
    var customerName = searchCustomerBox.val() != "" ? searchCustomerBox.val() : $("#labelCustomerDefault").val();
    var payment = $("#selectPayment").val();
    var stringBuilder = '<div class="row"><p class="hide error-msg text-danger"></p></div>' +
                        '<div class="row">' +
                            '<h6>'+ $("#labelReceiveInformation").val() +'</h6>' +
                        '</div>' +
                        '<div class="form-group">' +
                        '<div class="row">' +
                            '<div class="col-7">'+ $("#labelCustomerName").val() +'</div>' +
                            '<div class="col-5 text-right"><span>'+ customerName +'</span></div>' +
                        '</div>';

    stringBuilder += '<hr/><div class="row"><h6>'+ $("#labelOrderInformation").val() +'</h6></div>' +
                     '<div class="form-group row">' +
                        '<div class="form-label col-md-7">' +
                            '<span>'+ $("#labelAmountMustPay").val() +'</span>' +
                        '</div>' +
                        '<div class="form-output col-md-5 text-right">' +
                            '<span>'+ $("#moneyCustomerMustPay").text() +'</span>' +
                        '</div>' +
                    '</div>';

    stringBuilder += '</div>';

    $("#modalCustomerOrderInfor .modal-body").html(stringBuilder);
    $("#modalCustomerOrderInfor").modal("show");
    bindingEventFormatCurrencyForInputCustomerPay();
    bindEventPayment();
}

function bindEventPayment() {
    $("#btnConfirmPayment").unbind('click').on('click', function () {
        var totalPrice = formatStringToNumber($("#moneyCustomerMustPay").text());
        var customerPaid = formatStringToNumber($("#inputCustomPaid").val());
        //1. validate customer paid
        if(validateCustomerPaid(totalPrice, customerPaid)) {
            //2. validate credit
            var payment = $("#selectPayment").val();
            if(payment === 'CREDIT' && customerPaid < totalPrice) {

                var creditLimit = formatStringToNumber($("#creditLimit").text());
                var creditBalance = formatStringToNumber($("#creditBalance").text());
                var creditWarning = (formatStringToNumber($("#creditWarning").val())  * creditLimit) / 100;
                var paymentDueDate = $("#paymentDueDate").text();
                if(paymentDueDate) {
                    paymentDueDate = new Date(paymentDueDate).getTime();
                } else {
                    paymentDueDate = '0';
                }

                var creditInfor = {
                    "creditLimit" : creditLimit,
                    "creditBalance" : creditBalance,
                    "creditWarning" : creditWarning, // double
                    "paymentDueDate" : paymentDueDate
                };
                warningCredit(totalPrice, creditInfor, function (callbackResult, skipConfirm) {
                    if(callbackResult) {
                        loadReceipt(true, skipConfirm);
                    }
                });
            } else {
                loadReceipt(true, true);
            }
        }
    });
}

function validateCustomerPaid(totalPrice, customerPaid) {
    var saleChanmel = $("#selectSaleChanel").val();
    if(saleChanmel === SALE_CHANEL[0]) { // online
        $("#modalCustomerOrderInfor").modal('hide');
        return true;
    }
    var payment = $("#selectPayment").val();
    if(payment !== 'CREDIT' && (customerPaid - totalPrice) < 0) {
        $(".error-msg").text(messageProductCustomerUnpaid);
        $(".error-msg").show();
        return false;
    }
    $("#modalCustomerOrderInfor").modal('hide');
    return true;
}

function loadReceipt(processPayment, skipConfirm) {
    var inputCustomerPaid = $("#inputCustomPaid");
    var customerPaid = formatStringToNumber(inputCustomerPaid.val());
    var totalStoreDiscountPrice = formatStringToNumber($("#inputDiscountByCash").val());
    var totalPromotionDiscountPrice = formatStringToNumber($("#promtionDiscountByCash").text());
    var totalPrice = formatStringToNumber($("#moneyCustomerMustPay").text());
    var refund = customerPaid - totalPrice;

    // find orderOutlet
    var invoiceNumber = $(".invoice-list-nav ul li.active a").attr('href').split("#invoice")[1];
    $.fn.getOrderOutletByInoviceNumber(invoiceNumber, function (orderOutlet) {
        if(orderOutlet) {

            var customerId = orderOutlet.customerId;
            var outletId = orderOutlet.outletId;

            if(!customerId){
                customerId = -1;
            }

            orderOutlet.createdDate = Date.now();
            orderOutlet.totalStoreDiscountPrice = totalStoreDiscountPrice;
            orderOutlet.totalPromotionDiscountPrice = totalPromotionDiscountPrice;
            orderOutlet.totalPrice = totalPrice;
            if(refund >= 0) {
                refund = 0;
            }

            orderOutlet.debt = Math.abs(refund);
            orderOutlet.note = $("#noteBox").val();

            // get outlet
            $.fn.getOutletById(outletId, function (outlet) {

                // get customer
                $.fn.getCustomerById(customerId, function (customer) {

                    // if debt in order over than credit limit -> notification
                    performPrintInvoice(invoiceNumber, outlet, orderOutlet, customer, customerPaid, processPayment, skipConfirm);

                });
            });
        } else {
            notification('error', messageErrorOccur, false);
        }
    });
}

function performPrintInvoice(invoiceNumber, outlet, orderOutlet, customer, customerPaid, processPayment, skipConfirm) {
    // get product order item
    $.fn.getProductOrderItems(invoiceNumber, function (orderItems) {
        var invoiceReceipt = $("#invoiceReceipt");
        if(processPayment) { //

            if(skipConfirm) {

                renderInvocieAndSubmitOrder(orderOutlet, orderItems, outlet, customer, customerPaid);

            } else { // confirm progress

                var selectSaleChanel = $("#selectSaleChanel").val();
                var messageConfirm = " ";
                if(selectSaleChanel === SALE_CHANEL[0]) { // online
                    messageConfirm = $("#messageConfirmProcessOrder").val();
                } else { // offline
                    messageConfirm = $("#messageConfirmProcessPayment").val();
                }

                bootbox.confirm({
                    message: messageConfirm,
                    buttons: {
                        confirm: {
                            label: 'Yes',
                            className: 'btn-primary'
                        },
                        cancel: {
                            label: 'No',
                            className: 'btn-danger'
                        }
                    },
                    callback: function (r) {
                        if (r) {

                            renderInvocieAndSubmitOrder(orderOutlet, orderItems, outlet, customer, customerPaid);
                            bootbox.hideAll();
                        } else { // not process to payment

                            $("#inputCustomPaid").val('');
                            $("#labelRefund").text('');

                        }
                    }
                });
            }
        } else { // not proccess to payment
            invoiceReceipt.find(".printBox").printThis({
                importCSS: false,
                afterPrint: function () {
                    invoiceReceipt.empty();
                }
            });
        }
    });
}

function clearPage(statusConnect) {
    $("#inputCustomPaid").val('');
    $("#labelRefund").text('0');
    searchProductBox.val('');
    $("#inputDiscountByCash").val("");
    $("#inputDiscountByPercent").val("");
    $("#deliveryAddress").val("");
    $("#deliveryLongitude").val("");
    $("#deliveryLatitude").val("");
    $("#hiddenCustomerId").val("");
    $("#inputPromotionCode").val("");
    $("#subtotalPrice").text('0');

    var invoiceNumber = $(".invoice-list-nav ul li.active a").attr('href').split("#invoice")[1];
    deleteInvoiceOffline(invoiceNumber);

    if($("#selectSaleChanel").val() === SALE_CHANEL[0]) {
        findAllDeliveryMethods();
    }

    if(statusConnect !== "online") {
        showTotalItemOrderOutletPaid();
        refreshPage();
    }
}

function renderInvocieAndSubmitOrder(orderOutlet, orderItems, outlet, customer, customerPaid) {
    var invoiceReceipt = $("#invoiceReceipt");
    var selectSaleChanel = $("#selectSaleChanel").val();
    var deliveryMethod = $("#deliveryMethod").val();

    if(!deliveryMethod) {
        deliveryMethod = 'PICK_N_GO';
    }

    var orderInformation = {};
    var customerName = " ";
    var phoneNumber = " ";
    var receiverAddress = " ";
    if(customer) {
        receiverAddress = $("#deliveryAddress").val();
        customerName = customer.customerName;
        phoneNumber = customer.postCode + "" + customer.phoneNumber;
    }
    orderInformation.receiverName = customerName;
    orderInformation.receiverPhone = phoneNumber;
    orderInformation.paymentMethod = $("#selectPayment").val();
    orderInformation.deliveryMethod = deliveryMethod;
    orderInformation.receiverAddress = receiverAddress;
    orderInformation.receiverLng = $("#deliveryLongitude").val();
    orderInformation.receiverLat = $("#deliveryLatitude").val();

    var orderOutletPaid = orderOutlet;
    orderOutletPaid.productOrderItems = orderItems;
    orderOutletPaid.customerPaid = customerPaid;
    orderOutletPaid.totalStoreDiscountPrice = orderOutlet.totalStoreDiscountPrice;
    orderOutletPaid.orderInformation = orderInformation;
    orderOutletPaid.promotionCode = $("#inputPromotionCode").val();

    if(selectSaleChanel === SALE_CHANEL[0]) { // sale chanel online
        orderOutletPaid.saleChanel = 'ONLINE';
        orderOutletPaid.referedBy = $("#loggedInUserId").val();
    } else { // sale chanel offline
        orderOutletPaid.saleChanel = 'SALE_CHANEL_DIRECT';
    }
    orderOutletPaid.code = generateOrderOutletCode();

    if(navigator.onLine) {

        payTheBill(orderOutletPaid, function (orderOutletCallback,promotion,giftPromotion) {
            if(orderOutletCallback) {

                if (selectSaleChanel === SALE_CHANEL[0]) { // sale chanel online
                    clearPage("online");
                } else { // sale chanel offline
                    //1. render html
                    invoiceReceipt.html(renderInvoiceHtml(orderOutletCallback, outlet, customer, orderItems, customerPaid,promotion,giftPromotion));
                    //2. print pop up
                    invoiceReceipt.find(".printBox").printThis({
                        importCSS: false,
                        afterPrint: function () {
                            invoiceReceipt.empty();
                            clearPage("online");
                        }
                    });
                }
            }
        });

    } else { // lost connection to internet ->  save order in indexeddb
        $.fn.writeData2OrderOutletPaid(orderOutletPaid, function (callbackOrder) {
            if (callbackOrder) {
                if(selectSaleChanel === SALE_CHANEL[0]) { // sale chanel online
                    clearPage("offline");
                } else {
                    //1. render html
                    invoiceReceipt.html(renderInvoiceHtml(orderOutlet, outlet, customer, orderItems, customerPaid));
                    // 2. print pop up
                    invoiceReceipt.find(".printBox").printThis({
                        importCSS: false,
                        afterPrint: function () {
                            invoiceReceipt.empty();
                            clearPage("offline");
                        }
                    });
                }
            } else {
                notification('error', messageErrorOccur, false);
            }
        });
    }
}

function renderInvoiceHtml(orderOutlet, outlet, customer, orderItems, customerPaid,promotion,giftPromotion) {
    var stringBuilder = "";
    if(outlet) {
        stringBuilder += renderOutletInforInvoiceOffline(outlet);
    }
    stringBuilder += renderInvoiceInforOffline(orderOutlet, "invoice");
    if(customer) {
        stringBuilder += renderCustomerInforInvoiceOffline(customer);
    }
    stringBuilder += renderProductCartInvoiceOffline(orderItems, promotion,giftPromotion, "invoice");
    stringBuilder += renderTotalInforCartInvoiceOffline(orderOutlet, customer, customerPaid);
    return stringBuilder;
}

function validateBeforePrint() {
    return new Promise((resolve, reject) => {
        var totalItem = $("#totalItem").text();
        //1. check total item
        if(totalItem <= 0) {
            reject(new Error(messageCartEmpty));
        } else {
            resolve(true);
        }
    });
}

function warningCredit(totalPrice, creditObject, callback) {
    var creditLimit = creditObject.creditLimit;
    var creditBalance = creditObject.creditBalance; // còn đươc nợ
    var creditWarning = creditObject.creditWarning; // double
    var paymentDueDate = creditObject.paymentDueDate;
    var today = new Date().getTime();

    var messageWarning = " ";
    var isNeedAuth = true;
    var className = '';

    if(paymentDueDate === '0' && creditBalance === creditLimit) { // first order
        if(totalPrice > creditBalance) {
            messageWarning = $("#msgCreditCase1").val();
        } else if((creditBalance - totalPrice) < creditWarning) {
            messageWarning = $("#msgCreditCase4").val();
            isNeedAuth = false;
        } else {
            callback(true, true);
            return;
        }
    }else if(totalPrice > creditBalance && paymentDueDate > today) { // Insufficient credit before due date
        messageWarning = $("#msgCreditCase1").val();
    } else if(totalPrice < creditBalance && paymentDueDate < today && (creditBalance - totalPrice) > creditWarning) { // Sufficient credit beyond payment due date
        messageWarning = $("#msgCreditCase2").val();
    } else if(totalPrice > creditBalance && paymentDueDate < today) { // Insufficient credit Beyond payment due date
        messageWarning = $("#msgCreditCase3").val();
    }  else if(totalPrice < creditBalance && paymentDueDate > today && (creditBalance - totalPrice) < creditWarning) { // Sufficient credit but below threshold Before due date
        messageWarning = $("#msgCreditCase4").val();
        isNeedAuth = false;
        className = 'text-warning';
    } else if(totalPrice < creditBalance && paymentDueDate < today && (creditBalance - totalPrice) < creditWarning) { // Sufficient credit but below threshold Beyond due date
        messageWarning = $("#msgCreditCase5").val();
        className = 'text-danger';
    } else {
        callback(true, false);
        return;
    }

    if(isNeedAuth) {
        bootbox.prompt({
            title: "Warning",
            message: messageWarning + "</br><div>" + $("#messageAuthApproveKey").val() + ":</div>",
            inputType: 'password',
            callback: function (result) {
                if(result) {
                    // get outlet
                    $.fn.getOutletById(globalOutletId, function (outlet) {
                        if(outlet && outlet.approveCreditKey) {
                            if(result === outlet.approveCreditKey) {
                                callback(true, true);
                            } else {
                                callback(false);
                                notification('error', $("#messageInvalidPassword").val(), false);
                            }
                        } else { // don't have approve key
                            callback(false);
                            notification('error', $("#messageNotHaveApproveKey").val(), false);
                        }
                    })
                } else {
                   callback(false);
                }
            },
            className: className
        });
    } else { // no need enter key approve
        bootbox.confirm({
            message: messageWarning,
            buttons: {
                confirm: {
                    label: 'Yes',
                    className: 'btn-primary'
                },
                cancel: {
                    label: 'No',
                    className: 'btn-danger'
                }
            },
            callback: function (result) {
                callback(result, true);
            }
        });
    }
}

function renderWarningNotEnoughQuantity(notEnough) {
    var row = '<td></td>';
    if(notEnough && linkedWithInventory) {
        row = "<td class='align-middle'>" +
                    "<span title='"+ messageNoProductsFound +"'>" +
                        "<i style='color: red' class='fa fa-exclamation-triangle'></i>" +
                    "</span>" +
               "</td>";
    }
    return row;
}

// todo update function render item in cart
function checkQuantityBeforePrint(invoiceNumber, callback) {
    $.fn.getOrderOutletByInoviceNumber(invoiceNumber, function (orderOutlet) {
        $.fn.getProductOrderItems(invoiceNumber, function (productOrderItems) {
            if(productOrderItems) {
                productOrderItems.forEach(function (orderItem, index) {
                    // get productSku corresponding
                    $.fn.getProductOutletSkuById(orderItem.skuId, function (productSku) {
                        if(productSku) {
                            if(productSku.quantity < orderItem.quantity) {
                                renderCartItems(orderOutlet, productOrderItems, false);
                                callback(false);
                                notification('error', messageNoProductsFound, false);
                                return;
                            }
                        }
                        if(index >= (productOrderItems.length - 1)) {
                            if(productSku.quantity < orderItem.quantity) {
                                renderCartItems(orderOutlet, productOrderItems, false);
                                callback(false);
                                notification('error', messageNoProductsFound, false);
                                return;
                            }
                            callback(true);
                        }
                    });
                });
            } else {
                callback(true);
            }
        });
    });
}

/**
 * Print preview pick list offline mode
 * @param orderOutlet
 * @param outlet
 */
function loadPrintPickListPreviewOffline(orderOutlet, outlet) {
    var customerId = orderOutlet.customerId;
    if(customerId == null || customerId === undefined){
        customerId = -1;
    }
    $.fn.getCustomerById(customerId, function (customer) {
        $.fn.getProductOrderItems(orderOutlet.invoiceNumber, function (orderItems) {
            //1. render html
            var stringBuilder = "";
            stringBuilder += renderOutletInforInvoiceOffline(outlet);
            stringBuilder += renderInvoiceInforOffline(orderOutlet, "pickList");
            // order by base on cat-group
            orderItems.sort(function (a, b) {
               return a.catGroup - b.catGroup;
            });
            stringBuilder += renderProductCartInvoiceOffline(orderItems, "pickList");

            $("#invoiceReceipt").html(stringBuilder);

            // 2. print pop up
            $(".printBox").printThis({
                importCSS: false,
                afterPrint: function () {
                    $("#invoiceReceipt").empty();
                }
            });
        });
    });
}

function showTotalItemOrderOutletPaid() {
    $.fn.totalItemOrderOutletPaid(function (totalItem) {
        var totalItemOrderOutlet = $("#totalItemOrderOutlet");
        var liItemOrderOutlet = $("#liTotalItemOrder");
        if(totalItem > 0) {
            totalItemOrderOutlet.text(totalItem);
            liItemOrderOutlet.show();
        } else {
            totalItemOrderOutlet.text(totalItem);
            liItemOrderOutlet.hide();
        }
    });
}

function renderOutletInforInvoiceOffline(outlet) {
   return "<div class=\"printBox\">\n" +
    "    <table width=\"100%\">\n" +
    "        <tbody><tr>\n" +
    "            <td style=\"text-align:center\">\n" +
    "                <table width=\"100%\">\n" +
    "                    <tbody>\n" +
    "                    <tr>\n" +
    "                        <td style=\"font-size:11px; text-align:center;\"></td>\n" +
    "                    </tr>\n" +
    "                    <tr>\n" +
    "                        <td style=\"font-size:11px; text-align:center;\"><strong style=\"font-size:11px\">"+ outlet.outletName +"</strong></td>\n" +
    "                    </tr>\n" +
    "                    <tr>\n" +
    "                        <td style=\"font-size:11px; text-align:center;\">"+ outlet.address +"</td>\n" +
    "                    </tr>\n" +
    "                    <tr>\n" +
    "                        <td style=\"font-size:11px; text-align:center;\">\n" +
    "                            <span> "+ $("#labelPhone").val() + ":</span><span>" + outlet.phoneNumber + "</span>\n" +
    "                        </td>\n" +
    "                    </tr>\n" +
    "                    </tbody>\n" +
    "                </table>\n" +
    "            </td>\n" +
    "        </tr>\n" +
    "        </tbody></table>\n";
}

function renderInvoiceInforOffline(orderOutlet, type) {
    var name;
    if(type === "pickList") {
        name = "labelPickListName";
    } else {
        name = "labelInvoiceName";
    }

    return "<div style=\"text-align:center; padding:10px 0 0;\"><strong style=\"font-size:12px\">"+ $("#"+ name).val() +"</strong></div>\n" +
    "    <table width=\"100%\">\n" +
    "        <tbody>\n" +
    "        <tr>\n" +
    "            <td style=\"text-align:center; font-size:11px;\">\n" +
    "                <span>"+$("#labelInvoiceNumber").val()+"</span>:&nbsp;\n" +
    "                <span>"+ orderOutlet.code +"</span>\n" +
    "            </td>\n" +
    "        </tr>\n" +
    "        <tr>\n" +
    "            <td style=\"text-align:center; font-size:11px;\">\n" + convertTimeStamp2SimpleDate(orderOutlet.createdDate) +"</td>\n" +
    "        </tr>\n" +
    "        </tbody>\n" +
    "    </table>";
}

function renderCustomerInforInvoiceOffline(customer){
    if(customer) {
         return "<table width=\"100%\" style=\"margin:10px 0 15px;\">\n" +
            "            <tbody>\n" +
            "            <tr>\n" +
            "                <td style=\"font-size:11px\">\n" +
            "                    <span>" + $("#labelCustomerName").val() + "</span>:&nbsp;<span> "+ customer.customerName +" </span>\n" +
            "                </td>\n" +
            "            </tr>\n" +
            "            <tr>\n" +
            "                <td style=\"font-size:11px\">\n" +
            "                    <span>\n"+ $("#labelPhone").val()+ "</span>:&nbsp;"+ customer.phoneNumber +"</td> \n" +
            "            <tr>\n" +
            "                <td style=\"font-size:11px\">\n" +
            "                    <span>"+ $("#labelAddress").val() +":&nbsp;\n" +
            "                    <span>\n" +
            "                    <span>" + customer.address +"</span>\n" +
            "                </td>\n" +
            "            </tr>\n" +
            "            </tbody>\n" +
            "        </table>\n";
    }
    return "";
}

function renderProductCartInvoiceOffline(orderItems, promotion, giftPromotion, type) {
    var stringBuilder = "<table style=\"width:98%;\" cellpadding=\"0\" cellspacing=\"0\"></tbody>\n";
    if(type === "invoice") {
        stringBuilder +=
            "        <tr>\n" +
            "            <td style=\"width:35%; border-top:1px solid black; border-bottom:1px solid black;\">" +
                            "<strong><span style=\"font-size:11px;\">"+ $("#labelUnitPrice").val() +"</span></strong></td>\n" +
            "            <td style=\"text-align:right;width:10%; border-top:1px solid black; border-bottom:1px solid black;\">" +
                            "<strong><span style=\"font-size:11px;\">" + $("#labelQuantity").val() + "</span></strong></td>\n" +
            "            <td style=\"text-align:right;width:20%; border-top:1px solid black; border-bottom:1px solid black;\"></td>\n" +
            "            <td style=\"text-align:right;width:33%; border-top:1px solid black; border-bottom:1px solid black;\">" +
                            "<strong><span style=\"font-size:11px;\">"+ $("#labelSubTotal").val() +"</span></strong>" +
                        "</td>\n" +
            "        </tr>\n";
            orderItems.forEach(function (item) {
                stringBuilder +=
                    "            <tr>\n" +
                    "                <td colspan=\"4\" style=\"padding-top:3px;\"><span style=\"font-size:12px;\">\n" + item.productName + " - " + item.skuName + "</span></td>\n" +
                    "            </tr>\n" +
                    "            <tr>\n" +
                    "                <td style=\"border-bottom:1px dashed black\"><span style=\"font-size:11px;\">\n" + formatNumber(item.salePrice) + "</span></td>\n" +
                    "                <td style=\"text-align:right;border-bottom:1px dashed black\"><span style=\"font-size:11px;\">\n" + item.quantity + "</span></td>\n" +
                    "                <td style=\"text-align:right;border-bottom:1px dashed black\">\n" + renderDiscountPercent(item.discountPrice, item.salePrice) + "</td>\n" +
                    "                <td style=\"text-align:right;border-bottom:1px dashed black\"><span style=\"font-size:11px;\">" + renderSubTotalPrice(item.salePrice, item.quantity) + "</span></td>\n" +
                    "            </tr>\n";
            });
            if(promotion){
                promotion.forEach(function (item) {
                    var quantity = $("#product_" + item.productOutletSkuId + "_quantity").text();
                    stringBuilder +=
                        "<tr>\n" +
                        "<td colspan=\"4\" style=\"padding-top:3px;\"><span style=\"font-size:12px;\">\n" + item.skuCode + " - " + item.productOutlet.product.name + "</span></td>\n" +
                        "</tr>\n" +
                        "<tr>\n" +
                        "<td style=\"border-bottom:1px dashed black\"><span style=\"font-size:11px;\">\n" + formatNumber(0) + "</span></td>\n" +
                        "<td colspan=\"1\" style=\"text-align:right;border-bottom:1px dashed black\"><span style=\"font-size:11px;\">" + quantity + "</span></td>\n" +
                        "<td colspan=\"2\" style=\"text-align:right;border-bottom:1px dashed black\"><span style=\"font-size:11px;\">" + formatNumber(0) + "</span></td>\n" +
                        "</tr>\n";
                });
            }
            if(giftPromotion){
                giftPromotion.forEach(function (item) {
                    var quantity = $("#gift_" + item.giftId + "_quantity").text();
                    console.log(item.giftId)
                    stringBuilder +=
                        "<tr>\n" +
                        "<td colspan=\"3\" style=\"padding-top:3px;\"><span style=\"font-size:12px;\">\n" + item.name + "</span></td>\n" +
                        "</tr>\n" +
                        "<tr>\n" +
                        "<td style=\"border-bottom:1px dashed black\"><span style=\"font-size:11px;\">\n" + formatNumber(0) + "</span></td>\n" +
                        "<td colspan=\"1\" style=\"text-align:right;border-bottom:1px dashed black\"><span style=\"font-size:11px;\">" + quantity + "</span></td>\n" +
                        "<td colspan=\"2\" style=\"text-align:right;border-bottom:1px dashed black\"><span style=\"font-size:11px;\">" + formatNumber(0) + "</span></td>\n" +
                        "</tr>\n" +
                        "<tr>\n" +

                        "</tr>\n";
                });
            } else {
                $('#productCart > tbody > tr.gifts').each(function (index, tr) {
                    stringBuilder +=
                        "<tr>\n" +
                        "<td colspan=\"3\" style=\"padding-top:3px;\"><span style=\"font-size:12px;\">\n" + $(this).find('td:eq(2)').html() + "</span></td>\n" +
                        "</tr>\n" +
                        "<tr>\n" +
                        "<td style=\"border-bottom:1px dashed black\"><span style=\"font-size:11px;\">\n" + formatNumber(0) + "</span></td>\n" +
                        "<td colspan=\"1\" style=\"text-align:right;border-bottom:1px dashed black\"><span style=\"font-size:11px;\">" + $(this).find('td:eq(4)').find('div').find('span').html() + "</span></td>\n" +
                        "<td colspan=\"2\" style=\"text-align:right;border-bottom:1px dashed black\"><span style=\"font-size:11px;\">" + formatNumber(0) + "</span></td>\n" +
                        "</tr>\n" +
                        "<tr>\n" +
                        "</tr>\n";
                });
            }
    } else { // type = pick list
        stringBuilder +=
        "<tr>\n" +
            "<td style=\"width:35%; border-top:1px solid black; border-bottom:1px solid black;\">" +
                "<strong><span style=\"font-size:11px;\">" + $("#labelSubCat").val() + "</span></strong>" +
            "</td>\n" +
            "<td style=\"text-align:right;width:30%;border-top:1px solid black; border-bottom:1px solid black;\">" +
                "<strong><span style=\"font-size:11px;\"></span></strong>" +
            "</td>\n" +
            "<td style=\"text-align:right;border-top:1px solid black; border-bottom:1px solid black;\">" +
                "<strong><span style=\"font-size:11px;\">" + $("#labelQuantity").val() + "</span></strong>" +
            "</td>\n" +
        "</tr>\n";
        if(orderItems.length > 0) {
            orderItems.forEach(function (item) {
                stringBuilder +=
                    "<tr>\n" +
                        "<td colspan=\"3\" style=\"padding-top:3px;\"><span style=\"font-size:12px;\">\n" + item.productName + " - " + item.skuName + "</span></td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                        "<td style=\"border-bottom:1px dashed black\"><span style=\"font-size:11px;\">" + (item.catGroupName ? item.catGroupName : '') + "</span></td>\n" +
                        "<td style=\"text-align:right;border-bottom:1px dashed black\"><span style=\"font-size:11px;\"></span></td>\n" +
                        "<td style=\"text-align:right;border-bottom:1px dashed black\"><span style=\"font-size:11px;\">" + item.quantity + "</span></td>\n" +
                    "</tr>\n";
            });
        }
    }
    return stringBuilder += "</tbody></table>";
}

function renderDiscountPercent(discountPrice, salePrice) {
    var percentDiscount = (discountPrice / (salePrice + discountPrice)) * 100;
    if(percentDiscount) {
        return "<span style=\"font-size:11px;\">\n" +
                    "<span> "+ - (Number(percentDiscount.toFixed(2))) +" %</span>" +
                "</span>\n";
    }
    return "<span></span>";
}

function renderSubTotalPrice(price, quantity) {
    return formatNumber(price * quantity);
}

function renderTotalInforCartInvoiceOffline(orderOutlet, customer, customerPaid) {
    var totalStoreDiscountPrice = orderOutlet.totalStoreDiscountPrice;
    var totalPromotionDiscountPrice = orderOutlet.totalPromotionDiscountPrice;
    var totalPrice = orderOutlet.totalPrice;
    var totalOriginalPrice = orderOutlet.totalOriginalPrice;
    if (totalOriginalPrice !== totalPrice
        && (totalPromotionDiscountPrice === 0 && totalStoreDiscountPrice===0)) {
        totalOriginalPrice = totalPrice;
    }
    return "<table border=\"0\" cellpadding=\"3\" style=\"border-collapse:collapse;width: 98%; margin-top:20px;\">\n" +
        "        <tfoot>\n" +
        "        <tr>\n" +
        "            <td style=\"text-align:right; font-weight:bold;font-size:11px; white-space:nowrap;\">"+ $("#labelTotalOriginalPrice").val() +":</td>\n" +
        "            <td style=\"text-align:right; font-weight:bold;font-size:11px;\">"+ formatNumber(totalOriginalPrice) +"</td>\n" +
        "        </tr>\n" +
        "        <tr>\n" +
        "            <td style=\"text-align:right; font-weight:bold;font-size:11px; white-space:nowrap;\">"+ $("#labelDiscount").val() +":</td>\n" +
        "            <td style=\"text-align:right; font-weight:bold;font-size:11px;\">" + formatNumber(totalStoreDiscountPrice) + "</td>\n" +
        "        </tr>\n" +
        "        <tr>\n" +
        "            <td style=\"text-align:right; font-weight:bold;font-size:11px; white-space:nowrap;\">"+ $("#labelPromotionInfor").val() +":</td>\n" +
        "            <td style=\"text-align:right; font-weight:bold;font-size:11px;\">" + formatNumber(totalPromotionDiscountPrice) + "</td>\n" +
        "        </tr>\n" +
        "        <tr>\n" +
        "            <td style=\"text-align:right; font-weight:bold;font-size:11px; white-space:nowrap;\">"+ $("#labelTotalPrice").val() +":</td>\n" +
        "            <td style=\"text-align:right; font-weight:bold;font-size:11px;\">" + formatNumber(totalPrice) + "</td>\n" +
        "        </tr>\n" +
        "        </tfoot>\n" +
        "    </table><hr/>\n" +
        "        <table border=\"0\" cellpadding=\"3\" style=\"border-collapse:collapse;width: 98%; margin-top:20px;\">\n" +
        "            <tfoot>\n" +
        "            <tr>\n" +
        "                <td style=\"text-align:right; font-weight:bold;font-size:11px; white-space:nowrap;\">"+ $("#labelCustomerPaid").val() +":</td>\n" +
        "                <td style=\"text-align:right; font-weight:bold;font-size:11px;\">"+ formatNumber(customerPaid) +"</td>\n" +
        "            </tr>\n" +
                        renderRefundInvoiceOffline(totalPrice, customerPaid) +
                        renderDebtInvoiceOffline(totalPrice, customerPaid, customer) +
        "            </tfoot>\n" +
        "        </table><hr/>\n" + renderLoyaltyPointInvoiceOffline(orderOutlet.loyaltyPoint, customer) +
        "    <table width=\"100%\" style=\"margin-top:20px\">\n" +
        "        <tbody>\n" +
        "        <tr>\n" +
        "            <td style=\"text-align:center; font-style:italic;font-size:11px;\">" + $("#labelSayGoodbye").val() + "</td>\n" +
        "        </tr>\n" +
        "        </tbody>\n" +
        "    </table>\n" +
        "</div>";
}

function renderRefundInvoiceOffline(totalPrice, customerPaid) {
    var refund = customerPaid - totalPrice;
    if(refund >= 0) {
        return "<tr>" +
            "<td style=\"text-align:right; font-weight:bold;font-size:11px; white-space:nowrap;\">" + $("#labelInvocieRefund").val() +":</td>" +
            "<td style=\"text-align:right; font-weight:bold;font-size:11px;\">" + formatNumber(refund) + "</td></tr>";
    }
    return "";
}

function renderDebtInvoiceOffline(totalPrice, customerPaid, customer) {
    var customerDebt = 0;
    var creditAmountdue = 0;
    if(!totalPrice) {
        totalPrice = 0;
    }
    if(customer) {
        customerDebt = customer.totalDebt !== null ? customer.totalDebt : 0;
        creditAmountdue = (customerPaid - totalPrice) >= 0 ? 0 : (totalPrice - customerPaid);
    }
    var debtFromOrder = 0;
    if((customerPaid - totalPrice) < 0) {
        debtFromOrder = -(customerPaid - totalPrice);
    }
    return "<tr>\n" +
                "<td style=\"text-align:right; font-weight:bold;font-size:11px; white-space:nowrap;\">"+ $("#labelNewDebt").val() +":</td>\n" +
                "<td style=\"text-align:right; font-weight:bold;font-size:11px;\">"+ formatNumber(debtFromOrder) +"</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
                "<td style=\"text-align:right; font-weight:bold;font-size:11px; white-space:nowrap;\">"+ $("#labelCreditAmountDue").val() + ":</td>\n" +
                "<td style=\"text-align:right; font-weight:bold;font-size:11px;\">"+ formatNumber(creditAmountdue) +"</td>\n" +
            "</tr>\n";
}

function renderLoyaltyPointInvoiceOffline(pointFromOrder, customer) {
    if(customer !== undefined && customer !== null && customer.customerName !== 'CURRENT_CUSTOMER') {
        return "<table border=\"0\" cellpadding=\"3\" style=\"border-collapse:collapse;width: 98%; margin-top:20px;\">" +
                    "<tfoot>" +
                        "<tr>" +
                            "<td style='text-align:right; font-weight:bold;font-size:11px; white-space:nowrap;'>" + $("#labelPoint").val() + ":</td>" +
                        "<td style=\"text-align:right; font-weight:bold;font-size:11px;\" id='lblPointFromOrder'>" + pointFromOrder + "</td>" +
                        "</tr>" +
                        "<tr>" +
                            "<td style=\"text-align:right; font-weight:bold;font-size:11px; white-space:nowrap;\">" + $("#labelRemainingPoint").val() + ":</td>" +
                            "<td style=\"text-align:right; font-weight:bold;font-size:11px;\" id='lblPointCustomer'>" + customer.point + "</td>" +
                        "</tr>" +
                    "</tfoot>" +
                "</table><hr/>"
    }
    return "";
}

/**
 * Convert timestamp to simple date -> 30/12/20xx
 * @param timeStamp
 * @returns {string}
 */
function convertTimeStamp2SimpleDate(timeStamp) {
    var date = new Date(timeStamp);
    return date.getDate() + "/" +(date.getMonth() + 1) + "/" + date.getFullYear() + " " + renderDoubleDigit(date.getHours()) + ":" + renderDoubleDigit(date.getMinutes());
}

function renderDoubleDigit(number) {
    if(number && number <= 9 ) {
        return '0' + number;
    }
    return number;
}

/**
 * Show popup sync invoice to server.
 */
function showPopUpSyncInvoice2Server() {
    // get all orderOutlet paid
    $.fn.getAllOrderOutletPaid(function (orderOutlets) {
        // render each row for orderOutlet
        renderRowOrderOutletPaid(orderOutlets);
    });
}

/**
 * Render information of OrderOutlet cached when no connection.
 * @param orderOutlets
 */
function renderRowOrderOutletPaid(orderOutlets) {
    var stringBuilder = "";
    if(orderOutlets.length < 0) {
        stringBuilder = "a";
    } else {
        var table = "<table class=\"table table-striped\">\n" +
                        "<thead>\n" +
                            "<tr>\n" +
                                "<td>" + $("#labelInvoiceCode").val() + "</td>\n" +
                                "<td>" + $("#labelTime").val() + "</td>\n" +
                                "<td>" + $("#labelValue").val() + "</td>\n" +
                                "<td></td>\n" +
                            "</tr>\n" +
                        "</thead>\n" +
                        "<tbody>";
        orderOutlets.forEach(function (item, index) {
            table += "<tr>\n" +
                        "<td>"+ item.code +"</td>\n" +
                        "<td>"+ convertTimeStamp2SimpleDate(item.createdDate)+"</td>\n" +
                        "<td>"+ formatNumber(item.totalPrice) +"</td>\n" +
                        "<td><a href='javascript:void(0)' onclick='syncInvoice2Server("+ item.code +")'>"+ $("#labelChoose").val() +"</a></td>\n" +
                    "</tr>"
        });
        table += "</tbody>\n" +
            "</table>";
        stringBuilder += table;
    }
    var modalSync = $("div#modalSyncInvocice");
    modalSync.find("div.modal-body").html(stringBuilder);
    modalSync.modal('show');
}

/**
 *  Sync invoice to server
 */
function syncInvoice2Server(orderCode) {
    $.fn.getOrderOutletPaidByCode(orderCode, function (orderOutletPaid) {
        if(orderOutletPaid) {
            var orderOutlet = [];
            orderOutlet.push(orderOutletPaid);
            $.ajax({
                url: syncInvoice2ServerUrl,
                method: "POST",
                contentType: 'application/json',
                dataType: 'json',
                data: JSON.stringify(orderOutlet),
            }).done(function (result) {
                $("#modalSyncInvocice").hide();
                if(result.result) {
                    // delete orderOutletPaid
                    $.fn.deleteOrderOutletPaid(orderOutletPaid.code, function (result) {
                        if(navigator.onLine) {
                            $("#preloader").show();
                            $("#status").show();
                            syncDataHideEvent(false, $("#selectSaleChanel").val());
                        } else {
                            showTotalItemOrderOutletPaid();
                            refreshPage();
                        }
                    });
                } else {
                    notification("error", messageErrorOccur, false);
                }
            });
        } else {
            notification("error", messageErrorOccur, false);
        }
    });
}

/**
 * Pay the pill and return orderOutlet object for online connection
 * @param orderOutletPaid
 * @param callback
 */
function payTheBill(orderOutletPaid, callback) {
    var orderOutlets = [];
    orderOutlets.push(orderOutletPaid);
    $.ajax({
        url: syncInvoice2ServerUrl,
        method: "POST",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(orderOutlets),
        timeout: 120000, // 2 minute
        error: function () {
            notification("error", messageErrorTimeOut, false);
        }
    }).done(function (response) {
        if(response.result) {
            callback(response.orderOutlet,response.orderItemsPromotion,response.giftPromotion);
        } else {
            if (response.errorList) {
                response.errorList.forEach(function (error) {
                    var message = '';
                    switch (error.code) {
                        case 'receiver.address.not.found' : {
                            message = $("#messageProductNotFound").val();
                            break;
                        }
                        case 'receiver.address.not.valid': {
                            message = $("#messageAddressNotValid").val();
                            break;
                        }
                        case 'customer.is.inactive': {
                            message = $("#messageCustomerInactive").val();
                            break;
                        }
                        case 'customer.not.found': {
                            message = $("#messageCustomerNotFound").val();
                            break;
                        }
                        case 'product.is.out.of.stock': {
                            message = $("#messageProductOutOfStock").val();
                            break;
                        }
                        case 'product.is.inactive' : {
                            message = $("#messageProductInactive").val();
                            break;
                        }
                        case 'product.not.sale.online.mode': {
                            message = $("#messageNotSaleOnline").val();
                            break;
                        }
                        case 'user.invalid': {
                            message = $("#messageUserInvalid").val();
                            break;
                        }
                        default: {
                            message = messageErrorOccur;
                            break;
                        }
                    }
                    notification("error", message, false);
                });
            } else {
                notification("error", messageErrorOccur, false);

            }
        }
    });
}

function getCustomerFromServer(customerId, outletId, callback) {
    $.ajax({
        url: getCustomerInforUrl,
        method: 'POST',
        data: {
            "customerId" : customerId,
            "outletId" : outletId
        },
        timeout: 60000,
        error: function () {
            notification('error', messageErrorOccur, false);
        }
    }).done(function (respond) {
        if(respond.result) {
            callback(respond.result);
        }
    })
}

function checkLinkedWithInventory() {
    if(navigator.onLine) {
        $.ajax({
            url: linkedWithInventoryUrl,
            method: "GET",
            timeout: 60000,
            error: function () {
                console.log("time out");
            }
        }).done(function (respond) {
            if(respond.result) {
                linkedWithInventory = true;
                $("#linkedWithInventory").val(true);
            } else {
                linkedWithInventory = false;
                $("#linkedWithInventory").val(false);
            }
        });
    }
}

function roundDecimalNumber(n) {
    var h = (n * 100) % 10;
    var result = n;
    switch(h) {
        case 1:
        case 2: {
            result = n - (h * 0.01);
            break;
        }
        case 3:
        case 4: {
            result = n + (5 - h) * 0.01;
            break;
        }
        case 6:
        case 7: {
            result = n - (h * 0.01) + 0.05;
            break;
        }
        case 8:
        case 9: {
            result = n + (10 - h) * 0.01;
            break;
        }
    }

    return result;
};

/**
 * Convert string value to number for calculate
 * @param stringValue
 * @returns {number}
 */
function formatStringToNumber(stringValue) {
    if(!stringValue) {
        return 0;
    }
    stringValue = stringValue.toString().replace(/\,/g, "");
    return Number(stringValue);
}

function realtimeInputFormartNumber(number, isQuantity, allowDecimal) {
    if(allowDecimal == null) {
        allowDecimal = true;
    }

    if(!number) {
        return 0;
    }
    number = formatStringToNumber(number);
    return formatNumber(number);
}

function calculateRefund(){
    var customerPaid = formatStringToNumber($("#inputCustomPaid").val().trim());
    var moneyMustPay = formatStringToNumber($("#moneyCustomerMustPay").text().trim());
    var refund = (customerPaid - moneyMustPay );
    $("#moneyCustomerIsDebited").text(refund < 0 ? formatNumber(-refund) : 0);
    $("#labelRefund").text(formatNumber(refund < 0 ? -refund : refund));
}

function calculateTotalPrice() {
    var storeDiscount = formatStringToNumber($("#inputDiscountByCash").val().trim());
    var promotionDiscount = formatStringToNumber($("#promtionDiscountByCash").text());
    var totalOriginalPrice = formatStringToNumber($("#totalOriginalPrice").text().trim());
    var deliveryPrice = formatStringToNumber($("#deliveryFee").text().trim());
    var tax = formatStringToNumber($("#inputTaxByCash").text().trim());
    var customerMustPay = totalOriginalPrice - storeDiscount - promotionDiscount  + tax + deliveryPrice;
    var moneyCustomerMustPay = formatNumber(customerMustPay < 0 ? 0 : customerMustPay);
    $("#moneyCustomerMustPay").text(moneyCustomerMustPay);
    $("#moneyCustomerIsDebited").text(moneyCustomerMustPay);
    $("#hiddenMoneyCustomerMustPay").val(formatStringToNumber(moneyCustomerMustPay));
}

function checkAcceptKey(key) {
    if ('#.###,##' === priceType ) {
        if(key == '.') {
            return false;
        } else if(key == ',') {
            return true;
        }
    } else {
        if(key == ',') {
            return false;
        } else if(key == '.') {
            return true;
        }
    }
    if(key >= '0' && key <= '9') {
        return true;
    }
    return keysAccept.includes(key);
}

function bindingEventAcceptKeyForInput() {
    // unit price
    $(".unit-price").on("keydown", function (e) {
        return checkAcceptKey(e.key);
    });
    // discount percent
    $("#inputDiscountByPercent").on("keydown", function (e) {
        return checkAcceptKey(e.key);
    });
    // discount price
    $("#inputDiscountByCash").on("keydown", function (e) {
        return checkAcceptKey(e.key);
    });
    // customer pay
    $("#inputCustomPaid").on("keydown", function (e) {
        return checkAcceptKey(e.key);
    });
}

function getOrderManagementView(url) {
    $("#preloader").show();
    $("#status").show();
    $.ajax({
        url: url,
        method: "GET",
        timeout: 90000,
        error: function () {
            console.log("#timeout");
            $("#preloader").hide();
            $("#status").hide();
        }
    }).done(function (respond) {
        $("#preloader").hide();
        $("#status").hide();
        $("#modalViewOrderHistory .modal-body").html(respond);
        $("#modalViewOrderHistory").modal("show");
        bindingEventsOnPopupOrder(url, "order_management");
        bindingEventViewOrdersOfCustomer();
    });
}

function bindingEventViewOrdersOfCustomer() {
    $(".btn-orders-customer").on("click", function (e) {
        e.preventDefault();
        var url = e.currentTarget.href;
        getOrdersOfCustomerView(url);
    })
}

function getOrdersOfCustomerView(url) {
    // hide order management modal
    $("modalViewOrderHistory").modal("hide");
    $("#preloader").show();
    $("#status").show();
    // fill data
    $.ajax({
        url: url,
        method: "GET",
        timeout: 90000,
        error: function () {
            console.log("#timeout");
            $("#preloader").hide();
            $("#status").hide();
            location.reload();
        }
    }).done(function (respond) {
        $("#preloader").hide();
        $("#status").hide();
        $("#modalViewOrderHistory .modal-body").html(respond);
        $("#modalViewOrderHistory").modal("show");
        bindingEventsOnPopupOrder(url, "order_of_customer");
    });
}

function editOrderView(orderOutletId) {
    // hide order management modal
    $("modalViewOrderHistory").modal("hide");
    $("#preloader").show();
    $("#status").show();
    // fill data
    $.ajax({
        url: $("#editOrderUrl").val(),
        method: "GET",
        data: {
            "orderOutletId": orderOutletId
        },
        timeout: 90000,
        error: function () {
            console.log("#timeout");
            $("#preloader").hide();
            $("#status").hide();
            location.reload();
        }
    }).done(function (respond) {
        $("#preloader").hide();
        $("#status").hide();
        $("#modalViewOrderHistory .modal-body").html(respond);
        $("#modalViewOrderHistory").modal("show");
        initOrderEditModal();
    });
}

// Add events for pop-up when shown
function bindingEventsOnPopupOrder(url, page) {
    pagingOnPopupOrder(url, page);
    filterOnPopupOrder(url, page);
    searchOnPopupOrder();
    bindingEventDatetimePicker();
}

function pagingOnPopupOrder(url, page) {
    $(".ajaxForm .pagination a.page-link").unbind('click').on('click', function (e) {
        e.preventDefault();
        var param = e.target.href.split("?")[1];
        url = url.split("?")[0];
        if(param){
            if(page === "order_management") {
                getOrderManagementView(url + "?" + param);
            } else if(page === "order_of_customer") {
                getOrdersOfCustomerView(url + "?" + param);
            }
        }
    });
}

function filterOnPopupOrder(url, page) {
    $("th.sorting a").unbind('click').on('click',function (e) {
        e.preventDefault();
        var getParam = e.currentTarget.href.split('?')[1];
        if(page === "order_management") {
            getOrderManagementView(url.split("?")[0] + "?" + getParam);
        } else if(page === "order_of_customer") {
            getOrdersOfCustomerView(url.split("?")[0] + "?" + getParam);
        }
    });
}

function searchOnPopupOrder() {
    $("#btnSearch").on("click", function (e) {
        e.preventDefault();
        $("#preloader").show();
        $("#status").show();
        $.ajax({
            url: $("#getOrdersHistoryUrl").val(),
            method: "GET",
            data : $("#orderForm").serialize(),
            timeout: 90000,
            error: function () {
                console.log("#timeout");
            }
        }).done(function (respond) {
            $("#preloader").hide();
            $("#status").hide();
            $("#modalViewOrderHistory .modal-body").html(respond);
            $("#modalViewOrderHistory").modal("show");
            bindingEventsOnPopupOrder($("#getOrdersHistoryUrl").val(), "order_management");
        })
    });

    $("#btnSearchOrderOfCustomer").on("click", function (e) {
       e.preventDefault();
        $("#preloader").show();
        $("#status").show();
        $.ajax({
            url: $("#getOrdersOfCustomer").val(),
            method: "GET",
            data : $("#orderOfCustomerForm").serialize(),
            timeout: 90000,
            error: function () {
                console.log("#timeout");
            }
        }).done(function (respond) {
            $("#preloader").hide();
            $("#status").hide();
            $("#modalViewOrderHistory .modal-body").html(respond);
            $("#modalViewOrderHistory").modal("show");
            bindingEventsOnPopupOrder($("#getOrdersOfCustomer").val(), "order_of_customer");
        })
    });
}

function bindingEventDatetimePicker() {
    $(".form-date").datepicker({
        format: "yyyy-mm-dd",
        autoclose : true,
        orientation: "bottom",
        todayHighlight:true
    });
}

function printOrderOnline(orderOutletId, type) {
    var url = $("#printOrderOnlineUrl").val();

    if(type === 'picklist') {
        url = $("#printPickListOnlineUrl").val();
    }

    // hide modal
    $("#modalViewOrderHistory").modal("toggle");

    $.ajax({
        url: url,
        method: "POST",
        data: {
            "orderOutletId": orderOutletId
        }
    }).done(function (response) {

        $("#invoiceReceipt").html(response);

        $(".printBox").printThis({
            importCSS: false,
            afterPrint: function () {
                $("#invoiceReceipt").empty();
                // show modal
                $("#modalViewOrderHistory").modal("toggle");
            }
        })
    })
}

function onChangeSaleChanel() {
    var saleChanel = $("#selectSaleChanel");
    saleChanel.on("change", function() {
        var productMode = '';
        if(saleChanel.val() === SALE_CHANEL[0]) {
            if(!navigator.onLine) {
                notification('error', $("#messageCheckConnection").val(),false);
                return;
            }
            productMode = "online";
            showInformationOnline();
        } else {
            productMode = "offline";
            showInformationOffline();
        }
        syncData(productMode);
    });
}

function showInformationOnline() {
    findAllDeliveryMethods();
    $("#btnPaySubmit").html($("#labelBtnOrder").val());
    $(".payment-offline").hide();
    $(".delivery-method").show();
    $("#deliveryFee").text(0);
    $(".delivery-fee").show();
}

function showInformationOffline() {
    $("#btnPaySubmit").html($("#labelBtnPayment").val());
    $(".payment-offline").show();
    $(".delivery-method").hide();
    $(".delivery-address").hide();
    $("#deliveryAddress").val('');
    $("#deliveryMethod").val('PICK_N_GO');
    $("#deliveryFee").text(0);
    $(".delivery-fee").hide();
}

function onSelectDeliveryMethod() {
    $("input[name='deliveryMethod']").on("click", function (e) {
        $("#deliveryMethod").val($(this).attr("value"));
        var deliveryPrice = 0;
        if($(this).attr("value") === "PICK_N_GO") {
            $(".delivery-address").hide();
        } else {
            $(".delivery-address").show();
            var currentDeliveryMethod = $("input[name='deliveryMethod']:checked").val();
            deliveryPrice = formatStringToNumber($("#priceOf" + currentDeliveryMethod).val());
        }

        $("#deliveryFee").text(formatNumber(deliveryPrice));
        var invoiceNumber = $(".invoice-list-nav ul li.active a").attr('href').split("#invoice")[1];
        loadOrderContent(invoiceNumber, false, false);
    });
}

function calculateDeliveryPrice(deliveryMethod) {
    var totalPrice = 0;

    if(!deliveryMethod) {
        deliveryMethod = 'PICK_N_GO';
    }

    var deliveryPrice = formatStringToNumber($("#priceOf" + deliveryMethod).val());
    var totalOriginalPrice = formatStringToNumber($("#totalOriginalPrice").text());
    var totalStoreDiscount = formatStringToNumber($("#inputDiscountByCash").val());
    var totalPromotionDiscount = formatStringToNumber($("#promtionDiscountByCash").text());

    totalPrice = totalOriginalPrice - (totalStoreDiscount + totalPromotionDiscount) + deliveryPrice;

    $("#deliveryFee").text(formatNumber(deliveryPrice));
    $("#moneyCustomerMustPay").text(formatNumber(totalPrice));
    $("#hiddenMoneyCustomerMustPay").text(formatNumber(totalPrice));

}

function findAllDeliveryMethods(currentDeliveryMethod, callback) {
    $("#preloader").show();
    $("#status").show();
    $.ajax({
        url: $("#getAllDeliveryWithPriceUrl").val(),
        data: {
            outletId : globalOutletId,
            address: $("#deliveryAddress").val(),
            longitude: $("#deliveryLongitude").val(),
            latitude: $("#deliveryLatitude").val(),
            customerId: $("#hiddenCustomerId").val()
        }
    }).done(function (response) {
        if(response.deliveryMethods) {
            renderDeliveryMethodWithPrice(response.deliveryMethods, response.grabService, currentDeliveryMethod);
        } else {
            $("#deliveryMethod").val('PICK_N_GO');
        }
        $("#preloader").hide();
        $("#status").hide();
        callback && callback();
    })
}

function renderDeliveryMethodWithPrice(deliveryMethods, grabService, currentDeliveryMethod) {
    var row = "";
    var classDisable = '';
    var grabErrorMessage = '';
    !currentDeliveryMethod && (currentDeliveryMethod = 'PICK_N_GO');
    deliveryMethods.forEach(function (deliveryMethod) {
        var isChecked = '';
        if(deliveryMethod.code === currentDeliveryMethod) {
            isChecked = 'checked';
        }

        if(deliveryMethod.code === 'SHIP_TO_HOME_EXPRESS' && grabService.message != null) { // error grab
            classDisable = 'disabled';
            grabErrorMessage = "<div><p class='text-danger'>"+ grabService.message +"</p></div>";
        }

        row += "<div class='form-check'>" +
                    "<input type='radio' class='form-check-input' name='deliveryMethod' value='"+ deliveryMethod.code +"' "+ classDisable +" "+ isChecked +">&nbsp;" +
                    "<span class='form-check-label'>" + $("#delivery" + deliveryMethod.code).val() + "</span>&nbsp;";

        if(deliveryMethod.code !== 'PICK_N_GO') {
            var distance = Number(deliveryMethod.distance).toFixed(2);
            var deliveryPrice = deliveryMethod.price;

            if(deliveryMethod.code === 'SHIP_TO_HOME_EXPRESS') {
                if(grabService.body && grabService.body.quotes[0].distance != null) {
                    distance = (grabService.body.quotes[0].distance / 1000);
                }
                if(grabService.body && grabService.body.quotes[0].amount != null) {
                    deliveryPrice = grabService.body.quotes[0].amount;
                }
            }

            row += "- <span>" + $("#labelDistance").val() + "</span>:&nbsp;<span>" + distance + "</span>";
            row += "<input type='hidden' id='priceOf"+ deliveryMethod.code +"' value='"+ deliveryPrice +"'/>";
        }
        classDisable = '';
        grabErrorMessage = '';

        row += "</div>";
        row += grabErrorMessage;

    });
    $(".delivery-method").html(row);
    $(".delivery-method").show();
    calculateDeliveryPrice(currentDeliveryMethod);
    onSelectDeliveryMethod();
}

function bindEventFormatNumber4Input() {
    var inputPrice = $(".number-price");
    inputPrice.focus(function(){
        $(this).select();
    });

    inputPrice.blur(function(){
        var number = $(this).val();
        if(number) {
            $(this).val(formatNumber(formatStringToNumber(number)));
        }
    });

    inputPrice.on("keydown", function (e) {
        return checkAcceptKey(e.key);
    });
    inputPrice.on("input", function (e) {
        e.preventDefault();
        if(e.target.value){
            e.target.value = realtimeInputFormartNumber(e.target.value, true);
        }else{
            e.target.value = "";
        }
    })
}

function initGoogleAddressSearch(){

    var inputs = document.getElementsByClassName("address");

    var autocompletes = [];

    for(var i = 0; i < inputs.length; i++) {
        var autocomplete = new google.maps.places.Autocomplete(inputs[i]);
        autocomplete.inputId = inputs[i].dataset.addressid;
        autocomplete.addListener('place_changed', fillAddress);
        autocompletes.push(autocomplete);
    }
}

function fillAddress() {

    var place = this.getPlace();
    var inputId = this.inputId;

    if (place && place.geometry && inputId) {
        $('.long_' + inputId).val(place.geometry.location.lng());
        $('.lat_' + inputId ).val(place.geometry.location.lat());

        if($("#selectSaleChanel").val() === SALE_CHANEL[0] && (!inputId || inputId == 0)) {
            findAllDeliveryMethods($("input[name='deliveryMethod']:checked").val(), function () {
                var invoiceNumber = $(".invoice-list-nav ul li.active a").attr('href').split("#invoice")[1];
                loadOrderContent(invoiceNumber, false, false, false, true);
            });
        }
    }
}

function checkValidReward(promotionCode, customerId, callback) {
    if(customerId && promotionCode) {
        $.fn.getCustomerReward(promotionCode, customerId, function (reward) {
            if(reward) {
                callback(reward);
            } else {
                callback(false);
            }
        })
    } else {
        callback(false);
    }
}
