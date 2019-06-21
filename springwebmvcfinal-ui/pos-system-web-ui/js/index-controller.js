var ajaxConfigCustomer = {
    method: "GET",
    url: "http://localhost:8080/api/v1/customers",
    async: true
}

$.ajax(ajaxConfigCustomer).done(function (Customer,textStatus,header) {
    var count = header.getResponseHeader("X-Count");
    $("#customers h1").text(count)

}).fail(function (xqjhr,textStatus,errorMsg) {
    console.log(errorMsg);
});

var ajaxConfigItem = {
    method: "GET",
    url: "http://localhost:8080/api/v1/items",
    async: true
}

$.ajax(ajaxConfigItem).done(function (Item,textStatus,header) {
    var count = header.getResponseHeader("X-Count");
    $("#items h1").text(count)

}).fail(function (xqjhr,textStatus,errorMsg) {
    console.log(errorMsg);
});

var ajaxConfigOrder = {
    method: "GET",
    url: "http://localhost:8080/api/v1/orders",
    async: true
}

$.ajax(ajaxConfigOrder).done(function (Order,textStatus,header) {
    var count = header.getResponseHeader("X-Count");
    $("#orders h1").text(count)

}).fail(function (xqjhr,textStatus,errorMsg) {
    console.log(errorMsg);
});