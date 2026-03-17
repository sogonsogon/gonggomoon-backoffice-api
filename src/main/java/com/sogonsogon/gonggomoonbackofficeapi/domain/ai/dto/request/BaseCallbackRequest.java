package com.sogonsogon.gonggomoonbackofficeapi.domain.ai.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.sogonsogon.gonggomoonbackofficeapi.domain.ai.entity.AiJobStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;

public record BaseCallbackRequest(
    @NotBlank(message = "type은 필수입니다.")
    String type,

    @NotNull(message = "id는 필수입니다.")
    Long id,

    @JsonProperty("user_id")
    @NotNull(message = "userId는 필수입니다.")
    Long userId,

    @NotNull(message = "status는 필수입니다.")
    AiJobStatus status,

    @NotNull(message = "result는 필수입니다.")
    JsonNode result,

    String error, // error는 실패한 경우에만 포함되므로, 필수는 아닙니다.

    @JsonProperty("attempt_count")
    Integer attemptCount, // attemptCount는 재시도 횟수를 나타내며, 실패한 경우에만 포함될 수 있습니다.

    @JsonProperty("processed_at")
    @NotNull(message = "processedAt은 필수입니다.")
    Instant processedAt
) {
}
