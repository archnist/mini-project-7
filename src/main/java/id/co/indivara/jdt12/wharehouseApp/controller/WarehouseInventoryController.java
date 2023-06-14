package id.co.indivara.jdt12.wharehouseApp.controller;
import id.co.indivara.jdt12.wharehouseApp.entity.Warehouse;
import id.co.indivara.jdt12.wharehouseApp.entity.WarehouseInventory;
import id.co.indivara.jdt12.wharehouseApp.repo.WarehouseInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("http://localhost:8080")
@RequestMapping("/warehouse")
@RestController
public class WarehouseInventoryController {
    @Autowired
    private WarehouseInventoryRepository warehouseInventoryRepository;

    @GetMapping("/find/{warehouse}")
    public WarehouseInventory find(@PathVariable Warehouse warehouse){
        return warehouseInventoryRepository.findByWarehouse(warehouse);
    }
}
