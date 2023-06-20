package id.co.indivara.jdt12.wharehouseApp.controller;
import id.co.indivara.jdt12.wharehouseApp.entity.SuppToWarehouse;
import id.co.indivara.jdt12.wharehouseApp.entity.Warehouse;
import id.co.indivara.jdt12.wharehouseApp.entity.WarehouseToStore;
import id.co.indivara.jdt12.wharehouseApp.entity.WarehouseToWarehouse;
import id.co.indivara.jdt12.wharehouseApp.repo.SuppToWarehouseRepository;
import id.co.indivara.jdt12.wharehouseApp.repo.WarehouseRepository;
import id.co.indivara.jdt12.wharehouseApp.repo.WarehouseToStoreRepository;
import id.co.indivara.jdt12.wharehouseApp.repo.WarehouseToWarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    SuppToWarehouseRepository suppToWarehouseRepository;
    @Autowired
    WarehouseToWarehouseRepository warehouseToWarehouseRepository;
    @Autowired
    WarehouseToStoreRepository warehouseToStoreRepository;

    @PostMapping("/register")
    public Warehouse addWarehouse(@RequestBody Warehouse warehouse){
        warehouse.setWarehouseId("wh" + (warehouseRepository.count() + 1));
        warehouse.setDate(new Date());
        return warehouseRepository.save(warehouse);
    }
    public List<SuppToWarehouse> findSupplierToWarehouseTransaction(Warehouse warehouse){
        return (List<SuppToWarehouse>) suppToWarehouseRepository.findByWarehouse(warehouse);
    }
    public List<WarehouseToWarehouse> findWarehouseToWarehouseTransaction(Warehouse warehouse){
        return (List<WarehouseToWarehouse>) warehouseToWarehouseRepository.findByWarehouseSource(warehouse);
    }
    public List<WarehouseToStore> findWarehouseToStoreTransaction(Warehouse warehouse){
        return (List<WarehouseToStore>) warehouseToStoreRepository.findByWarehouseSrc(warehouse);
    }

    @GetMapping("/transaction/{warehouse}")
    public Map<List,Object> findTransaction(@PathVariable("warehouse") Warehouse warehouse){
        Map result = new HashMap<List,Object>();
        result.put("Supplier To Warehouse",findSupplierToWarehouseTransaction(warehouse));
        result.put("Warehouse To Warehouse",findWarehouseToWarehouseTransaction(warehouse));
        result.put("Warehouse To Store", findWarehouseToStoreTransaction(warehouse));
        return result;
    }
}
