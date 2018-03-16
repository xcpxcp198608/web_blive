$(function () {

    var baseUrl = "https://"+location.host+"/blive";
    var jLoading = $('#loading');

    $('#a_sign_up').click(function(){
        $('#div_sign_in').css('display', 'none');
        $('#div_sign_up').css('display', 'block');
    });

    $('#close_sign_up').click(function(){
        $('#div_sign_up').css('display', 'none');
        $('#div_sign_in').css('display', 'block');
    });


    $('#a_reset').click(function(){
        $('#div_sign_in').css('display', 'none');
        $('#div_reset').css('display', 'block');
    });

    $('#close_reset').click(function(){
        $('#div_reset').css('display', 'none');
        $('#div_sign_in').css('display', 'block');
    });

    $('#btSignIn').click(function(){
        var jErrorMessage = $('#error_message_sign_in');
        var username = $('#sign_in_username').val();
        var password = $('#sign_in_password').val();
        if(username.length <= 0){
            jErrorMessage.html('username input error');
            return
        }
        if(password.length <= 0){
            jErrorMessage.html('password input error');
            return
        }
        jErrorMessage.html(' ');
        jErrorMessage.css('display', 'none');
        $.ajax({
            type: "POST",
            url: baseUrl + "/users/signin",
            data: {"username": username, "password": password},
            dataType: "json",
            beforeSend:function () {
                jLoading.css('display', 'block');
            },
            success: function (response) {
                var data = eval(response);
                if(data.code === 200){
                    window.open(baseUrl +"/users/home", "_self")
                }else{
                    jLoading.css('display', 'none');
                    jErrorMessage.html(data.message);
                    jErrorMessage.css('display', 'block');
                }
            },
            error:function (error) {
                jLoading.css('display', 'none');
                console.log(error);
            }
        })
    });


    $('#btSignUp').click(function(){
        var jErrorMessage = $('#error_message_sign_up');
        var username = $('#sign_up_username').val();
        var password = $('#sign_up_password').val();
        var password1 = $('#sign_up_password1').val();
        var email = $('#sign_up_email').val();
        var phone = $('#sign_up_phone').val();
        if(username.length <= 0){
            jErrorMessage.html('username input error');
            return
        }
        if(password.length <= 0){
            jErrorMessage.html('password input error');
            return
        }
        if(password1.length <= 0){
            jErrorMessage.html('password1 input error');
            return
        }
        if(password !== password1){
            jErrorMessage.html('password input no match');
            return
        }
        if(email.length <= 0){
            jErrorMessage.html('email input error');
            return
        }
        if(phone.length <= 0){
            jErrorMessage.html('phone input error');
            return
        }
        jErrorMessage.html(' ');
        jErrorMessage.css('display', 'none');
        $.ajax({
            type: "POST",
            url: baseUrl + "/user/signup",
            data: {"username": username, "password": password, "email": email, "phone": phone},
            dataType: "json",
            beforeSend:function () {
                jLoading.css('display', 'block');
            },
            success: function (response) {
                console.log(response);
                jLoading.css('display', 'none');
                if(response.code == 200){
                    $('#div_sign_up').css('display', 'none');
                    $('#div_sign_in').css('display', 'block');
                    $('#notice_message').html(response.message);
                    $('#notice').css('display', 'block');
                    setTimeout(function(){
                        $('#notice_message').html("");
                        $('#notice').css('display', 'none');
                    }, 3000)
                }else{
                    jErrorMessage.html(response.message);
                    jErrorMessage.css('display', 'block');
                }
            },
            error:function () {
                jLoading.css('display', 'none');
                jErrorMessage.html('ajax error');
                jErrorMessage.css('display', 'block');
            }
        })
    });

    $('#btReset').click(function(){
        var jErrorMessage = $('#error_message_reset');
        var username = $('#reset_username').val();
        var email = $('#reset_email').val();
        if(!username || username.length <= 0){
            jErrorMessage.html('username input error');
            return
        }
        if(!email || email.length <= 0){
            jErrorMessage.html('email input error');
            return
        }
        jErrorMessage.html(' ');
        jErrorMessage.css('display', 'none');
        $.ajax({
            type: "POST",
            url: baseUrl + "/user/reset",
            data: {"username": username, "email": email},
            dataType: "json",
            beforeSend:function () {
                jLoading.css('display', 'block');
            },
            success: function (response) {
                console.log(response);
                jLoading.css('display', 'none');
                if(response.code == 200){
                    $('#div_reset').css('display', 'none');
                    $('#div_sign_in').css('display', 'block');
                    $('#notice_message').html(response.message);
                    $('#notice').css('display', 'block');
                    setTimeout(function(){
                        $('#notice_message').html("");
                        $('#notice').css('display', 'none');
                    }, 3000)
                }else{
                    jErrorMessage.html(response.message);
                    jErrorMessage.css('display', 'block');
                }
            },
            error:function () {
                jLoading.css('display', 'none');
                console.log("ajax error")
            }
        })
    });

});