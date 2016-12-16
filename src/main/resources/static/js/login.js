$(document).ready(function () {

    function checkAutoCompleteInput() {
        var formInputs = $('.form').find('input, textarea');

        $.each(formInputs, function (idx, input) {
            if (input.value) {
                var $this = $(input),
                    label = $this.prev('label');

                if ($this.val() === '') {
                    label.removeClass('active highlight');
                } else {
                    label.addClass('active highlight');
                }
            }
        });
    }

    setTimeout(function () {
        checkAutoCompleteInput();
    }, 1500);

    $(".tab").click(function () {
        checkAutoCompleteInput();
    });

    $('.form').find('input, textarea').on('keyup blur focus', function (e) {

        var $this = $(this),
            label = $this.prev('label');

        if (e.type === 'keyup') {
            if ($this.val() === '') {
                label.removeClass('active highlight');
            } else {
                label.addClass('active highlight');
            }
        } else if (e.type === 'blur') {
            if ($this.val() === '') {
                label.removeClass('active highlight');
            } else {
                label.removeClass('highlight');
            }
        } else if (e.type === 'focus') {

            if ($this.val() === '') {
                label.removeClass('highlight');
            }
            else if ($this.val() !== '') {
                label.addClass('highlight');
            }
        }

    });

    $('.tab a').on('click', function (e) {

        e.preventDefault();

        $(this).parent().addClass('active');
        $(this).parent().siblings().removeClass('active');

        target = $(this).attr('href');

        $('.tab-content > div').not(target).hide();

        $(target).fadeIn(600);

    });


    $("#form-signup").submit(function () {
        var functionData = {};
        $("#form-signup").serializeArray().map(function (x) {
            functionData[x.name] = x.value;
        });

        $.ajax({
            url: "/signup",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(functionData)
        }).done(function () {
            window.location.replace("/enrollee/news.html");
        }).fail(function (data) {
            alert(data.responseJSON.message);
        });

        return false;
    });
});
