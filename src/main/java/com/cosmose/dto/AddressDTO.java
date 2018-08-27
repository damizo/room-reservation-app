package com.cosmose.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

/**
 * Created by damian on 26.08.18.
 */

@Data
public class AddressDTO extends BaseDTO{

    private String street;

    private String buildingNumber;

    private String flatNumber;

    private String postalCode;

    private String city;

    @Builder
    public AddressDTO(Long id, String street, String buildingNumber, String flatNumber, String postalCode, String city) {
        super(id);
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.flatNumber = flatNumber;
        this.postalCode = postalCode;
        this.city = city;
    }
}
