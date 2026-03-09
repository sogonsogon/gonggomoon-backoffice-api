package com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto;

public record SubmitPostRequest(
        String requestUrl,
        Long requestedBy
) {
}
