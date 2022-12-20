package com.dag.transaction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dag.transaction.entity.Transaction;
import com.dag.transaction.repository.TransactionRepository;

@RestController
@RequestMapping("/transactions")
public class TransactionRestController {

    @Autowired
    private TransactionRepository repository;

    @GetMapping
    public List<Transaction> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> get(@PathVariable Long id) {
        return repository.findById(id).map(x -> ResponseEntity.ok(x)).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/customer/transactions")
    public List<Transaction> get(@RequestParam String accountNumber) {
        return repository.findAllByAccountNumber(accountNumber);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> update(
            @PathVariable Long id, @RequestBody Transaction input) {

        Transaction found = repository.findById(id).orElse(null);

        if (found != null) {
            found.setReference(input.getReference());
            found.setAccountNumber(input.getAccountNumber());
            found.setDate(input.getDate());
            found.setAmount(input.getAmount());
            found.setFee(input.getFee());
            found.setDescription(input.getDescription());
            found.setStatus(input.getStatus());
            found.setChannel(input.getChannel());
        }

        Transaction save = repository.save(found);

        return ResponseEntity.ok(save);

    }

    @PostMapping
    public ResponseEntity<Transaction> save(@RequestBody Transaction input) {
        Transaction save = repository.save(input);
        return ResponseEntity.ok(save);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Transaction> delete(@PathVariable long id) {

        Transaction found = repository.findById(id).orElse(null);

        if (found != null) {
            repository.deleteById(found.getId());
        }

        return ResponseEntity.ok().build();

    }

}
