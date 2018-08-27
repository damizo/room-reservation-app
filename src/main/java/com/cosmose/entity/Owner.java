package com.cosmose.entity;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by damian on 25.08.18.
 */
@Entity
@DiscriminatorValue("OWNER")
public class Owner extends User {

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Hotel> hotels = new ArrayList<>();

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Owner)) return false;

        Owner owner = (Owner) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(hotels, owner.hotels)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(hotels)
                .toHashCode();
    }
}
