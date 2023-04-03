package org.pacific.campsite.validations;


import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class RangeReservationValidator implements ConstraintValidator<RangeReservationValidation, Object> {

    private String[] fields;
    private String errorMessage;

    @Override
    public void initialize(final RangeReservationValidation constraintAnnotation) {
        fields = constraintAnnotation.fields();
        errorMessage = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, javax.validation.ConstraintValidatorContext context) {
        if (fields.length == 2) {
            final BeanWrapperImpl beanWrapper = new BeanWrapperImpl(value);
            final Object start = beanWrapper.getPropertyValue(fields[0]);
            final Object end = beanWrapper.getPropertyValue(fields[1]);

            if(start == null || end == null) {
                return true;
            }

            if(DAYS.between((LocalDate)start, (LocalDate)end) > 3 || DAYS.between((LocalDate)start, (LocalDate)end) < 0) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(errorMessage).addPropertyNode(fields[0]).addConstraintViolation();
                return false;
            }
        }

        return true;
    }

}
