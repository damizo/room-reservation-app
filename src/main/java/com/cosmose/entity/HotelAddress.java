package com.cosmose.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by damian on 26.08.18.
 */
@Entity
@DiscriminatorValue("HOTEL")
public class HotelAddress extends Address {

}
