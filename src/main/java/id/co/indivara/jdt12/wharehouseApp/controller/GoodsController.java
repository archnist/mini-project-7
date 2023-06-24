package id.co.indivara.jdt12.wharehouseApp.controller;

import id.co.indivara.jdt12.wharehouseApp.entity.Goods;
import id.co.indivara.jdt12.wharehouseApp.repo.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:8081")
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    GoodsRepository goodsRepository;

    @GetMapping("/show/all")
    public List<Goods> showAll(){
        return (List<Goods>) goodsRepository.findAll();
    }
    @GetMapping("show/{goodsId}")
    public Goods findById(@PathVariable("goodsId") String goodsId){
        return goodsRepository.findById(goodsId).get();

    }
}
