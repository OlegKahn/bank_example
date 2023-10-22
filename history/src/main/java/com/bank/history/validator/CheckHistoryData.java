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
 * Annotation for fields to indicate
 * that the field is not Null and should be Long
 * Methods required when creating an annotation:
 * @see CheckHistoryData#message()
 * @see CheckHistoryData#groups()
 * @see CheckHistoryData#payload()
 *
 * Attached annotation:
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
