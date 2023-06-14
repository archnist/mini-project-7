package id.co.indivara.jdt12.wharehouseApp.controller;
import id.co.indivara.jdt12.wharehouseApp.entity.*;
import id.co.indivara.jdt12.wharehouseApp.exception.NotFoundException;
import id.co.indivara.jdt12.wharehouseApp.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:8080")
@RequestMapping("/store")
@RestController
public class WarehouseToStoreController {
    @Autowired
    WarehouseToStoreRepository warehouseToStoreRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    StoreInventoryRepository storeInventoryRepository;
    @Autowired
    WarehouseInventoryRepository warehouseInventoryRepository;

    @PostMapping("/delivery/{goodsId}/{whouseSrc}/to/{storeDest}")
    public ResponseEntity<WarehouseToStore> deliveryGoods(@PathVariable("goodsId") Goods goodsId, @PathVariable("whouseSrc") Warehouse warehouseSrc, @PathVariable("storeDest")Store storeDest, @RequestBody WarehouseToStore warehouseToStoreReq){
        WarehouseToStore wts = warehouseRepository.findById(warehouseSrc.getWarehouseId()).map(warehouse -> {
            StoreInventory storeInventory = new StoreInventory();
            StoreInventory si = storeInventoryRepository.findByGoodsAndStore(goodsId,storeDest);
            WarehouseInventory wi = warehouseInventoryRepository.findByGoodsAndWarehouse(goodsId,warehouseSrc);
            try {
                if (wi.getStock() >= warehouseToStoreReq.getTotal()){
                    si.setGoods(goodsId);
                    si.setStore(storeDest);
                    si.setStock(si.getStock() + warehouseToStoreReq.getTotal());
                    storeInventoryRepository.save(si);
                    wi.setStock(wi.getStock() - warehouseToStoreReq.getTotal());
                }
            }catch (Exception ex){
                if (wi.getStock() >= warehouseToStoreReq.getTotal()){
                    storeInventory.setGoods(goodsId);
                    storeInventory.setStore(storeDest);
                    storeInventory.setStock(warehouseToStoreReq.getTotal());
                    storeInventoryRepository.save(storeInventory);
                    wi.setStock(wi.getStock() - warehouseToStoreReq.getTotal());
                }
            }
            warehouseInventoryRepository.save(wi);
            WarehouseToStore warehouseToStore = new WarehouseToStore();
            if (wi.getStock() >= warehouseToStoreReq.getTotal()){
                warehouseToStore.setTransactionId("T" + (transactionRepository.count() + 1));
                warehouseToStore.setWarehouseSrc(warehouse);
                warehouseToStore.setStoreDest(storeDest);
                warehouseToStore.setGoodId(goodsId.getGoodId());
                warehouseToStore.setTotal(warehouseToStoreReq.getTotal());

                Transaction transaction = new Transaction();
                transaction.setTransactionId("T" + (transactionRepository.count() + 1));
                transaction.setType("Warehouse To Store");
                transaction.setGoods(goodsId);
                transactionRepository.save(transaction);
            }
            return warehouseToStoreRepository.save(warehouseToStore);
        }).orElseThrow(()-> new NotFoundException("data tidak ada"));
        return new ResponseEntity<>(wts, HttpStatus.CREATED);
    }
}
