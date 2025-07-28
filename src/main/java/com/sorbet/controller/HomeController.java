package com.sorbet.controller;

import com.sorbet.entity.Post;
import com.sorbet.entity.User;
import com.sorbet.repository.PostRepository;
import com.sorbet.repository.UserRepository;
import com.sorbet.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserRepository userRepository;
    private final PostRepository postRepository; // 게시글 repository 주입

    @GetMapping({"/", "/home"})
    public String home(@RequestParam(value = "category", required = false) String category,
                       Model model, 
                       @AuthenticationPrincipal CustomUserDetails userDetails) {

        // 로그인한 사용자 정보 추가
        if (userDetails != null) {
            model.addAttribute("nickname", userDetails.getNickname());
            model.addAttribute("isLoggedIn", true);
        } else {
            model.addAttribute("isLoggedIn", false);
        }

        List<Post> posts;
        if (category != null && !category.isEmpty() && !category.equals("all")) {
            posts = postRepository.findByCategory(category);
            model.addAttribute("selectedCategory", category);
        } else {
            posts = postRepository.findAll();
            model.addAttribute("selectedCategory", "all");
        }

        model.addAttribute("posts", posts);
        return "home";
    }
}

