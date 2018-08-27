package com.cosmose.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by damian on 25.08.18.
 */
@Entity
@Table(name = "ROOMS")
public class Room extends AbstractEntity {

    @Column(name = "LOCAL_NUMBER")
    private Integer localNumber;

    @Column(name = "DAILY_PRICE")
    private Integer dailyPrice;

    @ManyToOne
    @JoinColumn(name = "HOTEL_ID")
    private Hotel hotel;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();

    public Room(Integer localNumber, Integer dailyPrice) {
        this.localNumber = localNumber;
        this.dailyPrice = dailyPrice;
    }

    public Room() {
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Integer getLocalNumber() {
        return localNumber;
    }

    public void setLocalNumber(Integer localNumber) {
        this.localNumber = localNumber;
    }

    public Integer getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(Integer dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Room)) return false;

        Room room = (Room) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(localNumber, room.localNumber)
                .append(dailyPrice, room.dailyPrice)
                .append(hotel, room.hotel)
                .append(reservations, room.reservations)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(localNumber)
                .append(dailyPrice)
                .append(hotel)
                .append(reservations)
                .toHashCode();
    }
}
