package com.cosmose.entity;

import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by damian on 24.08.18.
 */
@Data
@Entity
@Table(name = "RESERVATIONS")
public class Reservation extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "ROOM_ID")
    private Room room;

    @Column(name = "PERIOD_FROM")
    private LocalDateTime periodFromDate;

    @Column(name = "PERIOD_TO")
    private LocalDateTime periodToDate;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Column(name = "TOTAL_PRICE")
    private Integer totalPrice;

}
