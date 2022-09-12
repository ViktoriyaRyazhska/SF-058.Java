package com.app.sportshubportal.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

//public class EmailValidatorClass implements ConstraintValidator<EmailValidator, String> {
//    @Override
//    public boolean isValid(String value, ConstraintValidatorContext context) {
//        String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
//                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
//        return Pattern.compile(regex)
//                .matcher(value)
//                .matches();
//    }
//}
