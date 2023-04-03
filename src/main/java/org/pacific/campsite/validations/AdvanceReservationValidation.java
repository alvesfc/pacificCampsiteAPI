package org.pacific.campsite.validations;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({TYPE, ANNOTATION_TYPE})
@Constraint(validatedBy = AdvanceReservationValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AdvanceReservationValidation {

    String message() default "The campsite can be reserved ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String field() default "startDate";

}
