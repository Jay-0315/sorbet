package com.sorbet.controller;

import com.sorbet.domain.CharacterType;
import com.sorbet.security.CustomUserDetails;
import com.sorbet.service.GatchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GatchaController {

    private final GatchaService gatchaService;

    // 뽑기 페이지 진입
    @GetMapping("/gatcha")
    public String gatchaPage() {
        return "gatcha"; // → templates/gatcha.html 로 연결
    }


    // GatchaController.java
    @PostMapping("/gatcha/draw10")
    public String draw10(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        String userId = userDetails.getUsername();
        List<CharacterType> results = gatchaService.drawMultiple(userId, 10); // 10번 뽑기

        model.addAttribute("results", results);
        return "gatcha-result-multi"; // 새로운 템플릿으로 렌더링
    }


    // 캐릭터 뽑기 실행
    @PostMapping("/gatcha/draw")
    public String draw(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        try {
            CharacterType result = gatchaService.drawCharacter(userDetails.getUsername());
            model.addAttribute("result", result);
            return "gatcha-result";

        } catch (IllegalStateException e) {
            // 포인트 부족 시 에러 메시지 전달
            model.addAttribute("error", e.getMessage());
            return "gatcha"; // 뽑기 페이지로 돌아감
        }
    }
}