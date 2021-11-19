package com.example.rest.endpoint;

import com.example.common.entity.Food;
import com.example.common.properties.FoodProperties;
import com.example.common.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FoodEndpoint {

    private final FoodService foodService;
    private final FoodProperties foodProperties;

    @GetMapping("/admin/food")
    public List<Food> getAllFood(){
        return foodService.getAllFoods();
    }

    @GetMapping("/popcorn")
    public List<Food> getAllByCategory (@RequestParam String category){

        return foodService.getAll(category);
    }

    @GetMapping ("foodImg")
    public  void productImage(@RequestParam("foodUrl") String productUrl, HttpServletResponse response) throws IOException {
        foodService.downloadPicByName(productUrl, response);
    }

     @PostMapping("/saveFood")
    public Food save(@RequestBody Food food){
        return foodService.save(food);
    }

    @PostMapping("/addFood")
    public  Food addPicById(@RequestParam int id, @RequestParam MultipartFile multipartFile, @RequestParam String category) throws IOException {
        return foodService.addById(id,multipartFile, category);
    }

    @PostMapping("/buyFood")
    public  void buyFood(HttpSession httpSession, @RequestParam int foodId,
                         @RequestParam int count, @RequestParam String name){
        foodService.addFoodInSession(foodId, count, name, httpSession);
    }

}
