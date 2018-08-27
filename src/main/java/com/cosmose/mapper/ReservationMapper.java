package com.cosmose.mapper;

import com.cosmose.dto.AddressDTO;
import com.cosmose.dto.AccommodationPlaceDetailsDTO;
import com.cosmose.dto.ReservationDTO;
import com.cosmose.dto.ReservationPeriodDTO;
import com.cosmose.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by damian on 25.08.18.
 */
@Component
public class ReservationMapper implements CustomMapper<ReservationDTO, Reservation> {

    @Override
    public Reservation toDomain(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setPeriodFromDate(LocalDateTime.parse(reservationDTO.getPeriodFromDate()));
        reservation.setPeriodToDate(LocalDateTime.parse(reservationDTO.getPeriodToDate()));
        reservation.setTotalPrice(reservationDTO.getTotalPrice());
        return reservation;
    }

    public Reservation toDomain(ReservationPeriodDTO reservationDTO,
                                Room room,
                                Customer customer) {
        Reservation reservation = new Reservation();
        reservation.setRoom(room);
        reservation.setCustomer(customer);
        reservation.setPeriodFromDate(LocalDateTime.parse(reservationDTO.getPeriodFromDate()));
        reservation.setPeriodToDate(LocalDateTime.parse(reservationDTO.getPeriodToDate()));
        return reservation;
    }

    @Override
    public ReservationDTO fromDomain(Reservation reservation) {
        Hotel hotel = reservation.getRoom().getHotel();
        HotelAddress hotelAddress = hotel.getHotelAddress();
        return ReservationDTO.builder()
                .id(reservation.getId())
                .periodFromDate(reservation.getPeriodFromDate().toString())
                .periodToDate(reservation.getPeriodToDate().toString())
                .totalPrice(reservation.getTotalPrice())
                .status(reservation.getStatus())
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
                        .build())
                .build();
    }
}
