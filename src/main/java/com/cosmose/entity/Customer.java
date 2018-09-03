package com.cosmose.entity;


import com.google.common.base.Objects;
import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by damian on 24.08.18.
 */
@Data
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
}
