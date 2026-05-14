package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private ProductRepository productRepository;

    // ADD PRODUCT
    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Product product) {

        if (productRepository.existsByQrCode(product.getQrCode())
                || productRepository.existsByBarcode(product.getBarcode())) {

            return ResponseEntity
                    .badRequest()
                    .body("Product Already Exists");
        }

        Product savedProduct = service.saveProduct(product);

        return ResponseEntity.ok(savedProduct);
    }

    @PutMapping("/{qr}")
    public ResponseEntity<?> updateProduct(
            @PathVariable String qr,
            @RequestBody Product updatedProduct
    ) {

        return productRepository.findByQrCode(qr)
                .map(product -> {

                    product.setName(updatedProduct.getName());
                    product.setPrice(updatedProduct.getPrice());
                    product.setExpirationDate(updatedProduct.getExpirationDate());

                    return ResponseEntity.ok(productRepository.save(product));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // GET PRODUCT BY QR CODE
    @GetMapping("/{qr}")
    public ResponseEntity<Product> getProduct(@PathVariable String qr) {

        return service.getProductByQr(qr)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET ALL PRODUCTS
    @GetMapping
    public List<Product> getAllProducts() {

        return service.getAllProducts();

    }

    // SEARCH PRODUCT BY NAME OR BARCODE
    @GetMapping("/search")
    public ResponseEntity<?> searchProduct(@RequestParam String query) {

        // Try barcode first
        return productRepository.findByBarcode(query)
                .<ResponseEntity<?>>map(ResponseEntity::ok)

                // If barcode not found, search by name
                .orElseGet(() -> {

                    List<Product> products =
                            productRepository.findByNameContainingIgnoreCase(query);

                    return ResponseEntity.ok(products);
                });
    }
}