package com.cosmose.dto;

import com.cosmose.entity.UserType;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Created by damian on 24.08.18.
 */

@NoArgsConstructor
public class CustomerDTO extends UserDTO {

    public CustomerDTO(Long id, String firstName,
                       String lastName, String phoneNumber,
                       String email) {
        super(id, firstName, lastName, phoneNumber, email, UserType.CUSTOMER);
    }
}

