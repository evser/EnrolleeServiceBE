$(document).ready(function() {

  $('header nav a').click(function(e) {
    
    $('header nav a.active').removeClass('active');
    if(this.className !== 'link-logout') {
        $(this).addClass('active');
    }

  });
  
});