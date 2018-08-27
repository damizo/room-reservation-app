package com.cosmose.dto;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by damian on 25.08.18.
 */
@Getter
@Setter
public class RoomDTO extends BaseDTO {

    private Integer dailyPrice;

    private Integer localNumber;

    private AccommodationPlaceDetailsDTO placeDetails;

    @Builder
    public RoomDTO(Long id, Integer dailyPrice,
                   Integer localNumber, AccommodationPlaceDetailsDTO placeDetails) {
        super(id);
        this.dailyPrice = dailyPrice;
        this.localNumber = localNumber;
        this.placeDetails = placeDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof RoomDTO)) return false;

        RoomDTO roomDTO = (RoomDTO) o;

        return new EqualsBuilder()
                .append(id, roomDTO.id)
                .append(dailyPrice, roomDTO.dailyPrice)
                .append(localNumber, roomDTO.localNumber)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(dailyPrice)
                .append(localNumber)
                .toHashCode();
    }
}
