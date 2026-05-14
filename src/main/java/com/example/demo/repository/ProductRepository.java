package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByQrCode(String qrCode);
    boolean existsByQrCode(String qrCode);


    Optional<Product> findByBarcode(String barcode);
    boolean existsByBarcode(String barcode);

    List<Product> findByNameContainingIgnoreCase(String name);
}