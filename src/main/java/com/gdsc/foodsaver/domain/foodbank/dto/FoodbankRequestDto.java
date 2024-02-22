package com.gdsc.foodsaver.domain.foodbank.dto;

import lombok.Data;

@Data
public class FoodbankRequestDto {
    private String name;
    private String location;
    private String address;
    private String phoneNumber;
}
