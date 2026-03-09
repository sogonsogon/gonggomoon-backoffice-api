package com.sogonsogon.gonggomoonbackofficeapi.domain.post.application;

import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.SubmitPostRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.PostSubmission;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.SubmissionStatus;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.infrastructure.PostSubmissionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostSubmissionService {

    private final PostSubmissionRepository postSubmissionRepository;

    public PostSubmissionService(PostSubmissionRepository postSubmissionRepository) {
        this.postSubmissionRepository = postSubmissionRepository;
    }

    public void submitPost(SubmitPostRequest request, Long approvedBy) {

    }

    public Page<PostSubmission> getSubmissions(SubmissionStatus status, Pageable pageable) {

        if (status != null) {
            return postSubmissionRepository.findByStatus(status, pageable);
        }

        return postSubmissionRepository.findAll(pageable);
    }

    public PostSubmission getSubmission(Long id) {

        return postSubmissionRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
