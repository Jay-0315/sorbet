// src/main/java/com/sorbet/controller/MypageController.java
package com.sorbet.controller;

import com.sorbet.dto.UserActivityDto;
import com.sorbet.entity.User;
import com.sorbet.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService mypageService;

    @GetMapping("/mypage")
    public String myPage(@AuthenticationPrincipal User user, Model model) {
        UserActivityDto dto = mypageService.getUserInfo(user);
        model.addAttribute("user", dto);
        return "mypage";
    }
}
