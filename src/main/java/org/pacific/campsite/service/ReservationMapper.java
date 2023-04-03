package org.pacific.campsite.service;

import org.pacific.campsite.domain.Reservation;
import org.pacific.campsite.rest.request.ReservationDTO;
import org.pacific.campsite.rest.request.ReservationWithUserDTO;
import org.pacific.campsite.rest.request.StatusEnum;
import org.pacific.campsite.rest.request.UserDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ReservationMapper {
    public Reservation mapToDomain(final ReservationWithUserDTO reservation){
        return Reservation.Builder.aReservation()
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .fullName(reservation.getUser().getFullName())
                .email(reservation.getUser().getEmail())
                .arrivalDate(reservation.getUser().getArrivalDate())
                .departureDate(reservation.getUser().getDepartureDate())
                .status(StatusEnum.CONFIRMED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public ReservationWithUserDTO mapToDTO(final Reservation reservation){
        return ReservationWithUserDTO.Builder.aReservationWithUserDTO()
                .id(reservation.getId())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .status(reservation.getStatus())
                .createdAt(reservation.getCreatedAt())
                .updatedAt(reservation.getUpdatedAt())
                .user(UserDTO.UserDTOBuilder.anUserDTO()
                        .fullName(reservation.getFullName())
                        .email(reservation.getEmail())
                        .arrivalDate(reservation.getArrivalDate())
                        .departureDate(reservation.getDepartureDate())
                        .build())
                .build();
    }

    public Reservation partialUpdate(final Reservation domain, ReservationWithUserDTO dto){
        Reservation.Builder reservationBuilder = Reservation.Builder.aReservation();

        if(dto.getStartDate() != null){
            reservationBuilder.startDate(dto.getStartDate());
        }
        if(dto.getEndDate() != null){
            reservationBuilder.endDate(dto.getEndDate());
        }

        if(dto.getUser() != null){
            if(dto.getUser().getFullName() != null){
                reservationBuilder.fullName(dto.getUser().getFullName());
            }
            if(dto.getUser().getEmail() != null){
                reservationBuilder.email(dto.getUser().getEmail());
            }
            if(dto.getUser().getArrivalDate() != null){
                reservationBuilder.arrivalDate(dto.getUser().getArrivalDate());
            }
            if(dto.getUser().getDepartureDate() != null){
                reservationBuilder.departureDate(dto.getUser().getDepartureDate());
            }
        }
        reservationBuilder.updatedAt(LocalDateTime.now());
        reservationBuilder.id(domain.getId());
        reservationBuilder.status(domain.getStatus());
        reservationBuilder.createdAt(domain.getCreatedAt());

       return reservationBuilder.build();
    }

}
