package contracts

import org.springframework.cloud.contract.spec.Contract
import org.springframework.cloud.contract.spec.internal.RegexPatterns

Contract.make {
    description 'Should not create a Reservation after or equals arrival date.'
    name 'shouldNotCreateAReservationAfterOrEqualsArrivalDate'
    label 'shouldNotCreateAReservationAfterOrEqualsArrivalDate'

    request {
        headers {
            contentType(applicationJson())
        }
        method 'POST'
        headers {
            contentType('application/json')
        }
        url value(consumer(regex('/reservations')), producer('/reservations'))
        body([
                "start_date": value(consumer(regex(new RegexPatterns().isoDate().toString())), producer('2023-04-01')),
                "end_date": value(consumer(regex(new RegexPatterns().isoDate().toString())), producer('2023-04-03')),
                "user": [
                        "full_name": value(consumer(regex('^.{6,60}$')), producer('User Full Name')),
                        "email": value(consumer(regex('^.{6,60}$')), producer('email@email.com')),
                        "arrival_date": value(consumer(regex(new RegexPatterns().isoDate().toString())), producer('2023-04-01')),
                        "departure_date": value(consumer(regex(new RegexPatterns().isoDate().toString())), producer('2023-04-03')),
                ]
        ])
    }

    response {
        status 400

        body([
                "type": regex(new RegexPatterns().url().toString()),
                "status":400,
                "path":"/reservations",
                "fieldErrors":[
                        [
                                "object_name": value(consumer('reservationWithUserDTOMono'), producer(regex('^.{3,100}$'))),
                                "field":"startDate",
                                "message":"The campsite can be reserved minimum 1 day(s) ahead of arrival. Please try again with a different date."
                        ]
                ]
        ])

    }
}