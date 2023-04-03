package org.pacific.campsite.domain;


import org.pacific.campsite.rest.request.StatusEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Table("reservations")
public class Reservation {

    @Id
    private UUID id;
    @Column("start_date")
    private LocalDate startDate;
    @Column("end_date")
    private LocalDate endDate;
    @Column("status")
    private StatusEnum status;
    @Column("created_at")
    private LocalDateTime createdAt;
    @Column("updated_at")
    private LocalDateTime updatedAt;
    @Column("full_name")
    private String fullName;
    private String email;
    @Column("arrival_date")
    private LocalDate arrivalDate;
    @Column("departure_date")
    private LocalDate departureDate;

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


    public static final class Builder {
        private Reservation reservation;

        private Builder() {
            reservation = new Reservation();
        }

        public static Builder aReservation() {
            return new Builder();
        }

        public Builder id(UUID id) {
            reservation.setId(id);
            return this;
        }

        public Builder startDate(LocalDate startDate) {
            reservation.setStartDate(startDate);
            return this;
        }

        public Builder endDate(LocalDate endDate) {
            reservation.setEndDate(endDate);
            return this;
        }

        public Builder status(StatusEnum status) {
            reservation.setStatus(status);
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            reservation.setCreatedAt(createdAt);
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            reservation.setUpdatedAt(updatedAt);
            return this;
        }

        public Builder fullName(String fullName) {
            reservation.setFullName(fullName);
            return this;
        }

        public Builder email(String email) {
            reservation.setEmail(email);
            return this;
        }

        public Builder arrivalDate(LocalDate arrivalDate) {
            reservation.setArrivalDate(arrivalDate);
            return this;
        }

        public Builder departureDate(LocalDate departureDate) {
            reservation.setDepartureDate(departureDate);
            return this;
        }

        public Reservation build() {
            return reservation;
        }
    }
}
