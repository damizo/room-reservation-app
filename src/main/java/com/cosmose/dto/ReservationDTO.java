package com.cosmose.dto;

import com.cosmose.entity.ReservationStatus;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Created by damian on 25.08.18.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ReservationDTO extends BaseDTO {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private String periodFromDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private String periodToDate;

    private Integer totalPrice;

    private ReservationStatus status;

    private AccommodationPlaceDetailsDTO placeDetails;

    @Builder
    public ReservationDTO(Long id, String periodFromDate, String periodToDate,
                          Integer totalPrice, ReservationStatus status, AccommodationPlaceDetailsDTO placeDetails) {
        super(id);
        this.periodFromDate = periodFromDate;
        this.periodToDate = periodToDate;
        this.totalPrice = totalPrice;
        this.status = status;
        this.placeDetails = placeDetails;
    }
}
