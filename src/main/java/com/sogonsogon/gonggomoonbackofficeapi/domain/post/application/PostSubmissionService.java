package com.sogonsogon.gonggomoonbackofficeapi.domain.post.application;

import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.ApproveSummitRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.RejectSummitRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.SubmitPostRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.Post;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.PostSubmission;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.SubmissionPlatform;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.SubmissionStatus;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.infrastructure.PostRepository;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.infrastructure.PostSubmissionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostSubmissionService {

    private final PostSubmissionRepository postSubmissionRepository;
    private final PostRepository postRepository;

    public PostSubmissionService(PostSubmissionRepository postSubmissionRepository, PostRepository postRepository) {
        this.postSubmissionRepository = postSubmissionRepository;
        this.postRepository = postRepository;
    }

    public void submitPost(SubmitPostRequest request, Long approvedBy) {

    }

    // 플랫폼 DB로 관리해야 할듯?
    @Transactional(readOnly = true)
    public Page<PostSubmission> getSubmissions(SubmissionStatus status, SubmissionPlatform platform, Pageable pageable) {

        if (status != null) {
            return postSubmissionRepository.findByStatus(status, pageable);
        } else if (platform != null) {
            return postSubmissionRepository.findByPlatform(platform, pageable);
        }

        return postSubmissionRepository.findAll(pageable);
    }

    @Transactional
    public void approveSubmission(Long id, ApproveSummitRequest request, Long approvedBy) {

        Post newPost;

        if (id != null) {
            PostSubmission submission = postSubmissionRepository.findById(id).orElseThrow(IllegalArgumentException::new);

            submission.approveSummit(SubmissionStatus.APPROVED, approvedBy);

            newPost = Post.create(submission.getId(), request.companyId(), request.title(), request.experienceLevel(), request.jobType(), request.deadline());
        } else {

            newPost = Post.create(
                    null,
                    request.companyId(),
                    request.title(),
                    request.experienceLevel(),
                    request.jobType(),
                    request.deadline()
            );
        }

        postRepository.save(newPost);
    }

    @Transactional
    public void rejectSubmission(Long id, RejectSummitRequest request, Long rejectBy) {

        PostSubmission submission = postSubmissionRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        submission.rejectSummit(SubmissionStatus.REJECTED, request.rejectionReason(), rejectBy);
    }
}
