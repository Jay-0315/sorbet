package com.sorbet.consulting.controller;

import com.sorbet.consulting.domain.Post;
import com.sorbet.consulting.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class PostController {
    private final PostService postService;

    //게시판 메인페이지
    //모든 글의 카테고리,제목을 목록으로 출력
    @GetMapping
    public String postList(Model model) {
        model.addAttribute("posts", postService.getAll());
        return "posts/main"; //templates/posts/main.html
    }
    //글 작성 폼 페이지 반환
    @GetMapping
    public String createPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "posts/create"; //templates/posts/creat.hteml
    }
    //글 저장 처리
    @PostMapping("/new")
    public String savePost(@ModelAttribute Post post) {
        postService.save(post);
        return "redirect:/posts";
    }
    //글 열람 페이지
    //추후에 권한 체크 필요
    @GetMapping("/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        Optional<Post> post = postService.getPost(id);
        if (post.isPresent()) {
            model.addAttribute("Post", post.get());
            return "posts/view"; //templates/posts/view.html
        }
        return "redirect:/posts"; //없는 글이면 목록으로 리다이렉트
    }
}