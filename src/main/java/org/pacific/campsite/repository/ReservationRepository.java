package org.pacific.campsite.repository;

import org.pacific.campsite.domain.Reservation;
import org.pacific.campsite.rest.request.StatusEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface ReservationRepository extends ReactiveCrudRepository<Reservation, UUID> {
    Flux<Reservation> findByStartDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
    Mono<Long> countFindByStartDateLessThanEqualAndEndDateGreaterThanEqualAndStatus(LocalDate startDate, LocalDate endDate, StatusEnum status);
}
