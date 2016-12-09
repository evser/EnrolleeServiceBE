$(document).ready(function() {

    $('header nav a').click(function(e) {

        $('header nav a.active').removeClass('active');
        if(this.className !== 'logout') {
            $(this).addClass('active');
        }
    });

    $('.logout').click(function(e ) {

        $.post( "/logout", function (data, textStatus, jqXHR) {
            window.location.replace("/");
        });
    });
});