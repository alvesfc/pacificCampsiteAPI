package contracts

import org.springframework.cloud.contract.spec.Contract
import org.springframework.cloud.contract.spec.internal.RegexPatterns

Contract.make {
    description 'Should not create a Reservation greater than three days.'
    name 'shouldNotCreateAReservationGreaterThanThreeDays'
    label 'shouldNotCreateAReservationGreaterThanThreeDays'

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
                "end_date": value(consumer(regex(new RegexPatterns().isoDate().toString())), producer('2023-04-05')),
                "user": [
                        "full_name": value(consumer(regex('^.{6,60}$')), producer('User Full Name')),
                        "email": value(consumer(regex('^.{6,60}$')), producer('email@email.com')),
                        "arrival_date": value(consumer(regex(new RegexPatterns().isoDate().toString())), producer('2023-03-31')),
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
                                "object_name":"reservationWithUserDTOMono",
                                "field":"startDate",
                                "message":"The campsite can be reserved for max 3 days. Please try again with a different date."
                        ]
                ]
        ])

        headers {
            contentType('application/problem+json')
        }

    }
}