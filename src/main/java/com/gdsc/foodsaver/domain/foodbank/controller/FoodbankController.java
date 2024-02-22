package com.gdsc.foodsaver.domain.foodbank.controller;

import com.gdsc.foodsaver.domain.foodbank.dto.FoodbankRequestDto;
import com.gdsc.foodsaver.domain.foodbank.entity.Foodbank;
import com.gdsc.foodsaver.domain.foodbank.service.FoodbankCsvDataLoaderService;
import com.gdsc.foodsaver.domain.foodbank.service.FoodbankService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/foodbanks")
public class FoodbankController {
    private final FoodbankService foodbankService;
    private final FoodbankCsvDataLoaderService foodbankCsvDataLoaderService;

    @Value("${foodbank-csv}")
    private String foodbankCsv;
    @Value("${foodbank-deploy-csv}")
    private String foodbankDeployCsv;

    @GetMapping("/all")
    public List<Foodbank> retrieveAll() {
        return foodbankService.retrieveFoodbankAll();
    }

    @GetMapping
    public Page<Foodbank> getFoodBanks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return foodbankService.getFoodBanks(page, size);
    }

    @GetMapping("/search")
    public Page<Foodbank> searchFoodBanks(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return foodbankService.searchFoodBanks(keyword, page, size);
    }

    @PostMapping
    public void save(FoodbankRequestDto dto) {
        foodbankService.save(dto);
    }



//    //////////////////////////////////////////////////////////////////////////
//    @GetMapping("/load-data")
//    public String saveAllData() {
//        foodbankCsvDataLoaderService.loadCsvData(foodbankCsv);
////        foodbankCsvDataLoaderService.loadCsvData(foodbankDeployCsv);
//        return "Data Load Done.";
//    }
}
