package com.example.solver.controller;

import com.example.solver.service.StoreAndDisplayPicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class PictureController {

    private final StoreAndDisplayPicService storeAndDisplayPicService;

    public PictureController(StoreAndDisplayPicService storeAndDisplayPicService) {
        this.storeAndDisplayPicService = storeAndDisplayPicService;
    }

    @GetMapping("/pictures")
    public String viewPictures(Model model) {
        List<Map<String, Object>> pictures = storeAndDisplayPicService.getAllPictures();
        model.addAttribute("pictures", pictures);
        return "pictures"; // Thymeleaf template: pictures.html
    }

    @PostMapping("/pictures/upload")
    public String uploadPicture(@RequestParam("title") String title,
            @RequestParam("picDate") Timestamp picDate,
            @RequestParam("picLocation") String picLocation,
            @RequestParam("image") MultipartFile imageFile) throws Exception {

        byte[] image = imageFile.getBytes();

        storeAndDisplayPicService.savePicture(
                title,
                picDate, // already a Timestamp!
                picLocation,
                image);

        return "redirect:/pictures";
    }

}
