$('#submitForm').click(function () {
    if ($('#registerInfo').validate({
        rules: {
            userName: {
                required: true,
                email: true
            },
            userAccountName: {
                required: true,
                minlength: 4,
                maxlength: 12
            },
            userPassword: {
                required: true,
                minlength: 8,
                maxlength: 16
            },
            userPasswordConfirm: {
                required: true,
                minlength: 8,
                maxlength: 16,
                equalTo: '#userPassword'
            }
            ,
            validityCode: 'required'
        },
        messages: {
            userPasswordConfirm: {
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