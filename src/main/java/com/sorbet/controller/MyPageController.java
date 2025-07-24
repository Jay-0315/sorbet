// src/main/java/com/sorbet/controller/MypageController.java
package com.sorbet.controller;

import com.sorbet.dto.UserActivityDto;
import com.sorbet.entity.User;
import com.sorbet.repository.PostRepository;
import com.sorbet.repository.UserRepository;
import com.sorbet.security.CustomUserDetails;
import com.sorbet.service.MyPageService;
import com.sorbet.service.PostService;
import com.sorbet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService mypageService;
    private final PostService postService;
    private final UserService userService;

    @GetMapping("/mypage")
    public String myPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        // 캐시된 유저 대신 DB에서 다시 불러오기
        User user = userService.findByUserId(userDetails.getUsername());

        UserActivityDto dto = mypageService.getUserInfo(user);
        long postCount = postService.getPostCountByUser(user);

        model.addAttribute("user", dto);
        model.addAttribute("countByUser", postCount);
        return "mypage";
    }



}
