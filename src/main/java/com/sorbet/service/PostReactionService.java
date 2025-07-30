package com.sorbet.service;

import com.sorbet.domain.ReactionType;
import com.sorbet.entity.Post;
import com.sorbet.entity.PostReaction;
import com.sorbet.entity.User;
import com.sorbet.repository.PostReactionRepository;
import com.sorbet.repository.PostRepository;
import com.sorbet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostReactionService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostReactionRepository reactionRepository;

    public void toggleReaction(Long postId, Long userId, ReactionType reactionType) {
        Post post = postRepository.findById(postId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        Optional<PostReaction> existing = reactionRepository.findByPostAndUser(post, user);

        if (existing.isPresent()) {
            PostReaction reaction = existing.get();
            if (reaction.getReactionType() == reactionType) {
                // 같은 반응 -> 취소
                reactionRepository.delete(reaction);
            } else {
                // 다른 반응 -> 수정
                reaction.setReactionType(reactionType);
                reactionRepository.save(reaction);
            }
        } else {
            // 처음 반응
            PostReaction newReaction = new PostReaction();
            newReaction.setPost(post);
            newReaction.setUser(user);
            newReaction.setReactionType(reactionType);
            reactionRepository.save(newReaction);
        }
    }

    public long getLikeCount(Long postId) {
        return reactionRepository.countByPostAndReactionType(
                postRepository.findById(postId).orElseThrow(), ReactionType.LIKE);
    }

    public long getDislikeCount(Long postId) {
        return reactionRepository.countByPostAndReactionType(
                postRepository.findById(postId).orElseThrow(), ReactionType.DISLIKE);
    }
}
