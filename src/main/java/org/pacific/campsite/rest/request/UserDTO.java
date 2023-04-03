package org.pacific.campsite.rest.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class UserDTO {

    @NotNull(message = "Full Name is mandatory")
    @Size(min = 10, max = 100, message = "Full Name must be between 10 and 100 characters")
    private String fullName;

    @NotNull(message = "Email is mandatory")
    @Size(min = 10, max = 100, message = "Email must be between 10 and 100 characters")
    private String email;

    @NotNull(message = "Arrival Date is mandatory")
    private LocalDate arrivalDate;

    @NotNull(message = "Departure Date is mandatory")
    private LocalDate departureDate;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }


    public static final class UserDTOBuilder {
        private UserDTO userDTO;

        private UserDTOBuilder() {
            userDTO = new UserDTO();
        }

        public static UserDTOBuilder anUserDTO() {
            return new UserDTOBuilder();
        }

        public UserDTOBuilder fullName(String fullName) {
            userDTO.setFullName(fullName);
            return this;
        }

        public UserDTOBuilder email(String email) {
            userDTO.setEmail(email);
            return this;
        }

        public UserDTOBuilder arrivalDate(LocalDate arrivalDate) {
            userDTO.setArrivalDate(arrivalDate);
            return this;
        }

        public UserDTOBuilder departureDate(LocalDate departureDate) {
            userDTO.setDepartureDate(departureDate);
            return this;
        }

        public UserDTO build() {
            return userDTO;
        }
    }
}
