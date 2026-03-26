package com.example.TranVanCuong.service;

import com.example.TranVanCuong.model.Product;
import com.example.TranVanCuong.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryService inventoryService;

    public Product createProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        // Create inventory when product is created
        inventoryService.createInventory(savedProduct);
        log.info("Product created with ID: {}", savedProduct.getId());
        return savedProduct;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    public Product updateProduct(String id, Product product) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product p = existingProduct.get();
            if (product.getName() != null) p.setName(product.getName());
            if (product.getDescription() != null) p.setDescription(product.getDescription());
            if (product.getPrice() > 0) p.setPrice(product.getPrice());
            if (product.getSku() != null) p.setSku(product.getSku());
            return productRepository.save(p);
        }
        return null;
    }

    public boolean deleteProduct(String id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            // Also delete related inventory
            inventoryService.deleteInventoryByProductId(id);
            log.info("Product deleted with ID: {}", id);
            return true;
        }
        return false;
    }
}
