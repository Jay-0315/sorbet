package com.sorbet.controller;

import com.sorbet.dto.CommentDto;
import com.sorbet.entity.Comment;
import com.sorbet.entity.Post;
import com.sorbet.security.CustomUserDetails;
import com.sorbet.service.CommentService;
import com.sorbet.service.PostService;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

//    @GetMapping("/post/{id}/comment")
//    public String getPostDetail(@PathVariable Long id, Model model, Principal principal) {
//        Post post = postService.findById(id);
//        List<Comment> comments = commentService.getCommentsByPostId(id);
//
//        model.addAttribute("post", post);
//        model.addAttribute("comments", comments);
//        model.addAttribute("commentDto", new CommentDto());
//        if (principal != null) model.addAttribute("writer", principal.getName());
//
//        return "post"; // post.html
//    }

    @PostMapping("/comments")
    public String writeComment(@AuthenticationPrincipal CustomUserDetails customUserDetails, @ModelAttribute CommentDto dto) {
        commentService.saveComment(dto, customUserDetails);
        return "redirect:/posts/" + dto.getPostId();
    }
    // 댓글 삭제
    @PostMapping("/comments/delete/{id}")
    public String deleteComment(@PathVariable Long id, @RequestParam Long postId) {
        commentService.deleteComment(id); // 댓글 ID로 삭제
        return "redirect:/posts/" + postId;
    }



}
