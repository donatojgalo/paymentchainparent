package com.dag.transaction.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String reference;

    private String accountNumber;

    private LocalDateTime date;

    private Double amount;

    private Double fee;

    private String description;

    private String status;

    private String channel;

}
