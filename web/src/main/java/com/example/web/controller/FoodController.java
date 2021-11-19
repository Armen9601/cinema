package com.example.web.controller;

import com.example.common.dto.BasketDto;
import com.example.common.entity.Food;
import com.example.common.properties.FoodProperties;
import com.example.common.service.FoodService;
import com.example.common.service.MovieService;
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
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequiredArgsConstructor
public class FoodController {

    private final FoodProperties foodProperties;
    private final FoodService foodService;
    private final MovieService movieService;

    @GetMapping("/admin/food")
    public String addFoodPage() {
        return "addFood";
    }

    /*----foood------*/
    @GetMapping("/popcorn")
    public String movieFood(
            ModelMap modelMap,
            HttpSession httpSession,
            @RequestParam(value = "category", required = false) String category
    ) {
        BasketDto basketDto = ((BasketDto) httpSession.getAttribute("basket"));
        if (basketDto == null) {
            return "redirect:/user/viewAll";
        }
        modelMap.addAttribute("foods", foodService.getAll(category));
        modelMap.addAttribute("basketDto", basketDto);
        modelMap.addAttribute("movie", movieService.getById(basketDto.getMovieId()));
        modelMap.addAttribute("total", foodService.totalPrice(httpSession));
        return "popcorn";
    }

    @GetMapping("/foodImg")
    void productImage(
            @RequestParam("foodUrl") String productUrl,
            HttpServletResponse response
    ) throws IOException {
        InputStream in = new FileInputStream(foodProperties.getFoodImg() + File.separator + productUrl);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }

    @PostMapping("/addFood")
    public String addFood(
            @ModelAttribute Food food,
            @RequestParam("picture") MultipartFile multipartFile,
            @RequestParam("category") String category
    ) throws IOException {
        foodService.add(food, multipartFile, category);
        return "redirect:/admin/food";
    }

    @PostMapping("/buyFood")
    public String buyFood(HttpSession httpSession,
                          @RequestParam("foodId") int foodId,
                          @RequestParam("qtybutton") int count,
                          @RequestParam("name") String name) {
        foodService.addFoodInSession(foodId, count, name, httpSession);
        return "redirect:/popcorn";
    }
}
