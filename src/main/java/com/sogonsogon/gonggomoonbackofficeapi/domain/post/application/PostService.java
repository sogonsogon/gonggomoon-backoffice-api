package com.sogonsogon.gonggomoonbackofficeapi.domain.post.application;

import com.sogonsogon.gonggomoonbackofficeapi.domain.ai.application.AiService;
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
    private final AiService aiService;

    public PostService(PostRepository postRepository, AiService aiService) {
        this.postRepository = postRepository;
        this.aiService = aiService;
    }

    //TODO: company, platform 존재 여부, 시작일이 마감일 이전인지 확인 하는 로직
    // 공고 등록
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

        Post savedPost = postRepository.save(newPost);

        // 공고 등록과 동시에 분석 요청
        // NOTE : 공고 분석을 요청하는 주체는 관리자이지만, AI 서버에서는 사용자 ID가 필요한 경우가 있을 수 있으므로, 현재는 0L로 고정하여 전달
        aiService.requestAnalyzePost(0L,savedPost.getId());

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
