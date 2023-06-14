package id.co.indivara.jdt12.wharehouseApp.controller;
import id.co.indivara.jdt12.wharehouseApp.entity.Goods;
import id.co.indivara.jdt12.wharehouseApp.entity.Warehouse;
import id.co.indivara.jdt12.wharehouseApp.entity.WarehouseInventory;
import id.co.indivara.jdt12.wharehouseApp.repo.WarehouseInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class WarehouseInventoryController {
    @Autowired
    private WarehouseInventoryRepository warehouseInventoryRepository;

    @GetMapping("/find/{good}/{warehouse}")
    public WarehouseInventory find(@PathVariable("good") Goods goods, @PathVariable("warehouse") Warehouse warehouse){
        return warehouseInventoryRepository.findByGoodsAndWarehouse(goods,warehouse);
    }
}
