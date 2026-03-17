package com.sogonsogon.gonggomoonbackofficeapi.domain.ai.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.sogonsogon.gonggomoonbackofficeapi.domain.ai.dto.request.BaseCallbackRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.Post;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.PostRepository;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.error.PostErrorCode;
import com.sogonsogon.gonggomoonbackofficeapi.global.error.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiCallbackService {

    private final PostRepository postRepository;


    public void updatePostAnalysisResults(BaseCallbackRequest request) {
        // 콜백 요청에서 필요한 데이터 추출
        Long postId = request.id();
        JsonNode resultNode = request.result();

        // 공고 분석 결과 업데이트
        Post foundPost = postRepository.findById(postId)
            .orElseThrow(() -> new BaseException(PostErrorCode.POST_NOT_FOUND));

        foundPost.updateAnalyzedResult(resultNode);
        postRepository.save(foundPost);
    }
}
