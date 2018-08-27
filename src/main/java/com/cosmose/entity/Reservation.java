package com.cosmose.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by damian on 24.08.18.
 */
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public LocalDateTime getPeriodFromDate() {
        return periodFromDate;
    }

    public void setPeriodFromDate(LocalDateTime periodFromDate) {
        this.periodFromDate = periodFromDate;
    }

    public LocalDateTime getPeriodToDate() {
        return periodToDate;
    }

    public void setPeriodToDate(LocalDateTime periodToDate) {
        this.periodToDate = periodToDate;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Reservation)) return false;

        Reservation that = (Reservation) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(customer, that.customer)
                .append(room, that.room)
                .append(periodFromDate, that.periodFromDate)
                .append(periodToDate, that.periodToDate)
                .append(status, that.status)
                .append(totalPrice, that.totalPrice)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(customer)
                .append(room)
                .append(periodFromDate)
                .append(periodToDate)
                .append(status)
                .append(totalPrice)
                .toHashCode();
    }
}
