// src/main/java/com/sorbet/controller/MypageController.java
package com.sorbet.controller;

import com.sorbet.domain.CharacterType;
import com.sorbet.dto.CharacterSummaryDto;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator; // Comparator 임포트
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService mypageService;
    private final PostService postService;
    private final UserService userService;

    @GetMapping("/mypage")
    public String myPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model,
                         @RequestParam(name = "sort", required = false) String sort) {

        // 캐시된 유저 대신 DB에서 다시 불러오기
        User user = userService.findByUserId(userDetails.getUsername());

        UserActivityDto dto = mypageService.getUserInfo(user);
        long postCount = postService.getPostCountByUser(user);

        List<CharacterSummaryDto> inventorySummary = new ArrayList<>(mypageService.getInventorySummary(user));

        // 2. 'grade' 정렬 요청이 있을 경우, 인벤토리 리스트를 정렬
        if ("grade".equals(sort)) {
            // CharacterSummaryDto에서 CharacterType을 가져오고, 거기서 Grade를 기준으로 정렬
            inventorySummary.sort(Comparator.comparing(summary -> summary.getCharacterType().getGrade()));
        }

        model.addAttribute("user", dto);
        model.addAttribute("countByUser", postCount);
        model.addAttribute("inventory", inventorySummary); // 정렬되었거나, 정렬되지 않은 리스트가 담김
        return "mypage";
    }

    @PostMapping("/mypage/set-main-character")
    public String setMainCharacter(@AuthenticationPrincipal CustomUserDetails userDetails,
                                   @RequestParam("character") CharacterType characterType) {
        userService.setMainCharacter(userDetails.getUsername(), characterType);
        return "redirect:/mypage";
    }
}
