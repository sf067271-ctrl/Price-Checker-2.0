package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    // SAVE PRODUCT
    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    // GET BY QR CODE
    public Optional<Product> getProductByQr(String qr) {
        return repository.findByQrCode(qr);
    }

    // GET ALL PRODUCTS
    public List<Product> getAllProducts() {
        return repository.findAll();
    }
}