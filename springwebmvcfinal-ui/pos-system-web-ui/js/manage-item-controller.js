$(document).ready(function () {
    $("#txtItemCode").focus();
    loadAllItems();
});

var selectedRowUpdate = null;

function loadAllItems() {
    $("#tblItems tbody tr").remove();
    var ajaxConfig ={
        method: "GET",
        url: "http://localhost:8080/api/v1/items",
        async: true
    };

    $.ajax(ajaxConfig).done(function (Items) {
        for (var i = 0; i < Items.length; i++) {

            var html = "<tr>" +
                "<td>"+Items[i].code+"</td>" +
                "<td>"+Items[i].description+"</td>" +
                "<td>"+Items[i].qtyOnHand+"</td>" +
                "<td>"+Items[i].unitPrice+"</td>" +
                "<td><i class=\"fas fa-trash\"></i></td>" +
                "</tr>";
            $("#tblItems tbody").append(html);
            $("#tblItems tbody tr:last-child").off('click');
            $("#tblItems tbody tr:last-child i").off('click');
            attachDeleteItemEventListener();
            attachGetItemToInputField();

        }
        $('#tblItems').DataTable();
    }).fail(function (jqxhr,textStatus,errorMsg) {
        console.log(errorMsg);
    })

}

function attachGetItemToInputField() {

    $("#tblItems tbody tr").click(function () {
        var code = $(this).find("td:first-child").text();
        var description = $(this).find("td:nth-child(2)").text();
        var qtyOnHand = $(this).find("td:nth-child(3)").text();
        var unitPrice = $(this).find("td:nth-child(4)").text();

        $("#txtItemCode").val(code);
        $("#txtItemDescription").val(description);
        $("#txtItemQty").val(qtyOnHand);
        $("#txtItemUnitPrice").val(unitPrice);

        $("#txtItemCode").attr("disabled",true);
        $("#btnSave").text("Update");

        selectedRowUpdate =this.rowIndex;

    });
}

function attachDeleteItemEventListener() {
    $("#tblItems tbody i").off('click');
    $("#tblItems tbody i").click(function () {
        var code = $(this).parents("tr").find("td:first-child").text();
        if (confirm("Are you sure to delete this Item?")) {

            var ajaxConfig = {
                method: "DELETE",
                url: "http://localhost:8080/api/v1/items/"+code,
                async: true

            }

            var selectedRow = this;
            $.ajax(ajaxConfig).done(function (response) {

                var row = $(selectedRow).parents("tr");
                row.fadeOut(500, function () {
                    row.remove();
                });

            }).fail(function (jqxhr,textStatus,errorMsg) {
                alert("Can't delete this Item");
                console.log(errorMsg);

            });


        }
    });
}
$("#btnSave").click(function () {

    var code = $("#txtItemCode").val();
    var description = $("#txtItemDescription").val();
    var qtyOnHand = $("#txtItemQty").val();
    var unitPrice = $("#txtItemUnitPrice").val();

    var flag = true;

    if(unitPrice.trim().length==0){
        $("#txtItemUnitPrice").select();
        flag = false;
    }

    if (qtyOnHand.trim().length == 0) {
        $("#txtItemQty").select();
        flag = false;
    }

    if (description.trim().length == 0) {
        $("#txtItemDescription").select();
        flag = false;
    }

    if (code.trim().length == 0) {
        $("#txtItemCode").select();
        flag = false;
    }

    if ($("#btnSave").text() !== "Update") {
        $("#tblItems tbody tr td:first-child").each(function () {
            var itemCode = $(this).text();
            if (itemCode === code) {
                alert("Item code is already exists in the table");
                $("#txtItemCode").select();
                flag = false;

            }
        });
    }

    if (!flag) return;

    if ($("#btnSave").text() === "Update") {
        var data = {
            code: code,
            description: description,
            qtyOnHand: parseInt(qtyOnHand),
            unitPrice: parseFloat(unitPrice)
        };
        var ajaxConfig = {
            method: "PUT",
            url: "http://localhost:8080/api/v1/items/"+code,
            async: true,
            data: JSON.stringify(data),
            contentType: "application/json"
        }
        $.ajax(ajaxConfig).done(function (response) {
            selectedRowUpdate = $("table tr:nth-child(" + selectedRowUpdate +")");
            $(selectedRowUpdate).find("td:nth-child(2)").text(description);
            $(selectedRowUpdate).find("td:nth-child(3)").text(qtyOnHand);
            $(selectedRowUpdate).find("td:nth-child(4)").text(unitPrice);
            // $("#tblCustomers").refresh();
        }).fail(function (jqxhr,textStatus,errorMsg) {
            console.log(errorMsg);
        });
        // var customer = customers.find(function (customer) {
        //     return customer.id == customerId;
        // });
        //
        // customer.name = customerName;
        // customer.address = customerAddress;
        $("#btnSave").text("Save");
        $("#txtItemCode").attr("disabled",false);

        // for (var i = 0; i < customers.length ; i++) {
        //     if (customers[i].id == customerId){
        //
        //     }
        // }


    } else {
        var data = {
            code: code,
            description: description,
            qtyOnHand: parseInt(qtyOnHand),
            unitPrice: parseFloat(unitPrice)
        };
        var ajaxConfig = {
            method: "POST",
            url: "http://localhost:8080/api/v1/items",
            async: true,
            data: JSON.stringify(data),
            contentType: "application/json"
        }
        $.ajax(ajaxConfig).done(function (response) {
            var html = "<tr>" +
                "<td>" + code + "</td>" +
                "<td>" + description + "</td>" +
                "<td>" + qtyOnHand + "</td>" +
                "<td>" + unitPrice + "</td>" +
                "<td><i class=\"fas fa-trash\"></i></td>" +
                "</tr>";
            $("#tblItems tbody").append(html);

            //Event bind to new rows

            attachDeleteItemEventListener();
            attachGetItemToInputField();
        }).fail(function (jqxhr,textStatus,errorMsg) {
            console.log(errorMsg);
        });
    }

    // loadAllItems();

    $("#txtItemCode, #txtItemDescription, #txtItemQty, #txtItemUnitPrice").val("");
    $("#txtItemCode").focus();

});

$("#btnClear").click(function () {
    $("#txtItemCode, #txtItemDescription, #txtItemQty, #txtItemUnitPrice").val("");
    $("#txtItemCode").attr("disabled",false);
    $("#btnSave").text("Save");
});