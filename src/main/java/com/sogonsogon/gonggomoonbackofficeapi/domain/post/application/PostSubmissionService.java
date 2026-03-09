package com.sogonsogon.gonggomoonbackofficeapi.domain.post.application;

import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.SubmitPostRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.PostSubmission;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.infrastructure.PostSubmissionRepository;
import org.springframework.stereotype.Service;

@Service
public class PostSubmissionService {

    private final PostSubmissionRepository postSubmissionRepository;

    public PostSubmissionService(PostSubmissionRepository postSubmissionRepository) {
        this.postSubmissionRepository = postSubmissionRepository;
    }

    public void submitPost(SubmitPostRequest request, Long approvedBy) {

        PostSubmission postSubmission = PostSubmission.create(request.requestedBy(), request.requestUrl(), approvedBy);

        postSubmissionRepository.save(postSubmission);
    }
}
