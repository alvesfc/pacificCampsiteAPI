package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description 'Should cancel a Reservation by ID.'
    name 'shouldCancelAReservationById'
    label 'shouldCancelAReservationById'

    request {
        headers {
            contentType(applicationJson())
        }
        method 'DELETE'
        headers {
            contentType('application/json')
        }
        url value('/reservations/f7153579-c8f8-4990-a159-99ec616d05ba')

    }

    response {
        status 200
    }
}