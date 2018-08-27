package com.cosmose.mapper;

import com.cosmose.dto.CustomerDTO;
import com.cosmose.entity.Customer;
import org.springframework.stereotype.Component;

/**
 * Created by damian on 24.08.18.
 */
@Component
public class CustomerMapper implements UserMapper<Customer, CustomerDTO> {

    @Override
    public Customer toDomain(CustomerDTO customerDTO) {
        return new Customer(customerDTO.getFirstName(),
                customerDTO.getLastName(),
                customerDTO.getEmail(),
                customerDTO.getPhoneNumber());
    }

    @Override
    public CustomerDTO fromDomain(Customer customer) {
        return new CustomerDTO(customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getPhoneNumber(),
                customer.getEmail());
    }
}
