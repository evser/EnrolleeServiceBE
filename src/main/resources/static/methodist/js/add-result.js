$(document).ready(function () {

    $.get("/methodist/university/currentId", function (universityId) {

        var selectSubject = $('#subject-select');
        var dateSelect = $('#date-select');

        $.get("/subjects/search/findByUniversityId?universityId=" + universityId, function (data) {
            selectSubject.empty();
            $.each(data._embedded.subjects, function (key, value) {
                selectSubject.append($("<option></option>")
                    .attr("value", value.id)
                    .text(value.name + " (" + value.code + ")"));
            });

            setDatesToSelect();

            selectSubject.change(function () {
                setDatesToSelect();
            });

            dateSelect.change(function () {
                setTestAssignmentsToTable();
            });

            $(".button-submit").click(function () {
                $.ajax({
                    type: "POST",
                    url: "/tests/result",
                    data: JSON.stringify(getTestAssignmentsResults()),
                    contentType: 'application/json'
                }).done(function (data) {
                    alert("Все результаты были успешно добавлены.");
                }).fail(function (data) {
                    alert("Пожалуйста, проверьте внимательно все заполненные поля!");
                });

                function getTestAssignmentsResults() {
                    var testAssignments = [];
                    var inputs = $(".table-row").find("input");

                    $.each($(".table-row"), function (idx, row) {
                        testAssignments.push({
                            testAssignmentId: row.attributes['testassignment_id'].value,
                            points: inputs[idx].value
                        });
                    });

                    return testAssignments;
                }
            });

            function setDatesToSelect() {
                dateSelect.empty();

                $.get("/tests/search/findByUniversityIdAndSubjectId?universityId=" + universityId + "&subjectId="
                    + selectSubject.find("option:selected").val(), function (testData) {
                    var tests = testData._embedded.tests;
                    var testDates = [];
                    for (var idx in tests) {
                        var testDate = tests[idx].date;
                        if (testDates.indexOf(testDate) === -1) {
                            testDates.push(testDate);
                        }
                    }

                    testDates.sort(function (a, b) {
                        return new Date(a) - new Date(b);
                    });

                    $.each(testDates, function (key, value) {
                        dateSelect.append($("<option></option>")
                            .attr("value", value)
                            .text(new Date(value).toLocaleString()));
                    });

                    setTestAssignmentsToTable();
                });
            }

            function setTestAssignmentsToTable() {
                $.get("/testAssignments/search/findByUniversityIdAndSubjectId?universityId=" + universityId + "&subjectId="
                    + selectSubject.find("option:selected").val(), function (testAssignmentsData) {

                    var testAssignments = testAssignmentsData._embedded.testAssignments;
                    var selectedDate = $('#date-select').find("option:selected").val();

                    testAssignments = testAssignments.filter(function (testAssignment) {
                        return testAssignment.test.date === selectedDate;
                    });

                    var resultTable = $("#result-table");
                    $(".table-row").remove();
                    $(".no-enrollee-msg").remove();

                    if (testAssignments.length === 0) {
                        $(".button-submit").prop('disabled', true);
                        resultTable.after($('<p class="no-enrollee-msg">Пока никто не записался на этот тест.</p>'))
                    } else {
                        $(".button-submit").prop('disabled', false);
                    }

                    $.each(testAssignments, function (idx, testAssignment) {
                        var testRow = $("<div class = 'row table-row'></div>");
                        testRow.attr('testAssignment_id', testAssignment.id);
                        resultTable.append(testRow);

                        var testCell = $("<div class='cell fullName'></div>");
                        var test = testAssignment.test;
                        var points = testAssignment.points;

                        var enrollee = testAssignment.enrollee;
                        var fullName = enrollee.lastName + " " + enrollee.firstName + " " + enrollee.middleName;
                        testRow.append(testCell.text(fullName));
                        testRow.append($("<input class='enrollee-result' name='points' type='text' pattern='\\d{1,3}' required/>").val(points ? points : '-'));
                    });
                });
            }
        });
    });
});