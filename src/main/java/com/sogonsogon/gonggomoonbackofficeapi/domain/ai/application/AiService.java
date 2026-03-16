package com.sogonsogon.gonggomoonbackofficeapi.domain.ai.application;

import com.sogonsogon.gonggomoonbackofficeapi.domain.ai.dto.request.PostAnalysisRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.ai.infrastructure.AiServerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiService {

    private final AiServerClient aiServerClient;

    /*
    * 공고 분석 요청
    * - AI 서버로 공고 요청을 보내는 메서드
    * */
    public void requestAnalyzePost(Long userId, Long postId) {
        // DTO 생성
        PostAnalysisRequest request = new PostAnalysisRequest(userId, postId);

        // AI 서버에 공고 분석 요청 전송
        aiServerClient.requestAnalyzePost(request);
    }
}
