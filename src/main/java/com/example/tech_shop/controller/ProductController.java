package com.example.tech_shop.controller;

import com.example.tech_shop.model.Product;
import com.example.tech_shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @GetMapping
    public List<Product> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return repository.save(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product patch) {
        return repository.findById(id).map(p -> {
            if (patch.getName() != null) p.setName(patch.getName());
            if (patch.getBrand() != null) p.setBrand(patch.getBrand());
            if (patch.getCategory() != null) p.setCategory(patch.getCategory());
            if (patch.getQuantity() != 0) p.setQuantity(patch.getQuantity());
            if (patch.getPrice() != 0) p.setPrice(patch.getPrice());
            return ResponseEntity.ok(repository.save(p));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
