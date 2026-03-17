package com.sogonsogon.gonggomoonbackofficeapi.domain.ai.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.sogonsogon.gonggomoonbackofficeapi.domain.ai.dto.request.BaseCallbackRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.ai.entity.AiJobStatus;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.Post;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.PostRepository;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.PostStatus;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.error.PostErrorCode;
import com.sogonsogon.gonggomoonbackofficeapi.global.error.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiCallbackService {

    private final PostRepository postRepository;


    public void updatePostAnalysisResults(BaseCallbackRequest request) {

        // 업데이트 할 공고 조회
        Long postId = request.id();
        Post foundPost = postRepository.findById(postId)
            .orElseThrow(() -> new BaseException(PostErrorCode.POST_NOT_FOUND));

        // AI 분석 실패 결과 업데이트
        if (request.status() == AiJobStatus.FAILED) {
            foundPost.updateStatus(PostStatus.ANALYSIS_FAILED);
            postRepository.save(foundPost);
            return;
        }

        // 공고 분석 성공 결과 업데이트
        JsonNode resultNode = request.result();
        foundPost.updateAnalyzedResult(resultNode);
        postRepository.save(foundPost);
    }
}
