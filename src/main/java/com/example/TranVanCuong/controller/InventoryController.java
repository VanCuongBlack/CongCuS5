package com.example.TranVanCuong.controller;

import com.example.TranVanCuong.dto.StockOperationRequest;
import com.example.TranVanCuong.model.Inventory;
import com.example.TranVanCuong.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventory")
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventories() {
        List<Inventory> inventories = inventoryService.getAllInventories();
        return ResponseEntity.ok(inventories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable String id) {
        Optional<Inventory> inventory = inventoryService.getInventoryById(id);
        return inventory.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Inventory> getInventoryByProductId(@PathVariable String productId) {
        Optional<Inventory> inventory = inventoryService.getInventoryByProductId(productId);
        return inventory.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/add-stock")
    public ResponseEntity<?> addStock(@Valid @RequestBody StockOperationRequest request) {
        try {
            Inventory inventory = inventoryService.addStock(request.getProductId(), request.getQuantity());
            return ResponseEntity.ok(inventory);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/remove-stock")
    public ResponseEntity<?> removeStock(@Valid @RequestBody StockOperationRequest request) {
        try {
            Inventory inventory = inventoryService.removeStock(request.getProductId(), request.getQuantity());
            return ResponseEntity.ok(inventory);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/reservation")
    public ResponseEntity<?> reservation(@Valid @RequestBody StockOperationRequest request) {
        try {
            Inventory inventory = inventoryService.reservation(request.getProductId(), request.getQuantity());
            return ResponseEntity.ok(inventory);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/sold")
    public ResponseEntity<?> sold(@Valid @RequestBody StockOperationRequest request) {
        try {
            Inventory inventory = inventoryService.sold(request.getProductId(), request.getQuantity());
            return ResponseEntity.ok(inventory);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
