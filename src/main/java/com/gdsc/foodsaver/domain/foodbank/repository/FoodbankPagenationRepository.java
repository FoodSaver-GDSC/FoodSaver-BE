package com.gdsc.foodsaver.domain.foodbank.repository;

import com.gdsc.foodsaver.domain.foodbank.entity.Foodbank;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FoodbankPagenationRepository extends PagingAndSortingRepository<Foodbank, Long> {
}
