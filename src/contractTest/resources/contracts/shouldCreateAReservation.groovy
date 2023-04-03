package contracts

import org.springframework.cloud.contract.spec.Contract
import org.springframework.cloud.contract.spec.internal.RegexPatterns

def dateRegex = "([0-9]{4})-(1[0-2]|0[1-9])-(3[01]|0[1-9]|[12][0-9])T(2[0-3]|[01][0-9]):([0-5][0-9]):([0-5][0-9]).([0-9]*)"

Contract.make {
    description 'Should create a Reservation when submitting all valid information.'
    name 'shouldCreateAReservation'
    label 'shouldCreateAReservation'

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
                        "arrival_date": value(consumer(regex(new RegexPatterns().isoDate().toString())), producer('2023-03-31')),
                        "departure_date": value(consumer(regex(new RegexPatterns().isoDate().toString())), producer('2023-04-03')),
                ]
        ])
    }

    response {
        status 201

        body([
                "id": regex(new RegexPatterns().uuid().toString()),
                "start_date": value(consumer('2023-04-01'), producer(regex(new RegexPatterns().isoDate().toString()))),
                "end_date": value(consumer('2023-04-03'), producer(regex(new RegexPatterns().isoDate().toString()))),
                "created_at": value(consumer('2023-03-30T09:12:32.536443'), producer(regex(dateRegex))),
                "updated_at": value(consumer('2023-03-30T09:12:32.536443'), producer(regex(dateRegex))),
                "status": "CONFIRMED",
                "user": [
                        "full_name": value(consumer('User Full Name'), producer(regex('^.{6,60}$'))),
                        "email": value(consumer('email@email.com'), producer(regex('^.{6,60}$'))),
                        "arrival_date": value(consumer('2023-03-31'), producer(regex(new RegexPatterns().isoDate().toString()))),
                        "departure_date": value(consumer('2023-04-03'), producer(regex(new RegexPatterns().isoDate().toString()))),
                ]
        ])

        headers {
            contentType('application/json')
        }
    }
}