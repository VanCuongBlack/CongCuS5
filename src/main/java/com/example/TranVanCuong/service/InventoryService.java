package com.example.TranVanCuong.service;

import com.example.TranVanCuong.model.Inventory;
import com.example.TranVanCuong.model.Product;
import com.example.TranVanCuong.repository.InventoryRepository;
import com.example.TranVanCuong.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public Inventory createInventory(Product product) {
        Inventory inventory = new Inventory(product);
        Inventory savedInventory = inventoryRepository.save(inventory);
        log.info("Inventory created for product ID: {}", product.getId());
        return savedInventory;
    }

    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    public Optional<Inventory> getInventoryById(String id) {
        return inventoryRepository.findById(id);
    }

    public Optional<Inventory> getInventoryByProductId(String productId) {
        return inventoryRepository.findByProductId(productId);
    }

    public Inventory addStock(String productId, long quantity) {
        Optional<Inventory> inventory = inventoryRepository.findByProductId(productId);
        if (inventory.isPresent()) {
            Inventory inv = inventory.get();
            inv.setStock(inv.getStock() + quantity);
            Inventory saved = inventoryRepository.save(inv);
            log.info("Stock added: Product ID: {}, Quantity: {}, New Stock: {}", 
                productId, quantity, saved.getStock());
            return saved;
        }
        throw new RuntimeException("Inventory not found for product ID: " + productId);
    }

    public Inventory removeStock(String productId, long quantity) {
        Optional<Inventory> inventory = inventoryRepository.findByProductId(productId);
        if (inventory.isPresent()) {
            Inventory inv = inventory.get();
            if (inv.getStock() < quantity) {
                throw new RuntimeException("Insufficient stock. Available: " + inv.getStock() + ", Requested: " + quantity);
            }
            inv.setStock(inv.getStock() - quantity);
            Inventory saved = inventoryRepository.save(inv);
            log.info("Stock removed: Product ID: {}, Quantity: {}, Remaining Stock: {}", 
                productId, quantity, saved.getStock());
            return saved;
        }
        throw new RuntimeException("Inventory not found for product ID: " + productId);
    }

    public Inventory reservation(String productId, long quantity) {
        Optional<Inventory> inventory = inventoryRepository.findByProductId(productId);
        if (inventory.isPresent()) {
            Inventory inv = inventory.get();
            if (inv.getStock() < quantity) {
                throw new RuntimeException("Insufficient stock for reservation. Available: " + inv.getStock() + ", Requested: " + quantity);
            }
            inv.setStock(inv.getStock() - quantity);
            inv.setReserved(inv.getReserved() + quantity);
            Inventory saved = inventoryRepository.save(inv);
            log.info("Reservation: Product ID: {}, Quantity: {}, Stock: {}, Reserved: {}", 
                productId, quantity, saved.getStock(), saved.getReserved());
            return saved;
        }
        throw new RuntimeException("Inventory not found for product ID: " + productId);
    }

    public Inventory sold(String productId, long quantity) {
        Optional<Inventory> inventory = inventoryRepository.findByProductId(productId);
        if (inventory.isPresent()) {
            Inventory inv = inventory.get();
            if (inv.getReserved() < quantity) {
                throw new RuntimeException("Insufficient reserved items. Available: " + inv.getReserved() + ", Requested: " + quantity);
            }
            inv.setReserved(inv.getReserved() - quantity);
            inv.setSoldCount(inv.getSoldCount() + quantity);
            Inventory saved = inventoryRepository.save(inv);
            log.info("Sold: Product ID: {}, Quantity: {}, Reserved: {}, SoldCount: {}", 
                productId, quantity, saved.getReserved(), saved.getSoldCount());
            return saved;
        }
        throw new RuntimeException("Inventory not found for product ID: " + productId);
    }

    public void deleteInventoryByProductId(String productId) {
        Optional<Inventory> inventory = inventoryRepository.findByProductId(productId);
        inventory.ifPresent(inv -> inventoryRepository.deleteById(inv.getId()));
    }
}
