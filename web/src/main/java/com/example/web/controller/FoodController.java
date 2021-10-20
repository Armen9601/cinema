package com.example.web.controller;

import com.example.common.entity.Food;
import com.example.common.properties.FoodProperties;
import com.example.common.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequiredArgsConstructor
public class FoodController {

    private final FoodProperties foodProperties;
    private final FoodService foodService;

    @GetMapping("/admin/food")
    public String addFoodPage() {
        return "addFood";
    }

    @PostMapping("/addFood")
    public String addFood(@ModelAttribute Food food, @RequestParam("picture") MultipartFile multipartFile,
                          @RequestParam("category") String category) throws IOException {
        foodService.addFood(food, multipartFile, category);
        return "redirect:/admin/food";

    }

    @GetMapping("/popcorn")
    public String movieFood(ModelMap modelMap,
                            @RequestParam(value = "category", required = false) String category) {
        modelMap.addAttribute("foods", foodService.getFoods(category));
        return "popcorn";
    }

    @GetMapping("/foodImg")
    void productImage(@RequestParam("foodUrl") String productUrl, HttpServletResponse response) throws IOException {
        InputStream in = new FileInputStream(foodProperties.getFoodImg() + File.separator + productUrl);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }
}
