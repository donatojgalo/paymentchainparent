package com.dag.customer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dag.customer.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Retrieves an customer by its code.
     *
     * @param code must not be {@literal null}.
     * @return the customer with the given code
     *         or {@literal Optional#empty()} if none found.
     * @throws IllegalArgumentException if {@literal code} is {@literal null}.
     */
    Optional<Customer> findByCode(String code);

}
