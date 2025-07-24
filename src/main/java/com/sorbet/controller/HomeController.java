package com.sorbet.controller;

import com.sorbet.entity.Post;
import com.sorbet.entity.User;
import com.sorbet.repository.PostRepository;
import com.sorbet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserRepository userRepository;
    private final PostRepository postRepository; // 게시글 repository 주입

    @GetMapping({"/", "/home"})
    public String home(@RequestParam(value = "category", required = false) String category,
                       Model model, Principal principal) {

        if (principal != null) {
            String userId = principal.getName();
            Optional<User> user = userRepository.findByUserLoginId(userId);
            user.ifPresent(value -> model.addAttribute("nickname", value.getNickname()));
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

