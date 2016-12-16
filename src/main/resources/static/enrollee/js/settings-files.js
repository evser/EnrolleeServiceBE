$(document).ready(function () {
    $.get("/users/current", function (user) {
        var userId = user.id;

        $.get("/documents/search/findByEnrolleeId?enrolleeId=" + userId, function (data) {
            setHasDocuments(data.embedded.documents.length);
        });


        $(".passport-file").change(function () {
            if (this.value) {
                $.ajax({
                    type: "POST",
                    url: "/documents",
                    data: JSON.stringify({enrollee: {id: userId}, type: "PASSPORT"}),
                    contentType: 'application/json'
                }).done(function (data) {
                    alert("Файл был загружен.");
                    setHasDocuments(true);
                }).fail(function (data) {
                    alert("Произошла ошибка. Попробуйте пожалуйста ещё раз.");
                });

            }
        });

        function setHasDocuments(indicator) {
            var divIndicator = $("div.doc-indicator");
            divIndicator.empty();
            if (indicator) {
                divIndicator.append($('<i class="fa fa-plus-circle doc-indicator" aria-hidden="true"></i>'));
            } else {
                divIndicator.append($('<i class="fa fa-minus-circle doc-indicator" aria-hidden="true"></i>'));
            }
        }
    });
});