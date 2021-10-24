package com.example.common.repository;

import com.example.common.entity.Food;
import com.example.common.enums.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Integer> {

    List<Food> findByFoodCategory(FoodCategory category);

}
