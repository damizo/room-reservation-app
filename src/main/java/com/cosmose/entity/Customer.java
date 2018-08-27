package com.cosmose.entity;


import com.google.common.base.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by damian on 24.08.18.
 */
@Entity
@DiscriminatorValue("CUSTOMER")
public class Customer extends User {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Reservation> reservations = new ArrayList<>();

    public Customer(String firstName, String lastName, String email,
                    String phoneNumber) {
        super(firstName, lastName, email, phoneNumber);
    }

    public Customer() {

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

        if (!(o instanceof Customer)) return false;

        Customer customer = (Customer) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(reservations, customer.reservations)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(reservations)
                .toHashCode();
    }
}
