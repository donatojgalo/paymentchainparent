package com.dag.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dag.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
