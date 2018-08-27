package com.cosmose.integration;

import com.cosmose.dto.AddressDTO;
import com.cosmose.dto.AccommodationPlaceDetailsDTO;
import com.cosmose.entity.AccommodationType;

/**
 * Created by damian on 26.08.18.
 */
public final class ExpectedModelPopulator {

    public static final AccommodationPlaceDetailsDTO buildHotelIbisDetails() {
        return AccommodationPlaceDetailsDTO.builder()
                .name("Ibis")
                .type(AccommodationType.HOTEL)
                .address(AddressDTO.builder()
                        .id(1L)
                        .city("Warsaw")
                        .postalCode("03-553")
                        .buildingNumber("153")
                        .street("Marszalkowska Street")
                        .build()
                ).build();
    }

    public static final AccommodationPlaceDetailsDTO buildHotelRezydentDetails() {
        return AccommodationPlaceDetailsDTO.builder()
                .name("Rezydent")
                .type(AccommodationType.HOTEL)
                .address(AddressDTO.builder()
                        .id(2L)
                        .city("Sopot")
                        .postalCode("01-158")
                        .buildingNumber("20")
                        .street("Kosciuszki Street")
                        .build()
                ).build();
    }
}
