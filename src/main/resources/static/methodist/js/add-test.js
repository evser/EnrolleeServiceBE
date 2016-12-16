$(document).ready(function () {

    $('#date-picker').appendDtpicker();

    $.get("/methodist/university/currentId", function (universityId) {

        var selectSubject = $('#subject-select');
        $.get("/subjects/search/findByUniversityId?universityId=" + universityId, function (data) {
            selectSubject.empty();
            $.each(data._embedded.subjects, function (key, value) {
                selectSubject.append($("<option></option>")
                    .attr("value", value.id)
                    .text(value.name + " (" + value.code + ")"));
            });
        });

        var selectRoom = $('#room-select');

        $.get("/rooms/search/findByUniversityId?universityId=" + universityId, function (data) {

            selectRoom.empty();
            $.each(data._embedded.rooms, function (key, value) {
                selectRoom.append($("<option></option>")
                    .attr("value", value.id)
                    .text(value.number));
            });
        });

        $(".button-submit").click(function () {
            var testSelect = $("#test-select");

            // var tzoffset = (new Date()).getTimezoneOffset() * 60000; //offset in milliseconds
            var testDate = new Date($("#date-picker").val()).toISOString().slice(0, -5);

            $.ajax({
                method: "POST",
                url: "/tests/add",
                contentType: 'application/json',
                data: JSON.stringify({
                    date: testDate,
                    roomId: selectRoom.find("option:selected").val(),
                    subjectId: selectSubject.find("option:selected").val(),
                    type: $('#type-select').find("option:selected").val()
                })
            }).done(function () {
                alert("Тест был успешно добавлен.");
            }).fail(function () {
                alert("Это комната уже занята.");
            });
        });

    });
});