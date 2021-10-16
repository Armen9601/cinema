package com.example.common.service.serviceImpl;

import com.example.common.entity.Food;
import com.example.common.repository.FoodRepository;
import com.example.common.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;

    @Override
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    @Override
    public void addFood(Food food) {
        foodRepository.save(food);
    }
}
