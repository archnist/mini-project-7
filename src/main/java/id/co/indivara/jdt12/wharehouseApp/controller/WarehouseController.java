package id.co.indivara.jdt12.wharehouseApp.controller;
import id.co.indivara.jdt12.wharehouseApp.entity.Warehouse;
import id.co.indivara.jdt12.wharehouseApp.repo.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
    @Autowired
    private WarehouseRepository warehouseRepository;

    @PostMapping("/register")
    public Warehouse addWarehouse(@RequestBody Warehouse warehouse){
        warehouse.setWarehouseId("wh" + (warehouseRepository.count() + 1));
        warehouse.setDate(new Date());
        return warehouseRepository.save(warehouse);
    }
}
