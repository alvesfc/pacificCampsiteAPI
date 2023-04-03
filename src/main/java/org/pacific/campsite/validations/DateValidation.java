package org.pacific.campsite.validations;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = DateValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateValidation {

    String message() default "The date field is invalid.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
