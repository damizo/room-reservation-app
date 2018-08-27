package com.cosmose.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Created by damian on 26.08.18.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRoomDTO {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    String periodFromDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    String periodToDate;

    String city;

    Integer dailyPriceFrom;

    Integer dailyPriceTo;
}
