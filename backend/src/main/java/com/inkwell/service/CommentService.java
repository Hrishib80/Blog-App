package com.inkwell.service;

import com.inkwell.dto.CommentRequest;
import com.inkwell.dto.CommentResponse;
import com.inkwell.entity.Comment;
import com.inkwell.entity.Post;
import com.inkwell.entity.User;
import com.inkwell.exception.ResourceNotFoundException;
import com.inkwell.exception.UnauthorizedException;
import com.inkwell.repository.CommentRepository;
import com.inkwell.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public List<CommentResponse> listComments(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        return commentRepository.findByPostOrderByCreatedAtDesc(post).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public CommentResponse createComment(Long postId, CommentRequest request, User currentUser) {
        if (currentUser == null) {
            throw new UnauthorizedException("You must be logged in");
        }
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        Comment comment = Comment.builder()
                .content(request.getContent())
                .post(post)
                .author(currentUser)
                .build();

        comment = commentRepository.save(comment);
        return toResponse(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, User currentUser) {
        if (currentUser == null) {
            throw new UnauthorizedException("You must be logged in");
        }
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        if (!comment.getAuthor().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You can only delete your own comments");
        }
        commentRepository.delete(comment);
    }

    private CommentResponse toResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .authorId(comment.getAuthor() != null ? comment.getAuthor().getId() : null)
                .authorName(comment.getAuthor() != null ? comment.getAuthor().getDisplayName() : null)
                .authorAvatar(comment.getAuthor() != null ? comment.getAuthor().getAvatarUrl() : null)
                .build();
    }
}
