$(document).ready(function () {
    $.get("/users/current", function (user) {
    	$("#user-name").val(user.lastName +  " " + user.firstName + " " + user.middleName);
    	$("#passport-num").val(user.passportId);
    	$("#phone-num").val(user.phoneNumber);
    	$("#email").val(user.email);
    });
    
    $("#change-pswd").submit(function(){
    	if($("#new-pass").val() === $("#new-pass-again").val()){
    		$.ajax({
			  url: "/users",
			  method: "PUT",
			  contentType: "application/json",
			  data: JSON.stringify({ newPassword : $("#new-pass").val(), oldPassword : $("#old-pass").val()})
    		}).done(function(){
    			alert("Пароль был изменен.");
    		})
    		.fail(function(){
    			alert("Ошибка при смене пароля. Попробуйте ещё раз.");
    		});
    	} else {
    		alert("Введенные пароли не совпадают.");
    	}
    	
    	$("#new-pass").val('');
    	$("#new-pass-again").val('');
    	$("#old-pass").val('');
    	return false;
    });
});