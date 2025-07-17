package com.sorbet.controller;

import com.sorbet.entity.User;
import com.sorbet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    // 로그인 폼
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // 로그아웃 폼
    @GetMapping("/logout")
    public String logout() {
        return "login";
    }

    // 회원가입 폼
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("member", new User());
        return "register";
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String register(@ModelAttribute("member") User user, Model model) {
        try {
            userService.join(user);  // 🔧 정적 호출 → 인스턴스 호출로 수정
            return "redirect:/login";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }
    }
}
