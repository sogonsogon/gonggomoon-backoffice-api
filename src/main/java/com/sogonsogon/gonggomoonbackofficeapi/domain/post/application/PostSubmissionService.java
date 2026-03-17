package com.sogonsogon.gonggomoonbackofficeapi.domain.post.application;

import com.sogonsogon.gonggomoonbackofficeapi.domain.ai.application.AiService;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.CreatePostRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.RejectSummitRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.SubmissionListResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.Post;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.PostRepository;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.PostSubmission;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.PostSubmissionRepository;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.PostSubmissionStatus;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.error.PostSubmissionErrorCode;
import com.sogonsogon.gonggomoonbackofficeapi.global.error.BaseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostSubmissionService {

    private final PostSubmissionRepository postSubmissionRepository;
    private final PostRepository postRepository;
    private final AiService aiService;

    public PostSubmissionService(PostSubmissionRepository postSubmissionRepository, PostRepository postRepository, AiService aiService) {
        this.postSubmissionRepository = postSubmissionRepository;
        this.postRepository = postRepository;
        this.aiService = aiService;
    }

    //TODO companyId, platformId로 해당 데이터 존재하는지 검증하는 로직 필요함
    @Transactional
    public void approveSubmission(Long submissionId, CreatePostRequest request, Long processedBy) {

        PostSubmission submission = postSubmissionRepository.findById(submissionId)
                .orElseThrow(() -> new BaseException(PostSubmissionErrorCode.POST_SUBMISSION_NOT_FOUND));

        submission.approveSummit(processedBy);

        Post newPost = Post.create(
                submissionId,
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

    @Transactional
    public void rejectSubmission(Long id, RejectSummitRequest request, Long rejectBy) {

        PostSubmission submission = postSubmissionRepository.findById(id)
                .orElseThrow(() -> new BaseException(PostSubmissionErrorCode.POST_SUBMISSION_NOT_FOUND));

        if (submission.getStatus() != PostSubmissionStatus.PENDING) {
            throw new BaseException(PostSubmissionErrorCode.POST_SUBMISSION_ALREADY_PROCESSED);
        }

        submission.rejectSummit(request.rejectionReason(), rejectBy);
    }

    // 플랫폼 DB로 관리해야 할듯?
    @Transactional(readOnly = true)
    public Page<SubmissionListResponse> getSubmissions(PostSubmissionStatus status, Pageable pageable) {

        return postSubmissionRepository.findByStatus(status, pageable);
    }

}
