$(document).ready(function () {
    $.fn.openIndexedDB().then(result => {
        if(result) {
            renderCartItems();
            generateProductItems();
        }
    });
    bindEvent2CheckAllAndCheckBoxRelate2CheckAll("checkAll");
    initCheckBoxCheckAll("checkAll");
});


function notification(message, isSuccess = true) {
    let formatMessage = "";
    if(isSuccess) {
        formatMessage = '<p class="text-center" style="font-size: 16px"><i class="fa fa-check-circle text-success"></i>&nbsp;'+ message +'</p>';
    } else {
        formatMessage = '<p class="text-center" style="font-size: 16px"><i class="fa fa-exclamation-triangle text-danger"></i>&nbsp;'+ message +'</p>';
    }

    var dialog = bootbox.dialog({
        message: formatMessage,
        closeButton: false
    });
    dialog.init(function () {
        setTimeout(function () {
            dialog.modal('hide');
        }, 1000);
    });
}

function renderCartItems() {
    // get item exists in indexedDB
    $.fn.getAllObjectStored("ShoppingCart")
        .then(existsItems => {
            // render html
            appendItemToCart(existsItems);
            // render number of item
            renderQuantityItemInCart(existsItems.length);
            // render cart items
            generateProductItems(existsItems);
        }).catch(err=>{
            renderQuantityItemInCart(0);
    });
}

function renderQuantityItemInCart(totalItem) {
    let cartNumberElement = $("ul.option li.option-cart span.cart_no");
    if(!totalItem || totalItem === 0) {
        cartNumberElement.hide();
    } else {
        cartNumberElement.show();
        cartNumberElement.text(totalItem);
    }
}

function appendItemToCart(items) {
    let ul = $("ul.option-cart-item");
    // reset cart
    ul.empty();
    let liTotal = '';
    let totalPrice = 0;
    let imageRemove = $("#imageRemove").val();

    items.forEach(function (item) {
        var li = '<li>' +
                    '<div class="cart-item">' +
                        '<div class="image"><img src="'+ item.skuDimension.sku.image +'" alt=""></div>' +
                        '<div class="item-description">' +
                            '<p class="name"> '+ item.skuDimension.sku.product.name + '</p>' +
                            '<p>Size: <span class="light-red">'+ item.skuDimension.size +'</span><br>Quantity: <span class="light-red">'+ item.quantity +'</span></p>' +
                        '</div>' +
                        '<div class="right text-right">' +
                            '<p class="price">'+ formatNumber(item.skuDimension.salePrice) +'</p>' +
                            '<a href="#" class="remove">' +
                                '<img src="'+ imageRemove +'" alt="remove">' +
                            '</a>' +
                        '</div>' +
                    '</div>' +
                '</li>';
        ul.append(li);
        totalPrice += Number(item.skuDimension.salePrice);
    })

    liTotal = '<li><span class="total">Total <strong>'+ formatNumber(totalPrice) +'</strong></span><button class="checkout" onClick="location.href=\'checkout.html\'">CheckOut</button></li>';
    ul.append(liTotal);
}

function showSpinner() {
    $('#preloader').show();
}

function hideSpinner() {
    $('#preloader').hide();
}

/**
 * Format number to string readable
 * @param number
 * @returns {string}
 */
function formatNumber(number) {
    if(!number || number === 0) {
        return 0;
    }
    return number.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,');
}

/**
 * Format string to number
 */
function formatStringToNumber(stringNumber) {
    if(!stringNumber || stringNumber == 0) {
        return 0;
    }
    stringNumber = stringNumber.toString().replace(/\./g, "").replace(",", ".");
    return Number(stringNumber);
}


function generateProductItems(cartItems) {
    var table = $("div.table-product-items");
    if(table && cartItems) {
        cartItems.forEach(function (item) {
            let row = renderRowItem(item);
            table.append(row);
        })
    }
}

function renderRowItem(item) {
    return "<div class='cart-item-content'>" +
        "       <div class='row'>" +
        "           <div class='col-1'>" +
        "               <input type='checkbox' class='form-control'>" +
        "           </div>" +
        "           <div class='col-10'>" +
        "               <div class='row'>" +
        "                   <div class='col-7'>" +
        "                       <div class='row'>" +
        "                           <div class='col-2'>" +
        "                               <img src='"+ item.skuDimension.sku.image +"' alt='image' onerror='this.error=null;this.src="+ $("#defaultImage").val() +"'>" +
        "                           </div>" +
        "                           <div class='col-8'>" +
        "                               <span>"+ item.skuDimension.sku.product.name +"</span>" +
        "                           </div>" +
        "                           <div class='col-2'>" +
        "                               <span>"+ item.skuDimension.size +"</span>" +
        "                           </div>" +
        "                       </div>" +
        "                       </div>" +
                            "<div class='col-5'></div>" +
        "               </div>" +
        "           </div>" +
        "           <div class='col-1'>" +
        "               <span>"+ $("#labelDelete").val() +"</span>" +
        "           </div>" +
        "   </div>" +
        "</div>"
}

function initCheckBoxCheckAll(id){
    if($("#" + id).closest('table').find('tbody input[type="checkbox"]:checked').length > 0){
        if($("#" + id).closest('table').find('tbody input[type="checkbox"]:checked').length
            == $("#" + id).closest('table').find('tbody input[type="checkbox"]').length) {
            $("#" + id).prop('checked', true);
        } else {
            $("#" + id).prop('checked', false);
        }
    }else{
        $("#" + id).prop('checked', false);
    }
}

function bindEvent2CheckAllAndCheckBoxRelate2CheckAll(id){
    bindEventCheckAllCheckBox(id);
    bindEventEnableOrDisableCheckBoxAll(id);
}


function bindEventEnableOrDisableCheckBoxAll(id){
    $("#" + id).closest("table").find("tbody input[type=checkbox]").each(function(){
        var tableObj =  $("#" + id).closest("table");
        $(this).on('change', function () {
            if($(tableObj).find("tbody input[type=checkbox]:checked").length == $(tableObj).find("tbody input[type=checkbox]").length){
                $('#' + id).prop('checked', true);
            }else{
                $('#' + id).prop('checked', false);
            }
        });
    });
}

function bindEventCheckAllCheckBox(id){
    $("#" + id).change(function() {
        if(this.checked) {
            $(this).closest("table").find("input[type=checkbox]").prop('checked', true);
        }else{
            $(this).closest("table").find("input[type=checkbox]").prop('checked', false);
        }
    });
}