package com.cosmose.rest;

import com.cosmose.dto.CustomerDTO;
import com.cosmose.dto.ReservationDTO;
import com.cosmose.dto.ReservationPeriodDTO;
import com.cosmose.service.ReservationService;
import com.cosmose.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by damian on 24.08.18.
 */
@RestController
@RequestMapping(value = "/api/v1/customers")
public class CustomerController {

    @Autowired
    private UserService<CustomerDTO> userService;

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<CustomerDTO> register(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO createdCustomer = userService.create(customerDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdCustomer);
    }

    @PostMapping(value = "/{customerId}/reservations/rooms/{roomId}/reserve")
    public ResponseEntity<ReservationDTO> reserve(@PathVariable Long customerId,
                                                  @PathVariable Long roomId,
                                                  @RequestBody ReservationPeriodDTO periodDTO) {
        ReservationDTO createdReservation = reservationService.reserve(customerId, roomId, periodDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdReservation);
    }

    @PutMapping(value = "/{customerId}/reservations/{reservationId}/cancel")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long customerId,
                                                            @PathVariable Long reservationId) {
        ReservationDTO canceledReservation = reservationService.cancel(customerId, reservationId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(canceledReservation);
    }

    @GetMapping(value = "/{customerId}/reservations/{reservationId}/check")
    public ResponseEntity<ReservationDTO> checkReservation(@PathVariable Long customerId,
                                                 @PathVariable Long reservationId) {
        ReservationDTO reservation = reservationService.check(customerId, reservationId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reservation);
    }
}
