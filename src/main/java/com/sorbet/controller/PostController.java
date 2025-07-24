package com.sorbet.controller;

import com.sorbet.dto.CommentDto;
import com.sorbet.entity.User;
import com.sorbet.entity.Post;
import com.sorbet.security.CustomUserDetails;
import com.sorbet.service.CommentService;
import com.sorbet.service.PostService;
import com.sorbet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService;


    // 홈 화면: 전체 게시글 출력
//    @GetMapping("/")
//    public String home(Model model) {
//        model.addAttribute("posts", postService.findAll());
//        return "home";
//    }


    @GetMapping("/createpost")
    public String createForm(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Post post = new Post();

        if (userDetails instanceof CustomUserDetails customUserDetails) {
            post.setWriter(customUserDetails.getNickname());
        }

        model.addAttribute("post", post);
        return "createpost";
    }
    @PostMapping("/post/{id}/delete")
    public String deletePost(@PathVariable Long id,
                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        postService.deletePostByIdAndUser(id, userDetails.getUser());
        return "redirect:/home";
    }









    // 글 등록 처리
    @PostMapping("/createpost")
    public String submit(@ModelAttribute Post post) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = null;

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails customUserDetails) {
            user = customUserDetails.getUser(); // ✅ 먼저 user를 초기화
            post.setWriter(user.getNickname()); // ✅ 이제 Null 아님
            post.setUser(user); // ✅ 외래키 매핑
        }

        postService.save(post);
        return "redirect:/";
    }



    @GetMapping("/posts/{id}")
    public String viewPost(@AuthenticationPrincipal CustomUserDetails userDetails,
                           @PathVariable Long id,
                           Model model) {
        Post post = postService.findById(id);
        String username = userDetails.getUsername();
        User user = userService.findByUserId(username);

        // 🟡 작성자의 닉네임 기반 등급 정보 조회


        // 기본 정보
        model.addAttribute("post", post);


        // 댓글 관련 정보
        List<CommentDto> comments = commentService.getCommentsByPostId(id);
        model.addAttribute("comments", comments);
        model.addAttribute("user", user);
        model.addAttribute("commentDto", new CommentDto());
        model.addAttribute("userId", user.getId());

        return "post";
    }

}
