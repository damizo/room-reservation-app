package com.cosmose.entity;

import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by damian on 25.08.18.
 */
@Data
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

}
