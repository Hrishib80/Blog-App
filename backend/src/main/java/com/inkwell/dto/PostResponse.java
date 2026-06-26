package com.inkwell.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

    private Long id;
    private String title;
    private String subtitle;
    private String slug;
    private String content;
    private String coverImage;
    private boolean published;
    private int readTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long authorId;
    private String authorName;
    private String authorAvatar;
    private List<String> tags;
    private int likeCount;
    private int commentCount;
    private boolean likedByCurrentUser;
}
