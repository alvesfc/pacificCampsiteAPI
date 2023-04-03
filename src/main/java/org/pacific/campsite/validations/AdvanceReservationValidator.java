package org.pacific.campsite.validations;

import org.pacific.campsite.rest.request.ReservationWithUserDTO;

import javax.validation.ConstraintValidator;

public class AdvanceReservationValidator implements ConstraintValidator<AdvanceReservationValidation, ReservationWithUserDTO> {

    private String field;
    private String message;

    @Override
    public void initialize(final AdvanceReservationValidation constraintAnnotation) {
        field = constraintAnnotation.field();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(ReservationWithUserDTO dto, javax.validation.ConstraintValidatorContext context) {
        if(dto.getStartDate() == null || dto.getUser() == null || dto.getUser().getArrivalDate() == null)
            return true;

        boolean isValid = true;
        if (!dto.getStartDate().minusMonths(1).isBefore(dto.getUser().getArrivalDate())){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message + "up to 1 month in advance. Please try again with a different date.").addPropertyNode(field).addConstraintViolation();
            isValid = false;
        }

        if (dto.getStartDate().equals(dto.getUser().getArrivalDate()) || dto.getStartDate().isBefore(dto.getUser().getArrivalDate())){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message + "minimum 1 day(s) ahead of arrival. Please try again with a different date.").addPropertyNode(field).addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }

}
