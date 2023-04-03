package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description 'Should retrieve a Reservation by ID.'
    name 'shouldRetrieveAReservationById'
    label 'shouldRetrieveAReservationById'

    request {
        headers {
            contentType(applicationJson())
        }
        method 'GET'
        headers {
            contentType('application/json')
        }
        url value('/reservations/e32a1c0c-7ff9-48f9-9525-530642060e41')

    }

    response {
        status 200

        body(
                [
                        "id"        : "e32a1c0c-7ff9-48f9-9525-530642060e41",
                        "start_date": "2023-04-10",
                        "end_date"  : "2023-04-13",
                        "status"    : "CONFIRMED",
                        "created_at": "2023-04-08T21:34:46.328386",
                        "updated_at": "2023-04-08T21:34:46.328398",
                        "user"      : [
                                "full_name"     : "First User",
                                "email"         : "firstUser@gmail.com",
                                "arrival_date"  : "2023-04-09",
                                "departure_date": "2023-04-14"
                        ]
                ]
        )

    }
}