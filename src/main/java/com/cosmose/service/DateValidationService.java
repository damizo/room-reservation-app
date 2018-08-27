package com.cosmose.service;

import com.cosmose.entity.Reservation;
import com.cosmose.entity.ReservationStatus;
import com.cosmose.exception.LogicValidationException;
import com.cosmose.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by damian on 27.08.18.
 */
@Service
public class DateValidationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateValidationService.class);

    @Autowired
    private ReservationRepository reservationRepository;

    public void checkDatesConflicts(String periodFromDate, String periodToDate, Long roomId) {
        Stream<Reservation> reservationStream = reservationRepository.findByRoomIdAndStatus(roomId, ReservationStatus.CONFIRMED);
        LOGGER.info("Stream of reservations: {}", reservationStream);

        reservationStream.forEach(reservation -> {
            checkReservation(periodFromDate, periodToDate, reservation);
        });
    }

    public void checkReservation(String periodFromDate, String periodToDate, Reservation reservation) {
        LocalDateTime toReservePeriodFrom = LocalDateTime.parse(periodFromDate);
        LocalDateTime toReservePeriodTo = LocalDateTime.parse(periodToDate);
        LOGGER.info("Dates to reserve: {} and {}", toReservePeriodFrom, toReservePeriodTo);

        LocalDateTime reservedPeriodFrom = reservation.getPeriodFromDate();
        LocalDateTime reservedPeriodTo = reservation.getPeriodToDate();
        LOGGER.info("Reserved dates: {} and {}", reservedPeriodFrom, reservedPeriodTo);

        if (haveDateConflicts(toReservePeriodFrom, toReservePeriodTo, reservedPeriodFrom, reservedPeriodTo)
                && reservation.getStatus() == ReservationStatus.CONFIRMED) {
            throw new LogicValidationException("Period of time reservation proposal has conflicts with another reservation");
        }
    }

    public boolean validateReservations(String periodFromDate, String periodToDate, List<Reservation> reservations) {
        for (Reservation reservation : reservations) {
            LOGGER.info("Reservation during validation: {}", periodFromDate, periodToDate, reservation);
            try {
                checkReservation(periodFromDate, periodToDate, reservation);
            } catch (LogicValidationException e) {
                return false;
            }
        }
        return true;
    }

    public boolean haveDateConflicts(LocalDateTime toReservePeriodFrom, LocalDateTime toReservePeriodTo,
                                     LocalDateTime reservedPeriodFrom, LocalDateTime reservedPeriodTo) {
        return periodToConflictsWithReservedRange(toReservePeriodFrom, toReservePeriodTo, reservedPeriodFrom)
                || chosenPeriodIsBetweenReservedRange(toReservePeriodFrom, toReservePeriodTo, reservedPeriodFrom, reservedPeriodTo)
                || periodFromConflictsWithReservedRange(toReservePeriodFrom, toReservePeriodTo, reservedPeriodTo)
                || periodIsEqual(reservedPeriodFrom, toReservePeriodFrom, reservedPeriodTo, toReservePeriodTo);
    }

    private boolean periodIsEqual(LocalDateTime reservedPeriodFrom, LocalDateTime toReservePeriodFrom,
                                  LocalDateTime reservedPeriodTo, LocalDateTime toReservePeriodTo) {
        return reservedPeriodFrom.isEqual(toReservePeriodFrom) ||
                reservedPeriodTo.isEqual(toReservePeriodTo);
    }

    public boolean periodFromConflictsWithReservedRange(LocalDateTime toReservePeriodFrom, LocalDateTime toReservePeriodTo,
                                                        LocalDateTime reservedPeriodTo) {
        return reservedPeriodTo.isAfter(toReservePeriodFrom) && reservedPeriodTo.isBefore(toReservePeriodTo);
    }

    public boolean chosenPeriodIsBetweenReservedRange(LocalDateTime toReservePeriodFrom, LocalDateTime toReservePeriodTo,
                                                      LocalDateTime reservedPeriodFrom, LocalDateTime reservedPeriodTo) {
        return reservedPeriodFrom.isBefore(toReservePeriodFrom) && toReservePeriodFrom.isBefore(toReservePeriodTo)
                && toReservePeriodTo.isBefore(reservedPeriodTo);
    }

    public boolean periodToConflictsWithReservedRange(LocalDateTime toReservePeriodFrom, LocalDateTime toReservePeriodTo,
                                                      LocalDateTime reservedPeriodFrom) {
        return periodFromConflictsWithReservedRange(toReservePeriodFrom, toReservePeriodTo, reservedPeriodFrom);
    }
}
