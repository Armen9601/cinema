package com.example.rest.endpoint;

import com.example.common.entity.Food;
import com.example.common.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FoodEndpoint {
    private final FoodService foodService;

     @GetMapping("/addFood")
     public List<Food> getFoods(){
        return foodService.getAllFoods();
    }

    /*--add Food --*/
    @PostMapping("/addFood")
    public Food food(@RequestBody Food food){
        return foodService.addFood(food);
    }

}
