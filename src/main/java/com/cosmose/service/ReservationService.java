package com.cosmose.service;

import com.cosmose.dto.ReservationDTO;
import com.cosmose.dto.ReservationPeriodDTO;

/**
 * Created by damian on 25.08.18.
 */
public interface ReservationService {

    ReservationDTO  reserve(Long customerId, Long roomId, ReservationPeriodDTO d);

    ReservationDTO cancel(Long customerId, Long reservationId);

    ReservationDTO check(Long customerId, Long reservationId);
}
