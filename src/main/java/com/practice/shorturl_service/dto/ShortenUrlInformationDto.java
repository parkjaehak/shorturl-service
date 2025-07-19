package com.practice.shorturl_service.dto;

import com.practice.shorturl_service.domain.ShortenUrl;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "단축 URL 정보 조회 응답 DTO")
public class ShortenUrlInformationDto {
    
    @Schema(description = "원본 URL", example = "https://www.google.com")
    private String originalUrl;
    
    @Schema(description = "단축 URL 키", example = "abc123")
    private String shortenUrlKey;
    
    @Schema(description = "리다이렉트 횟수", example = "42")
    private Long redirectCount;

    public ShortenUrlInformationDto(ShortenUrl shortenUrl) {
        this.originalUrl = shortenUrl.getOriginalUrl();
        this.shortenUrlKey = shortenUrl.getShortenUrlKey();
        this.redirectCount = shortenUrl.getRedirectCount();
    }
    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortenUrlKey() {
        return shortenUrlKey;
    }

    public Long getRedirectCount() {
        return redirectCount;
    }
}