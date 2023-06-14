package id.co.indivara.jdt12.wharehouseApp.controller;
import id.co.indivara.jdt12.wharehouseApp.entity.*;
import id.co.indivara.jdt12.wharehouseApp.exception.NotFoundException;
import id.co.indivara.jdt12.wharehouseApp.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:8080")
@RequestMapping("/supp")
@RestController
public class SuppToWarehouseController {
    @Autowired
    private SuppToWarehouseRepository suppToWarehouseRepository;
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    WarehouseInventoryRepository warehouseInventoryRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @PostMapping("/add/{whouseid}/{goodsid}")
    public ResponseEntity<SuppToWarehouse> addGoods(@PathVariable("whouseid") Warehouse whouseid, @PathVariable("goodsid") Goods goodsid, @RequestBody SuppToWarehouse suppToWarehouse){
        SuppToWarehouse supp = warehouseRepository.findById(whouseid.getWarehouseId()).map(warehouse -> {
            Goods goods = goodsRepository.findById(goodsid.getGoodId()).get();
            suppToWarehouse.setTransactionId("T" + (transactionRepository.count()+1));
            suppToWarehouse.setWarehouse(warehouse);
            suppToWarehouse.setGoods(goods);
            Transaction transaction = new Transaction();
            transaction.setTransactionId("T" + (transactionRepository.count()+1));
            transaction.setType("Supplier To Warehouse");
            transaction.setGoods(goodsid);
            transactionRepository.save(transaction);
            WarehouseInventory warehouseInventory = new WarehouseInventory();
            try {
                WarehouseInventory warehouseInventory1 = warehouseInventoryRepository.findByGoodsAndWarehouse(goodsid,whouseid);
                warehouseInventory1.setStock(warehouseInventory1.getStock() + suppToWarehouse.getTotal());
                warehouseInventoryRepository.save(warehouseInventory1);
            } catch (Exception ex){
                warehouseInventory.setGoods(goods);
                warehouseInventory.setWarehouse(warehouse);
                warehouseInventory.setStock(suppToWarehouse.getTotal());
                warehouseInventoryRepository.save(warehouseInventory);
            }
            return suppToWarehouseRepository.save(suppToWarehouse);
        }).orElseThrow(()->new NotFoundException("data tidak ada"));
        return new ResponseEntity<>(supp, HttpStatus.CREATED);
    }
}