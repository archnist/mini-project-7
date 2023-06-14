package id.co.indivara.jdt12.wharehouseApp.controller;

import id.co.indivara.jdt12.wharehouseApp.entity.Store;
import id.co.indivara.jdt12.wharehouseApp.repo.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/store")
public class StoreController {
    @Autowired
    private StoreRepository storeRepository;

    @PostMapping("/register")
    public Store addStore(@RequestBody Store store){
        System.out.println(store.getStoreName() + " Added Successfully!");
        return storeRepository.save(store);
    }
}
