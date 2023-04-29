package com.bank.history.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Класс, где происходит валидация,
 * но так как в History неизменяемая валидация,
 * метод initialize не реализован
 * @see CheckHistoryDataValidator#initialize(CheckHistoryData)
 */
public class CheckHistoryDataValidator implements ConstraintValidator<CheckHistoryData, String> {

    /**
     * Метод из которого получаем значение
     * и сравниваем в методе isValid,
     * но так как неизменяемый валидаторб
     * потому пуст
     * @param constraintAnnotation тут получаем сравниваемое значение
     */
    @Override
    public void initialize(CheckHistoryData constraintAnnotation) {
    }

    /**
     * Метод проверяющий валидность поля
     * @param o значение поля
     * @param constraintValidatorContext без комментариев
     * @return возвращает булевское значение
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
