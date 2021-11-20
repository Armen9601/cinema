package com.example.common.service.serviceImpl;

import com.example.common.dto.BasketDto;
import com.example.common.dto.FoodDto;
import com.example.common.entity.Food;
import com.example.common.enums.FoodCategory;
import com.example.common.properties.FoodProperties;
import com.example.common.repository.FoodRepository;
import com.example.common.service.FoodService;
import com.example.common.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodProperties foodProperties;
    private final FoodRepository foodRepository;
    private final FileUploadUtil fileUploadUtil;

    @Override
    public void add(Food food, MultipartFile multipartFile, String category) throws IOException {
        if (!multipartFile.isEmpty()) {
            food.setPicUrl(fileUploadUtil.getSmallPicUrl(multipartFile, false));
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
    public List<Food> getAll(String category) {
        return StringUtils.isEmpty(category)
                ? foodRepository.findAll()
                : getByCategory(category);
    }

    @Override
    public void addFoodInSession(int foodId, int count, String name, HttpSession httpSession) {
        FoodDto foodDto = new FoodDto(foodId, count, name);

        BasketDto basketDto = ((BasketDto) httpSession.getAttribute("basket"));
        if (basketDto.getFoods().size() == 0) {
            basketDto.getFoods().add(foodDto);
        } else if (basketDto.getFoods().stream().anyMatch(o -> o.getName().equals(name))) {
            FoodDto food = basketDto.getFoods().stream().filter(o -> o.getName().equals(name)).findFirst().get();
            food.setCount(food.getCount() + count);
        } else {
            basketDto.getFoods().add(foodDto);
        }

    }

    @Override
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    @Override
    public Food save(Food food) {
        return foodRepository.save(food);
    }

    @Override
    public void downloadPicByName(String productUrl, HttpServletResponse response) throws IOException {
        InputStream in = new FileInputStream(foodProperties.getFoodImg()+ File.separator+productUrl);
        response.setContentType(MediaType.IMAGE_GIF_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }

    @Override
    public Food addById(int id, MultipartFile multipartFile, String category) throws IOException {
        Food food = foodRepository.getById(id);
        if (!multipartFile.isEmpty()) {
            food.setPicUrl(fileUploadUtil.getSmallPicUrl(multipartFile, false));
        }
        food.setFoodCategory(FoodCategory.valueOf(category.toUpperCase(Locale.ROOT)));
        foodRepository.save(food);
        return null;
    }

    @Override
    public int totalPrice(HttpSession httpSession) {
        BasketDto basketDto = ((BasketDto) httpSession.getAttribute("basket"));
        int foodTotalPrice = 0;
        int placeTotalPrice = basketDto.getMyPlaces().size() * 12;
        if (basketDto.getFoods().size() > 0) {
            for (FoodDto food : basketDto.getFoods()) {
                foodTotalPrice += (foodRepository.getById(food.getId()).getPrice() * food.getCount());
            }
        }
        return foodTotalPrice + placeTotalPrice;
    }

}


