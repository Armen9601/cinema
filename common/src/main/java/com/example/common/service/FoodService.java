package com.example.common.service;

import com.example.common.entity.Food;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public interface FoodService {

    void add(Food food, MultipartFile multipartFile, String category) throws IOException;

    List<Food> getByCategory(String category);

    List<Food> getAll(String category);

    void addFoodInSession(int foodId, int count, String name, HttpSession httpSession);

    int totalPrice(HttpSession httpSession);

    List<Food> getAllFoods();
}
