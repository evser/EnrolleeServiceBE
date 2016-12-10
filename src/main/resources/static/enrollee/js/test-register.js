$(document).ready(function () {
    $.get("/universities", function (data) {
        $.each(data._embedded.universities, function (key, value) {
            $('#university-select').append($('<option></option>')
                .attr("value", value.id)
                .text(value.name + " (" + value.address + ")"));
        });

        extractSubjectByUniversity();
    });

    $("#university-select").change(function () {
        extractSubjectByUniversity();
    });


    $("#subject-select").change(function () {
        extractTestsBySubjectByUniversity();
    });

    $(".button-submit").click(function () {
        var testSelect = $("#test-select");

        $.ajax({
            method: "PUT",
            url: "/tests/assign/" + testSelect.find("option:selected")[0].value,
        }).done(function () {
            alert("Вы были успешно записаны на тест.");
        }).fail(function () {
            alert("Вы были уже записаны на этот тест.");
        });
    });


    function extractSubjectByUniversity() {
        var selectedUniversity = $("#university-select").find("option:selected");
        var selectedSubject = $('#subject-select');

        $.get("/subjects/search/findByUniversityId?universityId=" + selectedUniversity[0].value, function (data) {
            selectedSubject.empty();
            $.each(data._embedded.subjects, function (key, value) {
                selectedSubject.append($("<option></option>")
                    .attr("value", value.id)
                    .text(value.name + " (" + value.code + ")"));
            });

            extractTestsBySubjectByUniversity();
        });
    }

    function extractTestsBySubjectByUniversity() {
        var selectedUniversity = $("#university-select").find("option:selected");
        var selectedSubject = $("#subject-select").find("option:selected");


        $.get("/tests/search/findByUniversityIdAndSubjectId?universityId=" + selectedUniversity[0].value + "&subjectId=" + selectedSubject[0].value, function (data) {
            var testSelect = $('#test-select');
            var submitButton = $(".button-submit");
            testSelect.empty();
            testSelect.prop('disabled', false);
            submitButton.prop('disabled', false);

            var tests = data._embedded.tests;
            $.each(tests, function (key, value) {
                testSelect.append($("<option></option>")
                    .attr("value", value.id)
                    .text(new Date(value.date).toLocaleString() + " (" + value.type + ")"));
            });

            if (!tests.length) {
                testSelect.prop('disabled', true);
                submitButton.prop('disabled', true);
                testSelect.append($("<option></option>")
                    .text("Нет доступных тестов"));
            }
        });
    }

});