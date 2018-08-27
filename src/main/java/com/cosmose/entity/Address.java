package com.cosmose.entity;


import com.google.common.base.Objects;

import javax.persistence.*;

/**
 * Created by damian on 26.08.18.
 */
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public AddressType getType() {
        return type;
    }

    public void setType(AddressType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return Objects.equal(street, address.street) &&
                Objects.equal(buildingNumber, address.buildingNumber) &&
                Objects.equal(flatNumber, address.flatNumber) &&
                Objects.equal(postalCode, address.postalCode) &&
                Objects.equal(city, address.city) &&
                type == address.type;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(street, buildingNumber, flatNumber, postalCode, city, type);
    }
}
