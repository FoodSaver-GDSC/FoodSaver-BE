package com.gdsc.foodsaver.domain.foodbank.repository;

import com.gdsc.foodsaver.domain.foodbank.entity.Foodbank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodbankRepository extends JpaRepository<Foodbank, Long> {
    Page<Foodbank> findByAddressContainingIgnoreCase(String keyword, Pageable pageable);
}
