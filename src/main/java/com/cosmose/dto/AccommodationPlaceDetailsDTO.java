package com.cosmose.dto;

import com.cosmose.entity.AccommodationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by damian on 26.08.18.
 */
@Data
@Builder
public class AccommodationPlaceDetailsDTO {

    private String name;
    private AddressDTO address;
    private AccommodationType type;

}
