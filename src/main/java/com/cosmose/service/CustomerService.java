package com.cosmose.service;

import com.cosmose.entity.Customer;
import com.cosmose.dto.CustomerDTO;
import com.cosmose.mapper.CustomerMapper;
import com.cosmose.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by damian on 24.08.18.
 */
@Service
@Transactional
public class CustomerService implements UserService<CustomerDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public CustomerDTO create(CustomerDTO customerDTO) {
        LOGGER.info("Before creating customer: {}", customerDTO);
        Customer customer = customerMapper.toDomain(customerDTO);

        Customer persistedCustomer = customerRepository.save(customer);
        LOGGER.info("Customer created: {}", customerDTO);

        return customerMapper.fromDomain(persistedCustomer);
    }
}
