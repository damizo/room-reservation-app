package com.cosmose.service;

import com.google.common.primitives.Ints;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Created by damian on 25.08.18.
 */
@Component
public class ReservationCalculatorService {

    public Integer calculate(LocalDateTime periodFromDate, LocalDateTime periodToDate, Integer dailyPrice) {
        long daysOfAccommodation = DAYS.between(periodFromDate, periodToDate);
        return Ints.checkedCast(daysOfAccommodation) * dailyPrice;
    }

}
