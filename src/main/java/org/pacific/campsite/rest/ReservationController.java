package org.pacific.campsite.rest;


import org.pacific.campsite.rest.request.FilterDTO;
import org.pacific.campsite.rest.request.ReservationWithUserDTO;
import org.pacific.campsite.service.ReservationService;
import org.pacific.campsite.validations.DateValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@Validated
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping(value = "/reservations", produces = {"application/json", "application/problem+json"}, consumes = {"application/json"})
    public @ResponseBody Mono<ResponseEntity<ReservationWithUserDTO>> create(@Valid @RequestBody Mono<ReservationWithUserDTO> createRequest) {
        return createRequest
                .flatMap(reservationService::create)
                .map(dto -> ResponseEntity.created(URI.create("/reservations/" + dto.getId()))
                        .body(dto));
    }

    @PatchMapping(value = "/reservations/{id}", produces = {"application/json", "application/problem+json"}, consumes = {"application/json"})
    public @ResponseBody Mono<ResponseEntity<ReservationWithUserDTO>> patch(@PathVariable String id, @RequestBody(required = false) Mono<ReservationWithUserDTO> request) {
        return request
                .flatMap(dto -> reservationService.patch(UUID.fromString(id), dto))
                .map(dto -> ResponseEntity.accepted().body(dto));
    }

    @DeleteMapping(value = "/reservations/{id}", produces = {"application/json", "application/problem+json"}, consumes = {"application/json"})
    public @ResponseBody Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return reservationService.delete(UUID.fromString(id)).map(dto -> ResponseEntity.accepted().body(dto));
    }

    @GetMapping(value = "/reservations/{id}", produces = {"application/json", "application/problem+json"}, consumes = {"application/json"})
    public @ResponseBody Mono<ResponseEntity<ReservationWithUserDTO>> fetchById(@PathVariable String id) {
        return reservationService.find(UUID.fromString(id)).map(dto -> ResponseEntity.ok().body(dto));
    }

    @GetMapping(value = "/reservations", produces = {"application/json", "application/problem+json"}, consumes = {"application/json"})
    public @ResponseBody Mono<ResponseEntity<Flux<ReservationWithUserDTO>>> fetch(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @DateValidation @RequestParam(value = "start_date", required = false) String startDate,
            @DateValidation @RequestParam(value = "end_date", required = false)  String endDate) {

        return reservationService.findAll(FilterDTO.Builder.aFilterDTO()
                        .page(page)
                        .size(size)
                        .startDate(LocalDate.parse(startDate))
                        .endDate(LocalDate.parse(endDate))
                        .build()).collectList()
                .map(countWithEntities -> ResponseEntity
                        .ok()
                        .headers(
                                headers -> headers.set("X-Total-Count", String.valueOf(countWithEntities.size()))
                        )
                        .body(Mono.just(countWithEntities).flatMapIterable(list -> list))
                );
    }
}
