package id.co.indivara.jdt12.wharehouseApp.controller;
import id.co.indivara.jdt12.wharehouseApp.entity.*;
import id.co.indivara.jdt12.wharehouseApp.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:8080")
@RequestMapping("/warehouse")
@RestController
public class WarehouseToWarehouseController {
    @Autowired
    WarehouseToWarehouseRepository warehouseToWarehouseRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    WarehouseInventoryRepository warehouseInventoryRepository;
    @Autowired
    WarehouseRepository warehouseRepository;

    @PostMapping("/transfer/{goodsId}/{source}/to/{destination}")
    public ResponseEntity<WarehouseToWarehouse> transferToWarehouse(@PathVariable("goodsId") Goods goodsId, @PathVariable("source") Warehouse warehouseSrc, @PathVariable("destination") Warehouse warehouseDst, @RequestBody WarehouseToWarehouse warehouse) {
        WarehouseInventory warehouseSource = warehouseInventoryRepository.findByGoodsAndWarehouse(goodsId, warehouseSrc);
        WarehouseInventory warehouseDestination = warehouseInventoryRepository.findByGoodsAndWarehouse(goodsId, warehouseDst);
        WarehouseInventory warehouseInventory = new WarehouseInventory();
        try {
            if (warehouseSource.getStock() >= warehouse.getTotal()){
                warehouseDestination.setStock(warehouseDestination.getStock() + warehouse.getTotal());
                warehouseInventoryRepository.save(warehouseSource);
                warehouseInventoryRepository.save(warehouseDestination);
            } else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            warehouseSource.setStock(warehouseSource.getStock() - warehouse.getTotal());
        } catch (Exception ex) {
            if (warehouseSource.getStock() >= warehouse.getTotal()){
                warehouseInventory.setGoods(goodsId);
                warehouseInventory.setWarehouse(warehouseDst);
                warehouseInventory.setStock(warehouse.getTotal());
                warehouseInventoryRepository.save(warehouseInventory);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            warehouseSource.setStock(warehouseSource.getStock() - warehouse.getTotal());
        }
        WarehouseToWarehouse warehouseToWarehouse = new WarehouseToWarehouse();
        warehouseToWarehouse.setTransactionId("T" + (transactionRepository.count() + 1));
        warehouseToWarehouse.setWarehouseSource(warehouseSrc.getWarehouseId());
        warehouseToWarehouse.setWarehouseDestination(warehouseDst.getWarehouseId());
        warehouseToWarehouse.setGoods(goodsId);
        warehouseToWarehouse.setTotal(warehouse.getTotal());
        Transaction transaction = new Transaction();
        transaction.setTransactionId("T" + (transactionRepository.count() + 1));
        transaction.setType("Warehouse To Warehouse");
        transaction.setGoods(goodsId);
        transactionRepository.save(transaction);
        warehouseToWarehouseRepository.save(warehouseToWarehouse);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}