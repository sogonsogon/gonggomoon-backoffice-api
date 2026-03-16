package com.sogonsogon.gonggomoonbackofficeapi.domain.ai.presentation;

import com.sogonsogon.gonggomoonbackofficeapi.domain.ai.application.AiCallbackService;
import com.sogonsogon.gonggomoonbackofficeapi.domain.ai.dto.request.BaseCallbackRequest;
import com.sogonsogon.gonggomoonbackofficeapi.global.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/callbacks")
public class AiCallbackController {

    private final AiCallbackService aiCallbackService;

    @PostMapping("/post-analysis")
    public ResponseEntity<BaseResponse<String>> handlePostAnalysisCallback(
        @RequestBody @Valid BaseCallbackRequest request
    ) {
        aiCallbackService.updatePostAnalysisResults(request);

        return ResponseEntity.ok(BaseResponse.success("AI 공고 분석 콜백 처리 완료"));
    }
}
