package com.sorbet.controller;

import com.sorbet.domain.ReactionType;
import com.sorbet.security.CustomUserDetails;
import com.sorbet.service.PostReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post/reaction")
public class PostReactionController {
    private final PostReactionService reactionService;

    @PostMapping("/{postId}/like")
    public ResponseEntity<Void> like(@PathVariable Long postId,
                                     @AuthenticationPrincipal CustomUserDetails userDetails) {
        reactionService.toggleReaction(postId, userDetails.getUserId(), ReactionType.LIKE);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{postId}/dislike")
    public ResponseEntity<Void> dislike(@PathVariable Long postId,
                                        @AuthenticationPrincipal CustomUserDetails userDetails) {
        reactionService.toggleReaction(postId, userDetails.getUserId(), ReactionType.DISLIKE);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{postId}/counts")
    public ResponseEntity<Map<String, Long>> getCounts(@PathVariable Long postId) {
        long likes = reactionService.getLikeCount(postId);
        long dislikes = reactionService.getDislikeCount(postId);
        return ResponseEntity.ok(Map.of("likes", likes, "dislikes", dislikes));
    }
}
