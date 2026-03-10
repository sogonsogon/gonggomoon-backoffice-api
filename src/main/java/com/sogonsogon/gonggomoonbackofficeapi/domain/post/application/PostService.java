package com.sogonsogon.gonggomoonbackofficeapi.domain.post.application;

import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.PostResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.PostsResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.JobType;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.PostStatus;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.infrastructure.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Page<PostsResponse> getPosts(JobType type, String name, Pageable pageable) {

        if (name != null) {
            return postRepository.searchByTitle(name, PostStatus.POSTED, pageable);
        } else if (type != null) {
            return postRepository.searchByJobType(type, PostStatus.POSTED, pageable);
        } else {
            return postRepository.searchByStatus(PostStatus.POSTED, pageable); // 관리자만
        }
    }

    public PostResponse getPost(Long id) {

        return postRepository.searchById(id, PostStatus.POSTED)
                .orElseThrow(IllegalArgumentException::new);
    }
}
