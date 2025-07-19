package com.practice.shorturl_service.dto;

import com.practice.shorturl_service.domain.ShortenUrl;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "단축 URL 생성 응답 DTO")
public class ShortenUrlCreateResponseDto {
    
    @Schema(description = "원본 URL", example = "https://www.google.com")
    private String originalUrl;
    
    @Schema(description = "단축 URL 키", example = "abc123")
    private String shortenUrlKey;

    public ShortenUrlCreateResponseDto(ShortenUrl shortenUrl) {
        this.originalUrl = shortenUrl.getOriginalUrl();
        this.shortenUrlKey = shortenUrl.getShortenUrlKey();
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortenUrlKey() {
        return shortenUrlKey;
    }
}
