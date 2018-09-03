package com.cosmose.entity;


import com.google.common.base.Objects;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by damian on 26.08.18.
 */
@Data
@Entity
@Table(name = "ADDRESSES")
@DiscriminatorColumn(name = "TYPE")
public class Address extends AbstractEntity {

    @Column(name = "STREET")
    private String street;

    @Column(name = "BUILDING_NUMBER")
    private String buildingNumber;

    @Column(name = "FLAT_NUMBER")
    private String flatNumber;

    @Column(name = "POSTAL_CODE")
    private String postalCode;

    @Column(name = "CITY")
    private String city;

    @Column(name = "TYPE", updatable = false, insertable = false)
    @Enumerated(EnumType.STRING)
    private AddressType type;

}
