package com.sorbet.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    private Long userId;
    private Long postId;
    private String nickname;
    private String content;
    private String levelLabel = "";
    private String levelColor = "";
    private String levelEmoji = "";
    private LocalDateTime createdAt;
}


