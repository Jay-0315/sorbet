package com.sorbet.controller;

import com.sorbet.dto.PostDto;
import com.sorbet.entity.Post;
import com.sorbet.entity.User;
import com.sorbet.repository.PostRepository;
import com.sorbet.repository.UserRepository;
import com.sorbet.security.CustomUserDetails;
import com.sorbet.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostService postService;// 게시글 repository 주입


    @GetMapping({"/", "/home"})
    public String home(@RequestParam(value = "category", required = false, defaultValue = "all") String category,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       Model model,
                       @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails != null) {
            model.addAttribute("nickname", userDetails.getNickname());
            model.addAttribute("isLoggedIn", true);
        } else {
            model.addAttribute("isLoggedIn", false);
        }

        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());
        Page<Post> postPage;
        List<PostDto> posts;

        // 검색어가 있을 경우
        if (keyword != null && !keyword.isBlank()) {
            Page<PostDto> searchPage = postService.searchByKeyword(keyword, pageable); // Page 타입으로 받고
            posts = searchPage.getContent(); // List<PostDto> 로 변환
            model.addAttribute("searchKeyword", keyword);
            model.addAttribute("currentPage", page); // 페이징도 추가 가능
            model.addAttribute("totalPages", searchPage.getTotalPages());
        } else {
            if (category != null && !category.equals("all")) {
                postPage = postRepository.findByCategoryOrderByCreatedAtDesc(category, pageable);
                model.addAttribute("selectedCategory", category);
            } else {
                postPage = postRepository.findAllByOrderByCreatedAtDesc(pageable);
                model.addAttribute("selectedCategory", "all");
            }

            posts = postService.getHomePostSummaries(pageable, category);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", postPage.getTotalPages());
        }

        model.addAttribute("posts", posts);
        return "home";
    }

}


