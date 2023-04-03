package contracts

import org.springframework.cloud.contract.spec.Contract
import org.springframework.cloud.contract.spec.internal.RegexPatterns

Contract.make {
    description 'Should not create a Reservation with an overlapping date.'
    name 'shouldNotCreateAOverlappingReservation'
    label 'shouldNotCreateAOverlappingReservation'

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
                "start_date": value(consumer(regex(new RegexPatterns().isoDate().toString())), producer('2023-04-12')),
                "end_date": value(consumer(regex(new RegexPatterns().isoDate().toString())), producer('2023-04-15')),
                "user": [
                        "full_name": value(consumer(regex('^.{6,60}$')), producer('User Full Name')),
                        "email": value(consumer(regex('^.{6,60}$')), producer('email@email.com')),
                        "arrival_date": value(consumer(regex(new RegexPatterns().isoDate().toString())), producer('2023-04-11')),
                        "departure_date": value(consumer(regex(new RegexPatterns().isoDate().toString())), producer('2023-04-17')),
                ]
        ])
    }

    response {
        status 400

        body([
                "type": regex(new RegexPatterns().url().toString()),
                "status":400,
                "path":"/reservations",
                "detail":"Reservation date is not available. Please try again with a different date.",
                "message":"error.http.400"

        ])

        headers {
            contentType('application/problem+json')
        }
    }
}