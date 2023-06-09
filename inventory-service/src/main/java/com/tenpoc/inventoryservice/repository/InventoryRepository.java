package com.tenpoc.inventoryservice.repository;

import com.tenpoc.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long>, JpaSpecificationExecutor<Inventory> {
    List<Inventory> findBySkuCodeIn(List<String> skuCode);
}
