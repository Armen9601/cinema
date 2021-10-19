package com.example.common.service.serviceImpl;

import com.example.common.entity.Food;
import com.example.common.enums.FoodCategory;
import com.example.common.repository.FoodRepository;
import com.example.common.service.FoodService;
import com.example.common.util.CustomMultipartFile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.example.common.service.serviceImpl.MovieServiceImpl.compressImage;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    @Value("C:\\Java2021\\cinemas\\web\\src\\main\\resources\\static\\assets\\images\\foods\\")
    private String uploadDir;

    private final MovieServiceImpl movieService;
    private final FoodRepository foodRepository;

    @Override
    public void addFood(Food food, MultipartFile multipartFile, String category) throws IOException {


        if (!multipartFile.isEmpty()) {
            if (multipartFile.getSize() > 1000000) {
                String png = "intermediate.png";
                CustomMultipartFile customMultipartFile = new CustomMultipartFile(multipartFile.getBytes(), png);
                String picUrl = compressImage(customMultipartFile, uploadDir, png);
                food.setPicUrl(picUrl);
            } else {
                String picUrl = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
                multipartFile.transferTo(new File(uploadDir + File.separator + picUrl));
                food.setPicUrl(picUrl);
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
        if (category != null) {
            return getByCategory(category);
        } else
            return foodRepository.findAll();
    }
}
