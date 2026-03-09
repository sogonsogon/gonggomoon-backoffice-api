package com.sogonsogon.gonggomoonbackofficeapi.domain.post.application;

import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.ApproveSummitRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.RejectSummitRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.SubmitPostRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.Post;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.PostSubmission;
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

    @Transactional(readOnly = true)
    public Page<PostSubmission> getSubmissions(SubmissionStatus status, Pageable pageable) {

        if (status != null) {
            return postSubmissionRepository.findByStatus(status, pageable);
        }

        return postSubmissionRepository.findAll(pageable);
    }

    @Transactional
    public void approveSubmission(Long id, ApproveSummitRequest request, Long approvedBy) {

        PostSubmission submission = postSubmissionRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        submission.approveSummit(SubmissionStatus.APPROVED, approvedBy);

        Post newPost = Post.create(submission.getId(), request.companyId(), request.title(), request.experienceLevel(), request.jobType(), request.deadline());

        postRepository.save(newPost);
    }

    @Transactional
    public void rejectSubmission(Long id, RejectSummitRequest request, Long rejectBy) {

        PostSubmission submission = postSubmissionRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        submission.rejectSummit(SubmissionStatus.REJECTED, request.rejectionReason(), rejectBy);
    }
}
