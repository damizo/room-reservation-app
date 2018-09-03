package com.cosmose.dto;

import com.cosmose.entity.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

/**
 * Created by damian on 24.08.18.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends BaseDTO {
    protected String firstName;

    protected String lastName;

    protected String phoneNumber;

    protected String email;

    protected UserType type;

    @Builder
    public UserDTO(Long id, String firstName, String lastName, String phoneNumber, String email, UserType type) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.type = type;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }


}
