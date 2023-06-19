package id.co.indivara.jdt12.wharehouseApp.controller;
import id.co.indivara.jdt12.wharehouseApp.entity.*;
import id.co.indivara.jdt12.wharehouseApp.exception.NotFoundException;
import id.co.indivara.jdt12.wharehouseApp.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin("http://localhost:8081")
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
    public ResponseEntity<WarehouseToWarehouse> transferToWarehouse(@PathVariable("goodsId") Goods goodsId, @PathVariable("source") Warehouse warehouseSrc, @PathVariable("destination") Warehouse warehouseDst, @RequestBody WarehouseToWarehouse transfer) {
        WarehouseToWarehouse warehouseToWarehouse = warehouseRepository.findById(warehouseSrc.getWarehouseId()).map(warehouse -> {
            WarehouseInventory whSrc = warehouseInventoryRepository.findByGoodsAndWarehouse(goodsId,warehouseSrc);
            WarehouseInventory wDest = warehouseInventoryRepository.findByGoodsAndWarehouse(goodsId,warehouseDst);
            WarehouseInventory wi = new WarehouseInventory();
            WarehouseToWarehouse wtw = new WarehouseToWarehouse();
            if (whSrc.getStock() >= transfer.getTotal()){
                whSrc.setStock(whSrc.getStock() - transfer.getTotal());
                try {
                    wDest.setStock(wDest.getStock() + transfer.getTotal());
                    warehouseInventoryRepository.save(wDest);
                }catch (Exception ex){
                    wi.setGoods(goodsId);
                    wi.setWarehouse(warehouseDst);
                    wi.setStock(transfer.getTotal());
                    warehouseInventoryRepository.save(wi);
                }
                wtw.setTransactionId("T" + (transactionRepository.count() + 1));
                wtw.setWarehouseSource(warehouse);
                wtw.setWarehouseDestination(warehouseDst);
                wtw.setGoods(goodsId);
                wtw.setTotal(transfer.getTotal());
                wtw.setDateTime(new Date());
                Transaction ts = new Transaction();
                ts.setTransactionId("T" + (transactionRepository.count() + 1));
                ts.setType("Warehouse To Warehouse");
                ts.setGoods(goodsId);
                ts.setDateTime(new Date());
                transactionRepository.save(ts);
            }
            return warehouseToWarehouseRepository.save(wtw);
        }).orElseThrow(()-> new NotFoundException("Warehouse Not Found!"));
        return new ResponseEntity<>(warehouseToWarehouse,HttpStatus.CREATED);
    }
}