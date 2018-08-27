package com.cosmose.mapper;

import com.cosmose.dto.AddressDTO;
import com.cosmose.dto.AccommodationPlaceDetailsDTO;
import com.cosmose.entity.*;
import com.cosmose.dto.RoomDTO;
import org.springframework.stereotype.Component;

/**
 * Created by damian on 26.08.18.
 */
@Component
public class RoomMapper implements CustomMapper<RoomDTO, Room> {
    @Override
    public Room toDomain(RoomDTO roomDTO) {
        return new Room(roomDTO.getLocalNumber(),
                roomDTO.getDailyPrice());
    }

    @Override
    public RoomDTO fromDomain(Room room) {
        Hotel hotel = room.getHotel();
        HotelAddress hotelAddress = hotel.getHotelAddress();
        return RoomDTO.builder()
                .id(room.getId())
                .dailyPrice(room.getDailyPrice())
                .localNumber(room.getLocalNumber())
                .placeDetails(AccommodationPlaceDetailsDTO.builder()
                        .name(hotel.getName())
                        .type(AccommodationType.HOTEL)
                        .address(AddressDTO.builder()
                                .id(hotelAddress.getId())
                                .city(hotelAddress.getCity())
                                .postalCode(hotelAddress.getPostalCode())
                                .buildingNumber(hotelAddress.getBuildingNumber())
                                .street(hotelAddress.getStreet())
                                .flatNumber(hotelAddress.getFlatNumber()
                                ).build())
                        .build()).build();
    }
}
