package org.pacific.campsite.service;

import org.pacific.campsite.repository.ReservationRepository;
import org.pacific.campsite.rest.request.FilterDTO;
import org.pacific.campsite.rest.request.ReservationWithUserDTO;
import org.pacific.campsite.rest.request.StatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zalando.problem.Problem;
import org.zalando.problem.ThrowableProblem;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.zalando.problem.Status.BAD_REQUEST;


@Service
public class ReservationService {

    private static final Logger LOG = LoggerFactory.getLogger(ReservationService.class);

    private final ReservationRepository reservationRepository;

    private final ReservationMapper reservationMapper;

    public ReservationService(ReservationRepository reservationRepository, ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    public Mono<ReservationWithUserDTO> create(final ReservationWithUserDTO dto) {
        return isOverlapping(dto)
                .flatMap(isOverlapping -> {
                    if (isOverlapping) {
                        return Mono.error(buildOverlappingError());
                    }
                    return reservationRepository.save(reservationMapper.mapToDomain(dto));
                })
                .map(reservationMapper::mapToDTO);
    }

    public Mono<ReservationWithUserDTO> patch(final UUID id, final ReservationWithUserDTO dto) {
        return reservationRepository.findById(id)
                .switchIfEmpty(Mono.error(buildNotfoundError()))
                .map(domain -> reservationMapper.partialUpdate(domain, dto))
                .flatMap(domain -> isOverlapping(reservationMapper.mapToDTO(domain))
                        .flatMap(isOverlapping -> {
                            if (isOverlapping) {
                                return Mono.error(buildOverlappingError());
                            }
                            return reservationRepository.save(domain);
                        }))
                .map(reservationMapper::mapToDTO);
    }

    public Mono<Void> delete(final UUID id) {
        return reservationRepository.findById(id)
                .switchIfEmpty(Mono.error(buildNotfoundError()))
                .flatMap(domain -> {
                    domain.setUpdatedAt(LocalDateTime.now());
                    domain.setStatus(StatusEnum.CANCELED);
                    return reservationRepository.save(domain);
                })
                .then();
    }

    public Flux<ReservationWithUserDTO> findAll(final FilterDTO filter) {
        FilterDTO filterDTO = buildFilterDTO(filter);

        Pageable pageable = Pageable.ofSize(filterDTO.getSize()).withPage(filterDTO.getPage() - 1);

        return reservationRepository.findByStartDateBetween(filterDTO.getStartDate(), filterDTO.getEndDate(), pageable)
                .map(reservationMapper::mapToDTO);
    }


    private Mono<Boolean> isOverlapping(final ReservationWithUserDTO dto) {
        return reservationRepository.countFindByStartDateLessThanEqualAndEndDateGreaterThanEqualAndStatus(
                dto.getEndDate(), dto.getStartDate(), StatusEnum.CONFIRMED).map(count -> count > 0);
    }

    private FilterDTO buildFilterDTO(final FilterDTO filter) {
        FilterDTO.Builder filterBuilder = FilterDTO.Builder.aFilterDTO();

        if (filter.getSize() == null || filter.getSize() == 0) {
            filterBuilder.size(25);
        } else {
            filterBuilder.size(filter.getSize());
        }
        if (filter.getPage() == null || filter.getPage() == 0) {
            filterBuilder.page(1);
        } else {
            filterBuilder.page(filter.getPage());
        }
        if (filter.getStartDate() == null) {
            filterBuilder.startDate(LocalDate.now().minusMonths(1));
        } else {
            filterBuilder.startDate(filter.getStartDate());
        }
        if (filter.getEndDate() == null) {
            filterBuilder.endDate(LocalDate.now());
        } else {
            filterBuilder.endDate(filter.getEndDate());
        }
        return filterBuilder.build();
    }

    public Mono<ReservationWithUserDTO> find(final UUID id) {
        return reservationRepository.findById(id)
                .switchIfEmpty(Mono.error(buildNotfoundError()))
                .map(reservationMapper::mapToDTO);

    }

    private ThrowableProblem buildNotfoundError() {
        return Problem
                .builder()
                .withStatus(BAD_REQUEST)
                .withDetail("Reservation Not Found!")
                .build();
    }

    private ThrowableProblem buildOverlappingError() {
        return Problem
                .builder()
                .withStatus(BAD_REQUEST)
                .withDetail("Reservation date is not available. Please try again with a different date.")
                .build();
    }

}
