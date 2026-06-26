package com.inkwell.dto;

import com.inkwell.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String username;
    private String displayName;
    private String bio;
    private String avatarUrl;
    private Role role;
    private LocalDateTime createdAt;
    private int postCount;
    private int followerCount;
    private int followingCount;
    private boolean followedByCurrentUser;
}
