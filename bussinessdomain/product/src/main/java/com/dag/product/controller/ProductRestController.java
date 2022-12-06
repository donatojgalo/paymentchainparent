package com.dag.product.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.dag.product.entity.Product;
import com.dag.product.repository.ProductRepository;

@RestController
@RequestMapping("/products")
public class ProductRestController {

    @Autowired
    private ProductRepository repository;

    @GetMapping
    public List<Product> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(
            @PathVariable Long id, @RequestBody Product input) {

        Product found = repository.findById(id).orElse(null);

        if (found != null) {
            found.setCode(input.getCode());
            found.setName(input.getName());
        }

        Product save = repository.save(found);

        return ResponseEntity.ok(save);

    }

    @PostMapping
    public ResponseEntity<Product> save(@RequestBody Product input) {
        Product save = repository.save(input);
        return ResponseEntity.ok(save);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable long id) {

        Product found = repository.findById(id).orElse(null);

        if (found != null) {
            repository.deleteById(found.getId());
        }

        return ResponseEntity.ok().build();

    }

}
