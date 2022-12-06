package com.dag.transaction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dag.transaction.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Returns all transactions with the given accountNumber.
     *
     * Note that the order of elements in the result is not guaranteed.
     *
     * @param accountNumber must not be {@literal null}.
     * @return guaranteed to be not {@literal null}.
     * @throws IllegalArgumentException if {@literal accountNumber} is
     *                                  {@literal null}.
     */
    List<Transaction> findAllByAccountNumber(String accountNumber);

}
