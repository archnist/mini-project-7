package id.co.indivara.jdt12.wharehouseApp.controller;
import id.co.indivara.jdt12.wharehouseApp.entity.Goods;
import id.co.indivara.jdt12.wharehouseApp.entity.Warehouse;
import id.co.indivara.jdt12.wharehouseApp.entity.WarehouseInventory;
import id.co.indivara.jdt12.wharehouseApp.repo.WarehouseInventoryRepository;
import id.co.indivara.jdt12.wharehouseApp.repo.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin("http://localhost:8081")
@RequestMapping("/warehouse")
@RestController
public class WarehouseInventoryController {
    @Autowired
    private WarehouseInventoryRepository warehouseInventoryRepository;
    @Autowired
    WarehouseRepository warehouseRepository;

    @GetMapping("/find/{warehouse}")
    public List<WarehouseInventory> find(@PathVariable("warehouse") Warehouse warehouse) {
       return (List<WarehouseInventory>) warehouseInventoryRepository.findByWarehouse(warehouse);
    }

    @GetMapping("/find/{warehouseId}/{goodsId}")
    public WarehouseInventory findByGoodsAndWarehouse(@PathVariable("warehouseId")Warehouse warehouse, @PathVariable("goodsId")Goods goods){
        return warehouseInventoryRepository.findByGoodsAndWarehouse(goods,warehouse);
    }

    @GetMapping("/find/all")
    public List<WarehouseInventory> findAll(){
        return (List<WarehouseInventory>) warehouseInventoryRepository.findAll();
    }
}
