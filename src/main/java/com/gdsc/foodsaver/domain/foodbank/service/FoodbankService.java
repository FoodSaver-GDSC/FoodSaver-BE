package com.gdsc.foodsaver.domain.foodbank.service;

import com.gdsc.foodsaver.domain.foodbank.dto.FoodbankRequestDto;
import com.gdsc.foodsaver.domain.foodbank.entity.Foodbank;
import com.gdsc.foodsaver.domain.foodbank.repository.FoodbankPagenationRepository;
import com.gdsc.foodsaver.domain.foodbank.repository.FoodbankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodbankService {
    private final FoodbankRepository foodbankRepository;
    private final FoodbankPagenationRepository foodbankPagenationRepository;

    public List<Foodbank> retrieveFoodbankAll() {
        return foodbankRepository.findAll();
    }

    public void save(FoodbankRequestDto dto) {
        Foodbank foodbank = Foodbank.builder()
                .name(dto.getName())
                .location(dto.getLocation())
                .address(dto.getAddress())
                .phoneNumber(dto.getPhoneNumber())
                .build();

        foodbankRepository.save(foodbank);
    }

    public Page<Foodbank> getFoodBanks(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Foodbank> foodBanks = foodbankPagenationRepository.findAll(pageRequest);

        return foodBanks;
    }

    public Page<Foodbank> searchFoodBanks(String keyword, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return foodbankRepository.findByAddressContainingIgnoreCase(keyword, pageRequest);
    }
}
