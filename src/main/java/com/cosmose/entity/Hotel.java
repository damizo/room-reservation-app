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
}
