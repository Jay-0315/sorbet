package com.sorbet.service;

import com.sorbet.domain.UserLevel;
import com.sorbet.dto.CommentDto;
import com.sorbet.entity.Comment;
import com.sorbet.entity.Post;
import com.sorbet.entity.User;
import com.sorbet.repository.CommentRepository;
import com.sorbet.repository.PostRepository;
import com.sorbet.repository.UserRepository;
import com.sorbet.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void saveComment(CommentDto dto, CustomUserDetails customUserDetails) {
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다"));

        // writer 필드에 들어갈 User 객체 조회
        /*User user = userRepository.findByUserId(dto.getWriter())
                .orElseThrow(() -> new IllegalArgumentException("작성자 정보가 없습니다"));*/
        User user = userRepository.findByUserLoginId(customUserDetails.getUsername()).orElse(null);

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .user(user)  // User 객체 직접 설정
                .createdAt(LocalDateTime.now())
                .post(post)
                .build();

        commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    public List<CommentDto> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream().map(comment -> {
            User user = comment.getUser();  // 이제 comment.getWriter()는 User 객체임
            user.updateLevelByPoint();
            UserLevel level = user.getLevel();

            CommentDto dto = new CommentDto();
            dto.setId(comment.getId());
            dto.setUserId(user.getId());
            dto.setPostId(postId);
            dto.setNickname(user.getNickname()); // 닉네임만 넣기
            dto.setContent(comment.getContent());
            dto.setCreatedAt(comment.getCreatedAt());
            dto.setContent(comment.getContent());
            dto.setLevelLabel(level.getLabel());
            dto.setLevelColor(level.getColorCode());
            dto.setLevelEmoji(level.getEmoji());

            return dto;
        }).toList();
    }
}
