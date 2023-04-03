package org.pacific.campsite.rest.request;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class ReservationDTO {
    private UUID id;

    @NotNull(message = "Start Date is mandatory")
    private LocalDate startDate;

    @NotNull(message = "End Date is mandatory")
    private LocalDate endDate;

    private StatusEnum status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


    public static final class Builder {
        private ReservationDTO reservationDTO;

        private Builder() {
            reservationDTO = new ReservationDTO();
        }

        public static Builder aReservationDTO() {
            return new Builder();
        }

        public Builder id(UUID id) {
            reservationDTO.setId(id);
            return this;
        }

        public Builder startDate(LocalDate startDate) {
            reservationDTO.setStartDate(startDate);
            return this;
        }

        public Builder endDate(LocalDate endDate) {
            reservationDTO.setEndDate(endDate);
            return this;
        }

        public Builder status(StatusEnum status) {
            reservationDTO.setStatus(status);
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            reservationDTO.setCreatedAt(createdAt);
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            reservationDTO.setUpdatedAt(updatedAt);
            return this;
        }

        public ReservationDTO build() {
            return reservationDTO;
        }
    }
}
