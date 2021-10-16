package com.example.common.service;

import com.example.common.entity.Food;

import java.util.List;

public interface FoodService {

    List<Food> getAllFoods();

    void addFood(Food food);

}
