package com.inkwell.service;

import com.inkwell.dto.PagedResponse;
import com.inkwell.dto.PostRequest;
import com.inkwell.dto.PostResponse;
import com.inkwell.entity.Post;
import com.inkwell.entity.Tag;
import com.inkwell.entity.User;
import com.inkwell.exception.BadRequestException;
import com.inkwell.exception.ResourceNotFoundException;
import com.inkwell.exception.UnauthorizedException;
import com.inkwell.repository.PostRepository;
import com.inkwell.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    public PagedResponse<PostResponse> getPublishedPosts(int page, int size, User currentUser) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postPage = postRepository.findByPublishedTrueOrderByCreatedAtDesc(pageable);
        return toPagedResponse(postPage, currentUser);
    }

    public PagedResponse<PostResponse> getFeed(int page, int size, User currentUser) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postPage = postRepository.findByPublishedTrueOrderByCreatedAtDesc(pageable);
        return toPagedResponse(postPage, currentUser);
    }

    public PagedResponse<PostResponse> searchPosts(String query, int page, int size, User currentUser) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postPage = postRepository.searchByTitleOrContent(query, pageable);
        return toPagedResponse(postPage, currentUser);
    }

    public PagedResponse<PostResponse> getPostsByAuthor(String username, int page, int size, User currentUser) {
        User author = currentUser;
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postPage = postRepository.findByAuthor(author, pageable);
        return toPagedResponse(postPage, currentUser);
    }

    public PostResponse getByIdentifier(String identifier, User currentUser) {
        Post post;
        try {
            Long id = Long.parseLong(identifier);
            post = postRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        } catch (NumberFormatException ex) {
            post = postRepository.findBySlug(identifier)
                    .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        }

        if (!post.isPublished() && (currentUser == null || !post.getAuthor().getId().equals(currentUser.getId()))) {
            throw new UnauthorizedException("Post is not published");
        }
        return toResponse(post, currentUser);
    }

    @Transactional
    public PostResponse createPost(PostRequest request, User currentUser) {
        if (currentUser == null) {
            throw new UnauthorizedException("You must be logged in");
        }
        Post post = Post.builder()
                .title(request.getTitle())
                .subtitle(request.getSubtitle())
                .content(request.getContent())
                .coverImage(request.getCoverImage())
                .published(request.isPublished())
                .author(currentUser)
                .tags(resolveTags(request.getTagNames()))
                .build();
        post = postRepository.save(post);
        return toResponse(post, currentUser);
    }

    @Transactional
    public PostResponse updatePost(Long id, PostRequest request, User currentUser) {
        if (currentUser == null) {
            throw new UnauthorizedException("You must be logged in");
        }
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        if (!post.getAuthor().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You can only edit your own posts");
        }
        post.setTitle(request.getTitle());
        post.setSubtitle(request.getSubtitle());
        post.setContent(request.getContent());
        post.setCoverImage(request.getCoverImage());
        post.setPublished(request.isPublished());
        post.setTags(resolveTags(request.getTagNames()));
        post = postRepository.save(post);
        return toResponse(post, currentUser);
    }

    @Transactional
    public void deletePost(Long id, User currentUser) {
        if (currentUser == null) {
            throw new UnauthorizedException("You must be logged in");
        }
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        if (!post.getAuthor().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You can only delete your own posts");
        }
        postRepository.delete(post);
    }

    @Transactional
    public PostResponse toggleLike(Long id, User currentUser) {
        if (currentUser == null) {
            throw new UnauthorizedException("You must be logged in");
        }
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        if (post.getLikedBy().stream().anyMatch(user -> user.getId().equals(currentUser.getId()))) {
            post.getLikedBy().removeIf(user -> user.getId().equals(currentUser.getId()));
        } else {
            post.getLikedBy().add(currentUser);
        }
        post = postRepository.save(post);
        return toResponse(post, currentUser);
    }

    private Set<Tag> resolveTags(List<String> tagNames) {
        if (tagNames == null || tagNames.isEmpty()) {
            return new HashSet<>();
        }
        return tagNames.stream()
                .map(String::trim)
                .filter(name -> !name.isBlank())
                .map(name -> tagRepository.findByName(name)
                        .orElseGet(() -> tagRepository.save(Tag.builder().name(name).build())))
                .collect(Collectors.toCollection(HashSet::new));
    }

    private PagedResponse<PostResponse> toPagedResponse(Page<Post> page, User currentUser) {
        List<PostResponse> content = page.getContent().stream()
                .map(post -> toResponse(post, currentUser))
                .toList();
        return PagedResponse.<PostResponse>builder()
                .content(content)
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }

    private PostResponse toResponse(Post post, User currentUser) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .subtitle(post.getSubtitle())
                .slug(post.getSlug())
                .content(post.getContent())
                .coverImage(post.getCoverImage())
                .published(post.isPublished())
                .readTime(post.getReadTime())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .authorId(post.getAuthor() != null ? post.getAuthor().getId() : null)
                .authorName(post.getAuthor() != null ? post.getAuthor().getDisplayName() : null)
                .authorAvatar(post.getAuthor() != null ? post.getAuthor().getAvatarUrl() : null)
                .tags(post.getTags().stream().map(Tag::getName).toList())
                .likeCount(post.getLikedBy().size())
                .commentCount(post.getComments().size())
                .likedByCurrentUser(currentUser != null && post.getLikedBy().stream().anyMatch(user -> user.getId().equals(currentUser.getId())))
                .build();
    }
}
