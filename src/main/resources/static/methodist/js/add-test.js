$(document).ready(function () {

$('#date-picker').appendDtpicker();

$.get("/methodist/university/currentId", function(universityId) {
	
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

        $.ajax({
            method: "POST",
            url: "/tests",
            contentType: 'application/json',
            data: JSON.stringify({date: new Date($("#date-picker").val()).toISOString(), roomId: selectRoom.find("option:selected").val(), subjectId: selectSubject.find("option:selected").val(), type: $('#type-select').find("option:selected").val()})
        }).done(function () {
            alert("Тест был успешно добавлен.");
        }).fail(function () {
            alert("Это комната уже занята.");
        });
    });
	
});
});