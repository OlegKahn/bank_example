package com.bank.history.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * The class where validation occurs
 * but since History has immutable validation,
 * initialize method is not implemented
 * @see CheckHistoryDataValidator#initialize(CheckHistoryData)
 */
public class CheckHistoryDataValidator implements ConstraintValidator<CheckHistoryData, String> {

    /**
     * The method from which we get the value
     * and compare in the isValid method,
     * but since the validator is immutable
     * that's why it's empty
     * @param constraintAnnotation here we get the compared value
     */
    @Override
    public void initialize(CheckHistoryData constraintAnnotation) {
    }

    /**
     * Method that checks the validity of a field
     * @param o field value
     * @param constraintValidatorContext no comments
     * @return returns a boolean value
     */
    @Override
    public boolean isValid(String o, ConstraintValidatorContext constraintValidatorContext) {
        boolean b = !(o == null);
        if (b) {
            try {
                Long.parseLong(o);
            } catch (Exception e) {
                b = false;
            }
        }
        return b;
    }
}
