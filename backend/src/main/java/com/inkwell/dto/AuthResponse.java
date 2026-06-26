package com.inkwell.dto;

import com.inkwell.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private Long id;
    private String username;
    private String displayName;
    private String avatarUrl;
    private Role role;
}
