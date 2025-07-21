package com.sorbet.controller;

import com.sorbet.entity.User;
import com.sorbet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserRepository userRepository;

    @GetMapping({"/", "/home"})
    public String home(Model model, Principal principal) {
        if (principal != null) {
            String userId = principal.getName(); // 로그인된 사용자 아이디
            Optional<User> user = userRepository.findByUserId(userId);
            user.ifPresent(value -> model.addAttribute("nickname", value.getNickname()));
        }

        return "home";
    }
}
