package com.cosmose.entity;


import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by damian on 25.08.18.
 */
@Data
@Entity
@DiscriminatorValue("OWNER")
public class Owner extends User {

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Hotel> hotels = new ArrayList<>();

}
