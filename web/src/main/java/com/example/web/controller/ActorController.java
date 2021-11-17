package com.example.web.controller;

import com.example.common.entity.Actor;
import com.example.common.properties.FoodProperties;
import com.example.common.service.ActorService;
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
public class ActorController {

    private final ActorService actorService;
    private final FoodProperties actorProperties;


    @GetMapping("/admin/addActors")
    public String add(@RequestParam int movieId,
                      ModelMap modelMap) {
        modelMap.addAttribute("movie", movieId);
        return "add-actor";
    }

    @GetMapping("/actorDetails")
    public String actorDetails(@RequestParam int actorId,
                               @RequestParam int movieId,
                               ModelMap modelMap) {
        modelMap.addAttribute("actor", actorService.getById(actorId));
        modelMap.addAttribute("movie", movieId);
        return "actorDetails";
    }

    @GetMapping("/admin/deleteActor")
    public String delete(@RequestParam int id,
                         @RequestParam int movieId) {
        actorService.delete(id);
        return "redirect:/movieDetails?id="+movieId;
    }

    @GetMapping("/actorImg")
    void productImage(
            @RequestParam("actorUrl") String productUrl,
            HttpServletResponse response
    ) throws IOException {
        InputStream in = new FileInputStream(actorProperties.getFoodImg() + File.separator + productUrl);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }

    @PostMapping("/admin/addActors")
    public String add(@ModelAttribute Actor actor,
                      @RequestParam int movieId,
                      @RequestParam("pictureUrl") MultipartFile multipartFile) {
        actorService.save(actor, movieId, multipartFile);
        return "redirect:/movieDetails?id=" + movieId;
    }


}
