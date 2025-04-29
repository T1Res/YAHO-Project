package com.njm.yaho.controller.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.njm.yaho.service.schedule.ImageService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProfileController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/profile")
    public String getProfile(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("USER_ID");

        String profileImagePath;

        if (userId != null) {
            String userProfileImg = imageService.getUserProfileImgById(userId);
            if (userProfileImg != null && !userProfileImg.isEmpty()) {
                profileImagePath = userProfileImg;
            } else {
                profileImagePath = "/IMG/kibon_image.jpg";
            }
        } else {
            profileImagePath = "/IMG/kibon_image.jpg";
        }

        model.addAttribute("profileImagePath", profileImagePath);
        return "profile"; // profile.html 뷰
    }
}