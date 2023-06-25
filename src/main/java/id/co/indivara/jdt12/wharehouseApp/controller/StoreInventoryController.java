package id.co.indivara.jdt12.wharehouseApp.controller;

import id.co.indivara.jdt12.wharehouseApp.entity.Goods;
import id.co.indivara.jdt12.wharehouseApp.entity.Store;
import id.co.indivara.jdt12.wharehouseApp.entity.StoreInventory;
import id.co.indivara.jdt12.wharehouseApp.repo.StoreInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/store")
@CrossOrigin("http://localhost:8081")
public class StoreInventoryController {
    @Autowired
    StoreInventoryRepository storeInventoryRepository;

    @GetMapping("/find/{goodsId}/{storeId}")
    public StoreInventory findByGoodsAndStore(@PathVariable("goodsId")Goods goodsId, @PathVariable("storeId")Store store){
        return storeInventoryRepository.findByGoodsAndStore(goodsId,store);
    }
}
