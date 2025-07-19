package com.practice.shorturl_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

@Getter
@Schema(description = "단축 URL 생성 요청 DTO")
public class ShortenUrlCreateRequestDto {

    @Schema(description = "원본 URL", example = "https://www.google.com", required = true)
    @Pattern(regexp = "^(https?://)(www\\.)?[a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}([-a-zA-Z0-9@:%_\\+.~#?&//=]*)$")
    @NotNull
    private String originalUrl;

}