package id.co.indivara.jdt12.wharehouseApp.controller;
import id.co.indivara.jdt12.wharehouseApp.entity.Warehouse;
import id.co.indivara.jdt12.wharehouseApp.repo.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
    @Autowired
    private WarehouseRepository warehouseRepository;

    @PostMapping("/register")
    public Warehouse addWarehouse(@RequestBody Warehouse warehouse){
        System.out.println(warehouse.getWarehouseName() + " Added Successfully!");
        return warehouseRepository.save(warehouse);
    }

}
