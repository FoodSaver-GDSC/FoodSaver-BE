package com.gdsc.foodsaver.domain.foodbank.entity;

import com.gdsc.foodsaver.domain.foodbank.dto.FoodbankRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Foodbank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    private String address;
    private String detailAddress;
    private String phoneNumber;

    @Builder
    public Foodbank(String name, String location, String address, String detailAddress, String phoneNumber) {
        this.name = name;
        this.location = location;
        this.address = address;
        this.detailAddress = detailAddress;
        this.phoneNumber = phoneNumber;
    }
}
