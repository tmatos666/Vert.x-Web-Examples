$(document).ready(function () {
    $("#invoke").click(function (e) {
        //alert();
        var n = $("#name").val() || "World";
        $.getJSON("/api/greeting?name=" + n, function (res) {
            $("#greeting-result").text(JSON.stringify(res));
        });
        e.preventDefault();
    });
});
