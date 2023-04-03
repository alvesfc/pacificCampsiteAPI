package org.pacific.campsite.rest.request;

import org.pacific.campsite.validations.AdvanceReservationValidation;
import org.pacific.campsite.validations.RangeReservationValidation;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;



@AdvanceReservationValidation
@RangeReservationValidation
public class ReservationWithUserDTO extends ReservationDTO {

    @Valid
    @NotNull(message = "User is mandatory")
    private UserDTO user;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public static final class Builder {
        private ReservationWithUserDTO reservationWithUserDTO;

        private Builder() {
            reservationWithUserDTO = new ReservationWithUserDTO();
        }

        public static Builder aReservationWithUserDTO() {
            return new Builder();
        }

        public ReservationWithUserDTO.Builder user(UserDTO userDTO) {
            reservationWithUserDTO.setUser(userDTO);
            return this;
        }

        public ReservationWithUserDTO.Builder id(UUID id) {
            reservationWithUserDTO.setId(id);
            return this;
        }

        public ReservationWithUserDTO.Builder startDate(LocalDate startDate) {
            reservationWithUserDTO.setStartDate(startDate);
            return this;
        }

        public ReservationWithUserDTO.Builder endDate(LocalDate endDate) {
            reservationWithUserDTO.setEndDate(endDate);
            return this;
        }

        public ReservationWithUserDTO.Builder status(StatusEnum status) {
            reservationWithUserDTO.setStatus(status);
            return this;
        }

        public ReservationWithUserDTO.Builder createdAt(LocalDateTime createdAt) {
            reservationWithUserDTO.setCreatedAt(createdAt);
            return this;
        }

        public ReservationWithUserDTO.Builder updatedAt(LocalDateTime updatedAt) {
            reservationWithUserDTO.setUpdatedAt(updatedAt);
            return this;
        }

        public ReservationWithUserDTO build() {
            return reservationWithUserDTO;
        }
    }

}
