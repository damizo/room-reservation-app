package com.cosmose.entity;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by damian on 26.08.18.
 */
@Data
@Entity
@DiscriminatorValue("HOTEL")
public class HotelAddress extends Address {

}
