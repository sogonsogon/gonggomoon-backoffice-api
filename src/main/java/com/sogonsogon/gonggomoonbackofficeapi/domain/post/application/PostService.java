package com.sogonsogon.gonggomoonbackofficeapi.domain.post.application;

import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.CreatePostRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.PostResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.PostListResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.Post;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.PostRepository;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.PostStatus;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.error.PostErrorCode;
import com.sogonsogon.gonggomoonbackofficeapi.global.error.BaseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    //TODO: company, platform 존재 여부, 시작일이 마감일 이전인지 확인 하는 로직
    @Transactional
    public void createPost(CreatePostRequest request) {

        if (postRepository.existsByUrl(request.url())) {
            throw new BaseException(PostErrorCode.DUPLICATE_POST_URL);
        }

        Post newPost = Post.create(
                null,
                request.companyId(),
                request.platformId(),
                request.title(),
                request.url(),
                request.experienceLevel(),
                request.jobType(),
                request.originalContent(),
                request.startDate(),
                request.dueDate()
        );

        postRepository.save(newPost);
    }

    @Transactional(readOnly = true)
    public Page<PostListResponse> getPosts(String title, List<PostStatus> statuses, Pageable pageable) {

        if (statuses == null || statuses.isEmpty()) {
            statuses = List.of(PostStatus.PUBLISHED);
        }

        Instant now = Instant.now();

        return postRepository.searchPostsByTitleAndStatus(title, statuses, now, pageable);
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long postId) {

        if (!postRepository.existsById(postId)) throw new BaseException(PostErrorCode.POST_NOT_FOUND);

        return postRepository.searchPostById(postId)
                .orElseThrow(() -> new BaseException(PostErrorCode.POST_NOT_FOUND));
    }

    @Transactional
    public void publishPost(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(PostErrorCode.POST_NOT_FOUND));

        if (post.getStatus() != PostStatus.ANALYZED) throw new BaseException(PostErrorCode.POST_NOT_ANALYZED);

        post.publish();
    }

    @Transactional
    public void deletePost(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(PostErrorCode.POST_NOT_FOUND));

        postRepository.delete(post);
    }
}
