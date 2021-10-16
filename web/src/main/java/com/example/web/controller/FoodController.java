package com.example.web.controller;

import com.example.common.entity.Food;
import com.example.common.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;

    @GetMapping("/admin/food")
    public String addFoodPage() {
        return "addFood";
    }

    @PostMapping("/addFood")
    public String addFood(@ModelAttribute Food food) {

        foodService.addFood(food);

        return "redirect:/admin/food";

    }

}
