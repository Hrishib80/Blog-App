package com.inkwell.controller;

import com.inkwell.dto.CommentRequest;
import com.inkwell.dto.CommentResponse;
import com.inkwell.entity.User;
import com.inkwell.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentResponse>> listComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.listComments(postId));
    }

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@PathVariable Long postId,
                                                         @Valid @RequestBody CommentRequest request,
                                                         @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(commentService.createComment(postId, request, currentUser));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId,
                                               @AuthenticationPrincipal User currentUser) {
        commentService.deleteComment(commentId, currentUser);
        return ResponseEntity.noContent().build();
    }
}
