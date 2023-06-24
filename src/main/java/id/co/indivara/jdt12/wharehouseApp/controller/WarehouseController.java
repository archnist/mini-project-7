package id.co.indivara.jdt12.wharehouseApp.controller;
import id.co.indivara.jdt12.wharehouseApp.entity.*;
import id.co.indivara.jdt12.wharehouseApp.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    WarehouseInventoryRepository warehouseInventoryRepository;

    @PostMapping("/register")
    public Warehouse addWarehouse(@RequestBody Warehouse warehouse){
        warehouse.setWarehouseId("wh" + (warehouseRepository.count() + 1));
        warehouse.setDate(new Date());
        return warehouseRepository.save(warehouse);
    }

    @GetMapping("/show/all")
    public List<Warehouse> showAll(){
        return (List<Warehouse>) warehouseRepository.findAll();
    }

    @GetMapping("/show/{warehouseName}")
    public List<Warehouse> findByWarehouseName(@PathVariable("warehouseName") String warehouseName){
        return (List<Warehouse>) warehouseRepository.findByWarehouseName(warehouseName);
    }

    @GetMapping("/transaction/suppToWarehouse/{warehouse}")
    public List<SuppToWarehouse> findSupplierToWarehouseTransaction(@PathVariable("warehouse") Warehouse warehouse){
        return (List<SuppToWarehouse>) suppToWarehouseRepository.findByWarehouse(warehouse);
    }
    @GetMapping("/transaction/warehouseToWarehouse/{warehouse}")
    public List<WarehouseToWarehouse> findWarehouseToWarehouseTransaction(Warehouse warehouse){
        return (List<WarehouseToWarehouse>) warehouseToWarehouseRepository.findByWarehouseSource(warehouse);
    }
    @GetMapping("/transaction/warehouseToStore/{warehouse}")
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

    @DeleteMapping("/delete/{warehouse}")
    public void removeWarehouse(@PathVariable("warehouse") Warehouse warehouse){
        suppToWarehouseRepository.deleteByWarehouse(warehouse);
        warehouseToWarehouseRepository.deleteByWarehouseSource(warehouse);
        warehouseToStoreRepository.deleteByWarehouseSrc(warehouse);
        warehouseInventoryRepository.deleteByWarehouse(warehouse);
        warehouseRepository.deleteById(warehouse.getWarehouseId());
    }
}
