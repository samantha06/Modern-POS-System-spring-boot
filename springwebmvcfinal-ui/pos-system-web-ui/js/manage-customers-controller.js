$(document).ready(function () {
    $("#txtCustomerId").focus();
    loadAllCustomers();

});

var selectedRowUpdate = null;

function loadAllCustomers() {
    $("#tblCustomers tbody tr").remove();
    var ajaxConfig = {
        method: "GET",
        url: "http://localhost:8080//api/v1/customers",
        async: true
    };

    $.ajax(ajaxConfig).done(function (customers) {

        for (var i = 0; i < customers.length; i++) {
            var html = "<tr>" +
                "<td>" + customers[i].id + "</td>" +
                "<td>" + customers[i].name + "</td>" +
                "<td>" + customers[i].address + "</td>" +
                "<td><i class=\"fas fa-trash\"></i></td>" +
                "</tr>";
            $("#tblCustomers tbody").append(html);
            $("#tblCustomers tbody tr:last-child").off('click');
            $("#tblCustomers tbody tr:last-child i").off('click');
            attachDeleteCustomerEventListener();
           attachGetCustomerToInputField();


        }
        $('#tblCustomers').DataTable();
        console.log(customers);
    }).fail(function (jqxhr,textStatus,errorMsg) {
        console.log(errorMsg);
    });




}

function attachGetCustomerToInputField(){

    $("#tblCustomers tbody tr:last-child").click(function () {
        var id = $(this).find("td:first-child").text();
        var name = $(this).find("td:nth-child(2)").text();
        var address = $(this).find("td:nth-child(3)").text();

        $("#txtCustomerId").val(id);
        $("#txtCustomerName").val(name);
        $("#txtCustomerAddress").val(address);

        $("#txtCustomerId").attr("disabled", true);
        $("#btnSave").text("Update");

        selectedRowUpdate =this.rowIndex;
        console.log(selectedRowUpdate);


    });
}

function attachDeleteCustomerEventListener() {

    $("#tblCustomers tbody tr:last-child i").click(function () {
        var id = parseInt($(this).parents("tr").find("td:first-child").text());
        if (confirm("Are you sure to delete this customer?")) {

            var ajaxConfig = {
                method: "DELETE",
                url: "http://localhost:8080/api/v1/customers/"+ id ,
                async: true
            };
            var selectedRow = this;
            $.ajax(ajaxConfig).done(function (response) {
                var row = $(selectedRow).parents("tr");
                row.fadeOut(500, function () {
                    row.remove();
                });

                $("#txtCustomerId, #txtCustomerName, #txtCustomerAddress").val("");


            }).fail(function (jqxhr,textStatus,errorMsg) {
                alert(jqxhr.responseText);
                console.log(errorMsg);
            });


        }
    });
}

$("#btnSave").click(function () {

    var customerId = $("#txtCustomerId").val();
    var customerName = $("#txtCustomerName").val();
    var customerAddress = $("#txtCustomerAddress").val();

    var flag = true;

    if (customerAddress.trim().length == 0) {
        $("#txtCustomerAddress").select();
        flag = false;
    }

    if (customerName.trim().length == 0) {
        $("#txtCustomerName").select();
        flag = false;
    }

    if (customerId.trim().length == 0) {
        $("#txtCustomerId").select();
        flag = false;
    }

    if ($("#btnSave").text() !== "Update") {
        $("#tblCustomers tbody tr td:first-child").each(function () {
            var id = $(this).text();
            if (id === customerId) {
                alert("Customer Id is already exists in the table");
                $("#txtCustomerId").select();
                flag = false;

            }
        });
    }

    if (!flag) return;

    if ($("#btnSave").text() === "Update") {
        var data = {
            id: parseInt(customerId),
            name: customerName,
            address: customerAddress
        };
        var ajaxConfig = {
            method: "PUT",
            url: "http://localhost:8080/api/v1/customers/"+ customerId,
            async: true,
            data: JSON.stringify(data),
            contentType: "application/json"
        }
        $.ajax(ajaxConfig).done(function (response) {
           selectedRowUpdate = $("table tr:nth-child(" + selectedRowUpdate +")");
            $(selectedRowUpdate).find("td:nth-child(2)").text(customerName);
            $(selectedRowUpdate).find("td:nth-child(3)").text(customerAddress);
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
        $("#txtCustomerId").attr("disabled",false);

        // for (var i = 0; i < customers.length ; i++) {
        //     if (customers[i].id == customerId){
        //
        //     }
        // }


    } else {
        var data = {
            id: parseInt(customerId),
            name: customerName,
            address: customerAddress
        };
        var ajaxConfig = {
            method: "POST",
            url: "http://localhost:8080/api/v1/customers",
            async: true,
            data: JSON.stringify(data),
            contentType: "application/json"
        }
        $.ajax(ajaxConfig).done(function (response) {
            var html = "<tr>" +
                "<td>" + customerId + "</td>" +
                "<td>" + customerName + "</td>" +
                "<td>" + customerAddress + "</td>" +
                "<td><i class=\"fas fa-trash\"></i></td>" +
                "</tr>";
            $("#tblCustomers tbody").append(html);

            //Event bind to new rows

            attachDeleteCustomerEventListener();
            attachGetCustomerToInputField();
        }).fail(function (jqxhr,textStatus,errorMsg) {
           console.log(errorMsg);
        });
    }

    // loadAllCustomers();

    $("#txtCustomerId, #txtCustomerName, #txtCustomerAddress").val("");
    $("#txtCustomerId").focus();

});

$("#btnClear").click(function () {
    $("#txtCustomerId, #txtCustomerName, #txtCustomerAddress").val("");
    $("#txtCustomerId").attr("disabled",false);
    $("#btnSave").text("Save");
});
