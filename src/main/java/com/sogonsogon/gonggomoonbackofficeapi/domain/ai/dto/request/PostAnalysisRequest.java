package com.sogonsogon.gonggomoonbackofficeapi.domain.ai.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PostAnalysisRequest (
    @JsonProperty("user_id")
    Long userId,

    @JsonProperty("post_id")
    Long postId
){
}
