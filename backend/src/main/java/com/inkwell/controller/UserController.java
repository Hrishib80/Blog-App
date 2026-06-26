package com.inkwell.controller;

import com.inkwell.dto.UserResponse;
import com.inkwell.entity.User;
import com.inkwell.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getProfile(@PathVariable String username,
                                                   @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(userService.getProfile(username, currentUser));
    }

    @PostMapping("/{username}/follow")
    public ResponseEntity<UserResponse> toggleFollow(@PathVariable String username,
                                                     @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(userService.toggleFollow(username, currentUser));
    }
}
