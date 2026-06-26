package com.inkwell.service;

import com.inkwell.dto.UserResponse;
import com.inkwell.entity.User;
import com.inkwell.exception.ResourceNotFoundException;
import com.inkwell.exception.UnauthorizedException;
import com.inkwell.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse getProfile(String username, User currentUser) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return toResponse(user, currentUser);
    }

    @Transactional
    public UserResponse toggleFollow(String username, User currentUser) {
        if (currentUser == null) {
            throw new UnauthorizedException("You must be logged in");
        }
        User target = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (target.getId().equals(currentUser.getId())) {
            return toResponse(target, currentUser);
        }

        if (currentUser.getFollowing().contains(target)) {
            currentUser.getFollowing().remove(target);
            target.getFollowers().remove(currentUser);
        } else {
            currentUser.getFollowing().add(target);
            target.getFollowers().add(currentUser);
        }

        userRepository.save(currentUser);
        userRepository.save(target);
        return toResponse(target, currentUser);
    }

    private UserResponse toResponse(User user, User currentUser) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .displayName(user.getDisplayName())
                .bio(user.getBio())
                .avatarUrl(user.getAvatarUrl())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .postCount(user.getPosts().size())
                .followerCount(user.getFollowers().size())
                .followingCount(user.getFollowing().size())
                .followedByCurrentUser(currentUser != null && user.getFollowers().stream().anyMatch(follower -> follower.getId().equals(currentUser.getId())))
                .build();
    }
}
