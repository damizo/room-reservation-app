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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(firstName, userDTO.firstName) &&
                Objects.equals(lastName, userDTO.lastName) &&
                Objects.equals(phoneNumber, userDTO.phoneNumber) &&
                Objects.equals(email, userDTO.email) &&
                type == userDTO.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, phoneNumber, email, type);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", type=" + type +
                '}';
    }
}
