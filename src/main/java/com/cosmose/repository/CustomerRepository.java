package com.cosmose.repository;

import com.cosmose.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by damian on 24.08.18.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
