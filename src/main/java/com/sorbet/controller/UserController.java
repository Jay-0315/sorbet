package com.sorbet.controller;

import com.sorbet.dto.UserRegisterDto;
import com.sorbet.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    // test
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
        model.addAttribute("member", new UserRegisterDto()); // 💡 DTO로 변경
        return "register";
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("member") UserRegisterDto dto,
                           BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register"; // 유효성 실패 시 다시 폼으로
        }

        try {
            userService.join(dto);
            return "redirect:/login";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }

    }
}
