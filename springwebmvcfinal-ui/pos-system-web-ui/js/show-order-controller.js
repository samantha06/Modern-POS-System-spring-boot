$(document).ready(function () {
    loadAllCustomers();
});


function loadAllCustomers() {
    $("#tblOrders tbody tr").remove();
    var ajaxConfig = {
        method: "GET",
        url: "http://localhost:8080//api/v1/orders/show",
        async: true
    };

    $.ajax(ajaxConfig).done(function (Orders) {

        for (var i = 0; i < Orders.length; i++) {
            var html = "<tr>" +
                "<td>" + Orders[i].orderId + "</td>" +
                "<td>" + Orders[i].customerId + "</td>" +
                "<td>" + Orders[i].customerName + "</td>" +
                "<td>" + Orders[i].date + "</td>" +
                "<td>" + Orders[i].total + "</td>" +
                // "<td><i class=\"fas fa-eye\"></i></td>" +
                "</tr>";
            $("#tblOrders tbody").append(html);
            // $("#tblCustomers tbody tr:last-child i").off('click');
            // attachViewEventListener();


        }
        $('#tblOrders').DataTable();
    }).fail(function (jqxhr,textStatus,errorMsg) {
        console.log(errorMsg);
    });




}