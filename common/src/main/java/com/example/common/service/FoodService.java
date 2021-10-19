package com.example.common.service;

import com.example.common.entity.Food;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FoodService {

    void addFood(Food food, MultipartFile multipartFile, String category) throws IOException;

    List<Food> getByCategory(String category);

    List<Food> getFoods(String category);
}
