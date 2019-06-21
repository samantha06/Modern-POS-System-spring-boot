function getDate(){
    var d = new Date();

    var month = d.getMonth()+1;
    var day = d.getDate();

    var output = d.getFullYear() + '-' +
        (month<10 ? '0' : '') + month + '-' +
        (day<10 ? '0' : '') + day;
    return output;
}


$("#spnOrderDate").text(getDate());

loadOrderId();
loadAllCustomersIds();
$("#cmbCustomerId").val("");
loadAllItemCodes();
$("#cmbItemCode").val("");

function loadOrderId() {
    var ajaxConfig = {
        method: "GET",
        url: "http://localhost:8080/api/v1/orders",
        async: true
    }

    $.ajax(ajaxConfig).done(function (Order, textStatus, header) {
        var arrayIndex = parseInt(header.getResponseHeader("X-Count"))-1;
        var id = parseInt((Order[arrayIndex]).orderId) + 1;
        $("#spnOrderId").text(id);
        // console.log("working");
        // console.log(Order);
        // if (Order == null) {
        //     $("#spnOrderId").text("1");
        // } else {
        //     var id = parseInt(Order[Order.length - 1].orderId) + 1;
        //     $("#spnOrderId").text(id);
        // }
    }).fail(function (jqxhr, textStatus, errorMsg) {
        console.log(errorMsg);
    });
}

function loadAllCustomersIds() {
    var ajaxConfig = {
        method: "GET",
        url: "http://localhost:8080/api/v1/customers",
        async: true
    }

    $.ajax(ajaxConfig).done(function (customers) {
        $("#cmbCustomerId").append('<option value="0"></option>');
        for (var i = 0; i < customers.length; i++) {
            var id = customers[i].id;
            var html = '<option value="' + id + '">' + id + '</option>';
            $("#cmbCustomerId").append(html);
        }
    }).fail(function (jqxhr, textStatus, errorMsg) {
        console.log(errorMsg);
    });


    $("#cmbCustomerId").change(function () {
        if ($(this).val()==0){
            // alert("Please select Item");
        } else{
            var customerId = $(this).val();
            console.log(customerId);
            var ajaxConfig = {
                method: "GET",
                url: "http://localhost:8080/api/v1/customers/" + customerId,
                async: true
            }

            $.ajax(ajaxConfig).done(function (customers) {
                $("#lblCustomerName").text(customers.name);
            }).fail(function (jqxhr, textStatus, errorMsg) {
                console.log(errorMsg);
            });
        }

    });
}

function loadAllItemCodes() {
    var ajaxConfig = {
        method: "GET",
        url: "http://localhost:8080/api/v1/items",
        async: true
    }

    $.ajax(ajaxConfig).done(function (item) {
        $("#cmbItemCode").append('<option value="0"></option>');
        for (var i = 0; i < item.length; i++) {
            var code = item[i].code;
            var html = '<option value="' + code + '">' + code + '</option>';
            $("#cmbItemCode").append(html);
        }
    }).fail(function (jqxhr, textStatus, errorMsg) {
        console.log(errorMsg);
    });


    $("#cmbItemCode").change(function () {
        if ($("#cmbItemCode").val()==0){

        } else {
            var itemCode = $(this).val();
            var ajaxConfig = {
                method: "GET",
                url: "http://localhost:8080/api/v1/items/" + itemCode,
                async: true
            }

            $.ajax(ajaxConfig).done(function (item) {
                $("#lblDescription").text(item.description);
                var qtyOnHandNew = item.qtyOnHand;
                $("#lblQtyOnHand").text(qtyOnHandNew);
                $("#tblOrderDetails tbody tr").each(function () {
                    if($(this).find("td:first-child").text() == item.code){
                        var qtyInTable = parseInt($(this).find("td:nth-child(3)").text());
                        var qtyOnHandNew = item.qtyOnHand-qtyInTable;
                        $("#lblQtyOnHand").text(qtyOnHandNew);
                        console.log("working 12");
                        console.log(item.qtyOnHand-qtyInTable);
                        console.log(qtyOnHandNew);
                        $("#lblQtyOnHand").text(qtyOnHandNew);
                    }
                });

                $("#lblUnitPrice").text(item.unitPrice);
            }).fail(function (jqxhr, textStatus, errorMsg) {
                console.log(errorMsg);
            });
        }

    });
}

$("#txtQty").keypress(function (eventData) {
    if (eventData.which == 13) {


        var code = $("#cmbItemCode").val();
        var unitPrice = parseFloat($("#lblUnitPrice").text());
        var qty = parseInt($(this).val());
        var description = $("#lblDescription").text();
        var qtyOnHand = parseInt($("#lblQtyOnHand").text());

        if (qty <= 0 || qty > qtyOnHand) {
            alert("Invalid qty. Please enter valid Qty");
            $(this).select();
            return;
        }else{
            var disabled = $("#cmbItemCode").attr("disabled");
            if (disabled) {

                $("#tblOrderDetails tbody tr td:first-child").each(function () {
                    var itemCode = $(this).text();
                    if (itemCode == code) {

                        var oldQty = parseInt($(this).parents("tr").find("td:nth-child(3)").text());
                        $(this).parents("tr").find("td:nth-child(3)").text(qty);
                        $(this).parents("tr").find("td:nth-child(5)").text(qty * unitPrice);

                        // var item;

                        // var ajaxConfig ={
                        //     method: "GET",
                        //     url: "http://localhost:8080/Item/" + itemCode,
                        //     async: true
                        // }
                        //
                        // var ItemQtyOnHand = 0;
                        //
                        // $.ajax(ajaxConfig).done(function (Item) {
                        //     // item = Item;
                        //     ItemQtyOnHand = Item.qtyOnHand;
                        //     ItemQtyOnHand += oldQty;
                        //     ItemQtyOnHand = Item.qtyOnHand - qty;
                        // }).fail(function (jqxhr,textStatus,errorMsg) {
                        //     console.log(errorMsg);
                        // })

                        // item.qtyOnHand += oldQty;
                        // item.qtyOnHand = item.qtyOnHand - qty;
                    }
                });

            } else {
                var isExist = false;
                $("#tblOrderDetails tbody tr td:first-child").each(function () {
                    var itemCode = $(this).text();
                    if (itemCode == code) {
                        var oldQty = parseInt($(this).parents("tr").find("td:nth-child(3)").text());
                        $(this).parents("tr").find("td:nth-child(3)").text(oldQty + qty);
                        $(this).parents("tr").find("td:nth-child(5)").text(qty * unitPrice);
                        isExist = true;
                    }
                });
                if (!isExist) {
                    var html = '<tr>' +
                        '<td>' + code + '</td>' +
                        '<td>' + description + '</td>' +
                        '<td>' + qty + '</td>' +
                        '<td>' + unitPrice + '</td>' +
                        '<td>' + qty * unitPrice + '</td>' +
                        '<td><i class="fas fa-trash"></i></td>' +
                        '</tr>';
                    $("#tblOrderDetails tbody").append(html);

                    $("#tblOrderDetails tbody tr:last-child").click(function () {
                        var code = $(this).find("td:first-child").text();
                        var qty = $(this).find("td:nth-child(3)").text();
                        $("#txtQty").val(qty);
                        $("#cmbItemCode").val(code);
                        $("#cmbItemCode").trigger('change');
                        $("#cmbItemCode").attr('disabled', true);
                    });

                    $("#tblOrderDetails tbody tr:last-child td:last-child i").click(function () {

                        // var item = items.find(function (item) {
                        //     return item.code == code;
                        // });

                        // var qty = parseInt($(this).parents("tr").find("td:nth-child(3)").text());
                        // item.qtyOnHand += qty;

                        var row = $(this).parents("tr");
                        row.fadeOut(500, function () {
                            row.remove();
                            clearTextFields();
                            calculateTotal();
                        });

                    });
                }
                // var item = items.find(function (item) {
                //     return item.code == code;
                // });
                // item.qtyOnHand = item.qtyOnHand - qty;
            }

            clearTextFields();
            calculateTotal();
        }



    }
});

function clearTextFields() {
    $("#cmbItemCode").val("");
    $("#lblDescription").text("");
    $("#lblUnitPrice").text("");
    $("#lblQtyOnHand").text("");
    $("#txtQty").val("");
    $("#cmbItemCode").focus();
    $("#cmbItemCode").attr('disabled', false);
}

function calculateTotal() {
    var total = 0;
    $("#tblOrderDetails tbody tr td:nth-child(5)").each(function () {
        total += parseFloat($(this).text());
    });
    $("#spnTotal").text(total);
}

$("#btnPlaceOrder").click(function () {
    var array = [];
    $("#tblOrderDetails tbody tr").each(function () {
        var orderId = parseInt($("#spnOrderId").text());
        var itemCode = $(this).find("td:first-child").text();
        var description = $(this).find("td:nth-child(2)").text();
        var qty = parseInt($(this).find("td:nth-child(3)").text());
        var unitPrice = parseFloat($(this).find("td:nth-child(4)").text());

        array.push({ orderId: orderId, itemCode: itemCode, qty: qty,unitPrice: unitPrice });

    });
    
    var data = {
        orderId: parseInt($("#spnOrderId").text()),
        date: $("#spnOrderDate").text(),
        customerId: parseInt($("#cmbCustomerId").val()),
        orderDetails: array
    };

    var ajaxConfig = {
        method: "POST",
        url: "http://localhost:8080/api/v1/orders",
        async: true,
        data: JSON.stringify(data),
        contentType: "application/json"
    };

    $.ajax(ajaxConfig).done(function (response) {

        loadOrderId();
        console.log("working order and orderdetail save");

    }).fail(function (xhjqr,textStatus,errorMsg) {
       console.log(errorMsg);
    });

    var row = $("#tblOrderDetails").find("tbody tr");
    row.fadeOut(250, function () {
        row.remove();
    });

});


