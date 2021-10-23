package com.example.common.service.serviceImpl;

import com.example.common.entity.Food;
import com.example.common.enums.FoodCategory;
import com.example.common.properties.FoodProperties;
import com.example.common.repository.FoodRepository;
import com.example.common.service.FoodService;
import com.example.common.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodProperties foodProperties;
    private final FoodRepository foodRepository;

    @Override
    public void addFood(Food food, MultipartFile multipartFile, String category) throws IOException {
        if (!multipartFile.isEmpty()) {
            if (multipartFile.getSize() > foodProperties.getFileMaxSize()) {
                food.setPicUrl(FileUploadUtil.getSmallPicUrl(multipartFile));
            } else {
                food.setPicUrl(FileUploadUtil.getPicUrl(multipartFile));
            }
        }
        food.setFoodCategory(FoodCategory.valueOf(category.toUpperCase(Locale.ROOT)));
        foodRepository.save(food);
    }

    @Override
    public List<Food> getByCategory(String category) {
        FoodCategory foodCategory = FoodCategory.valueOf(category);
        return foodRepository.findByFoodCategory(foodCategory);
    }

    @Override
    public List<Food> getFoods(String category) {
        return category == null ? foodRepository.findAll() : getByCategory(category);
    }
}
