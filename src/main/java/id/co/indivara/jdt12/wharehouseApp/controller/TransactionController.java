package id.co.indivara.jdt12.wharehouseApp.controller;
import id.co.indivara.jdt12.wharehouseApp.entity.*;
import id.co.indivara.jdt12.wharehouseApp.repo.SuppToWarehouseRepository;
import id.co.indivara.jdt12.wharehouseApp.repo.TransactionRepository;
import id.co.indivara.jdt12.wharehouseApp.repo.WarehouseToStoreRepository;
import id.co.indivara.jdt12.wharehouseApp.repo.WarehouseToWarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/transaction")
@RestController
public class TransactionController {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    SuppToWarehouseRepository suppToWarehouseRepository;
    @Autowired
    WarehouseToWarehouseRepository warehouseToWarehouseRepository;
    @Autowired
    WarehouseToStoreRepository warehouseToStoreRepository;

    @GetMapping("/all")
    public List<Transaction> showAllTransaction(){
        return (List<Transaction>) transactionRepository.findAll();
    }

    @GetMapping("/{type}")
    public List<Transaction> showByType(@PathVariable("type") String type){
        return (List<Transaction>) transactionRepository.findByType(type);
    }

    @GetMapping("/suppToWhouse")
    public List<SuppToWarehouse> findSupplierToWarehouseTransaction(){
        return (List<SuppToWarehouse>) suppToWarehouseRepository.findAll();
    }
    @GetMapping("/whouseToWhouse")
    public List<WarehouseToWarehouse> findWarehouseToWarehouseTransaction(){
        return (List<WarehouseToWarehouse>) warehouseToWarehouseRepository.findAll();
    }
    @GetMapping("/whouseToStore")
    public List<WarehouseToStore> findWarehouseToStoreTransaction(){
        return (List<WarehouseToStore>) warehouseToStoreRepository.findAll();
    }
}
