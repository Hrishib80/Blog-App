package com.inkwell.repository;

import com.inkwell.entity.Post;
import com.inkwell.entity.Tag;
import com.inkwell.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByPublishedTrueOrderByCreatedAtDesc(Pageable pageable);

    Optional<Post> findBySlug(String slug);

    Page<Post> findByAuthor(User author, Pageable pageable);

    Page<Post> findByTagsContaining(Tag tag, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.published = true AND (LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(p.content) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<Post> searchByTitleOrContent(@Param("query") String query, Pageable pageable);

    Page<Post> findByPublishedTrueAndLikedByContaining(User user, Pageable pageable);
}
