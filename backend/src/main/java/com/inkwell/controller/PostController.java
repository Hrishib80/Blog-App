package com.inkwell.controller;

import com.inkwell.dto.PagedResponse;
import com.inkwell.dto.PostRequest;
import com.inkwell.dto.PostResponse;
import com.inkwell.entity.User;
import com.inkwell.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<PagedResponse<PostResponse>> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(postService.getPublishedPosts(page, size, currentUser));
    }

    @GetMapping("/feed")
    public ResponseEntity<PagedResponse<PostResponse>> getFeed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(postService.getFeed(page, size, currentUser));
    }

    @GetMapping("/search")
    public ResponseEntity<PagedResponse<PostResponse>> searchPosts(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(postService.searchPosts(query, page, size, currentUser));
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<PostResponse> getByIdentifier(@PathVariable String identifier,
                                                        @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(postService.getByIdentifier(identifier, currentUser));
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostRequest request,
                                                   @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(postService.createPost(request, currentUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long id,
                                                   @Valid @RequestBody PostRequest request,
                                                   @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(postService.updatePost(id, request, currentUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id,
                                            @AuthenticationPrincipal User currentUser) {
        postService.deletePost(id, currentUser);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<PostResponse> toggleLike(@PathVariable Long id,
                                                   @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(postService.toggleLike(id, currentUser));
    }
}
