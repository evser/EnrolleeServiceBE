$(document).ready(function () {
    $.get("/users/current", function (user) {
        var userId = user.id;

        $.get("/testAssignments/search/findByEnrolleeId?enrolleeId=" + userId, function (data) {
            var resultTable = $("#result-table");

            var testAssignments = data._embedded.testAssignments;
            testAssignments.sort(function (a, b) {
                return new Date(a.test.date) - new Date(b.test.date);
            });
            $.each(testAssignments, function (idx, testAssignment) {
                var testRow = $("<div class = 'row'></div>");
                resultTable.append(testRow);

                var testCell = $("<div class='cell'></div>");
                var test = testAssignment.test;
                var room = test.room;
                var points = testAssignment.points;
                var subject = test.subject;
                var university = room.university;

                testRow.append(testCell.text(test.type));
                testRow.append(testCell.clone().text(subject.name + " (" + subject.code + ")"));
                testRow.append(testCell.clone().text(new Date(test.date).toLocaleString()));
                testRow.append(testCell.clone().text(university.name + " (" + university.address + "), " + room.number));
                testRow.append(testCell.clone().text(points ? points : '-'));
            });
        });
    });
});