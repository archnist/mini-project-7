package id.co.indivara.jdt12.wharehouseApp.controller;

import id.co.indivara.jdt12.wharehouseApp.entity.Store;
import id.co.indivara.jdt12.wharehouseApp.repo.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/store")
public class StoreController {
    @Autowired
    private StoreRepository storeRepository;

    @PostMapping("/register")
    public Store addStore(@RequestBody Store store){
        store.setStoreId("str" + (storeRepository.count() + 1));
        store.setDate(new Date());
        return storeRepository.save(store);
    }

    @GetMapping("/show/all")
    public List<Store> showAll(){
        return (List<Store>) storeRepository.findAll();
    }

    @GetMapping("/show/{storeName}")
    public List<Store> findByName(@PathVariable("storeName") String storeName){
        return (List<Store>) storeRepository.findByStoreName(storeName);
    }
}
