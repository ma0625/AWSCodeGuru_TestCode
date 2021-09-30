window.onload = function(){
    $('#Questionnaire_form').bootstrapValidator({
        // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            sex: {
                validators: {
                        stringLength: {
                        min: 2,
                    },
                        notEmpty: {
                        message: 'Please supply your gender'
                    }
                }
            },
             age: {
                validators: {
                     stringLength: {
                        min: 2,
                    },
                    notEmpty: {
                        message: 'Please supply your age'
                    }
                }
            },
            username: {
                validators: {
                    notEmpty: {
                        message: 'Please supply your email username'
                    }
                }
            },
            Question1: {
                validators: {
                    notEmpty: {
                        message: 'Please supply Question 4: Please tell me the name of the university you are enrolled in.'
                    }
                }
            },
            Question2: {
                validators: {
                     stringLength: {
                        min: 8,
                    },
                    notEmpty: {
                        message: 'Please supply Question 5: Please tell me the name of your faculty / department.'
                    }
                }
            },
            Question3: {
                validators: {
                     stringLength: {
                        min: 4,
                    },
                    notEmpty: {
                        message: 'Please supply Question 6: Please tell us your hometown.'
                    }
                }
            },
            Question4: {
                validators: {
                    notEmpty: {
                        message: 'Please supply Question 7: What kind of job do you want to be in the future?'
                    }
                }
            },
            Question5: {
                validators: {
                    notEmpty: {
                        message: 'Please supply Question 8: What are your current hobbies?'
                    }
                }
            },
            Question6: {
                validators: {
                      stringLength: {
                        min: 10,
                        max: 200,
                        message:'Please supply Question 9: Please enter your self-introduction at the end.'
                    }
                    }
                }
            }
        })
        .on('success.form.bv', function(e) {
            $('#success_message').slideDown({ opacity: "show" }, "slow") // Do something ...
                $('#contact_form').data('bootstrapValidator').resetForm();

            // Prevent form submission
            e.preventDefault();

            // Get the form instance
            var $form = $(e.target);

            // Get the BootstrapValidator instance
            var bv = $form.data('bootstrapValidator');

            // Use Ajax to submit form data
            $.post($form.attr('action'), $form.serialize(), function(result) {
                console.log(result);
            }, 'json');
        });
};