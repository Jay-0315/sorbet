package com.sorbet.repository;

import com.sorbet.domain.ReactionType;
import com.sorbet.entity.Post;
import com.sorbet.entity.PostReaction;
import com.sorbet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostReactionRepository extends JpaRepository<PostReaction, Long> {
    Optional<PostReaction> findByPostAndUser(Post post, User user);
    Long countByPostAndReactionType(Post post, ReactionType reactionType);
}