package com.cosmose.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * Created by damian on 25.08.18.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationPeriodDTO {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private String periodFromDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private String periodToDate;
}
