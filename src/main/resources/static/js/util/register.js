$('#submitForm').click(function () {
    if ($('#registerInfo').validate({
        rules: {
            userEmail: {
                required: true,
                email: true
            },
            accountName: {
                required: true,
                minlength: 4,
                maxlength: 12
            },
            userPassword: {
                required: true,
                minlength: 8,
                maxlength: 16
            },
            PasswordConfirm: {
                required: true,
                minlength: 8,
                maxlength: 16,
                equalTo: '#userPassword'
            }
            ,
            validityCode: 'required'
        },
        messages: {
            passwordConfirm: {
                equalTo: 'Passwords do not match'
            }
        },
        errorPlacement: function (error, element) {
            error.appendTo(element.parent().next("div"));
        }
    }).form()) {
        $('#password').val($('#userPassword').val().MD5(32));
        var form = document.getElementById('registerInfo');
        form.submit();
    }
});