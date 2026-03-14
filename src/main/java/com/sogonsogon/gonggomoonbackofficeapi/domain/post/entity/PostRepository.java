package com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity;

import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.PostListResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(Post post);

    Optional<Post> findById(Long postId);

    boolean existsByUrl(String url);

    boolean existsById(Long postId);

    Page<PostListResponse> searchPostsByTitleAndStatus(
            String title,
            List<PostStatus> statuses,
            Instant now,
            Pageable pageable);

    Optional<PostResponse> searchPostById(Long id);

    void delete(Post post);
}
