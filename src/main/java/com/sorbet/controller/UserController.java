// com.sorbet.controller.UserController.java
package com.sorbet.controller;

import com.sorbet.entity.User;
import com.sorbet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    // 로그인 폼
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // 회원가입 폼
    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password) {
        userService.register(username, password);  // ✅ 수정된 부분!
        return "redirect:/login";
    }
}
