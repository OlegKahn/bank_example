package com.bank.history.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Аннотация для полей, чтобы указать,
 * что поле не Null и должен быть Long
 * Методы необходимые при создании аннотации:
 * @see CheckHistoryData#message()
 * @see CheckHistoryData#groups()
 * @see CheckHistoryData#payload()
 *
 * Вложенныя аннотация:
 * @see CheckHistoryData.List
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CheckHistoryData.List.class)
@Documented
@Constraint(validatedBy = CheckHistoryDataValidator.class)
public @interface CheckHistoryData {

    String message() default "History's data can not be Null!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE,
            ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        CheckHistoryData[] value();
    }
}
