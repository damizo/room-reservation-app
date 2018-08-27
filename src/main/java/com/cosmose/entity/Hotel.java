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
@Table(name = "HOTELS")
public class Hotel extends AbstractEntity {

    @Column(name = "NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private Owner owner;

    @OneToOne
    @JoinColumn(name = "HOTEL_ADDRESS_ID")
    private HotelAddress hotelAddress;

    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms = new ArrayList<>();


    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public HotelAddress getHotelAddress() {
        return hotelAddress;
    }

    public void setHotelAddress(HotelAddress hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Hotel)) return false;

        Hotel hotel = (Hotel) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(name, hotel.name)
                .append(owner, hotel.owner)
                .append(hotelAddress, hotel.hotelAddress)
                .append(rooms, hotel.rooms)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(name)
                .append(owner)
                .append(hotelAddress)
                .append(rooms)
                .toHashCode();
    }
}
