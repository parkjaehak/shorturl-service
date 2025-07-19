package com.practice.shorturl_service.controller;

import com.practice.shorturl_service.dto.ShortenUrlCreateRequestDto;
import com.practice.shorturl_service.dto.ShortenUrlCreateResponseDto;
import com.practice.shorturl_service.dto.ShortenUrlInformationDto;
import com.practice.shorturl_service.service.SimpleShortenUrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@Tag(name = "단축 URL 관리", description = "단축 URL 생성, 리다이렉트, 정보 조회 API")
public class ShortenUrlController {

    private final SimpleShortenUrlService simpleShortenUrlService;

    public ShortenUrlController(SimpleShortenUrlService simpleShortenUrlService) {
        this.simpleShortenUrlService = simpleShortenUrlService;
    }

    @Operation(summary = "단축 URL 생성", description = "원본 URL을 받아서 단축 URL을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "단축 URL 생성 성공",
                    content = @Content(schema = @Schema(implementation = ShortenUrlCreateResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (유효하지 않은 URL 형식)"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @RequestMapping(value = "/shortenUrl", method = RequestMethod.POST)
    public ResponseEntity<ShortenUrlCreateResponseDto> createShortenUrl(
            @Parameter(description = "단축 URL 생성 요청 정보", required = true)
            @Valid @RequestBody ShortenUrlCreateRequestDto shortenUrlCreateRequestDto
    ) {
        ShortenUrlCreateResponseDto shortenUrlCreateResponseDto = simpleShortenUrlService.generateShortenUrl(shortenUrlCreateRequestDto);
        return ResponseEntity.ok(shortenUrlCreateResponseDto);
    }

    @Operation(summary = "단축 URL 리다이렉트", description = "단축 URL 키를 받아서 원본 URL로 리다이렉트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "301", description = "영구 리다이렉트 (원본 URL로 이동)"),
            @ApiResponse(responseCode = "404", description = "단축 URL을 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @RequestMapping(value = "{shortenUrlKey}", method = RequestMethod.GET)
    public ResponseEntity<?> redirectShortenUrl(
            @Parameter(description = "단축 URL 키", example = "abc123", required = true)
            @PathVariable String shortenUrlKey
    ) throws URISyntaxException {
        String originalUrl = simpleShortenUrlService.getOriginalUrlByShortenUrlKey(shortenUrlKey);

        URI redirectUri = new URI(originalUrl);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(redirectUri);

        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }

    @Operation(summary = "단축 URL 정보 조회", description = "단축 URL의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "단축 URL 정보 조회 성공",
                    content = @Content(schema = @Schema(implementation = ShortenUrlInformationDto.class))),
            @ApiResponse(responseCode = "404", description = "단축 URL을 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @RequestMapping(value = "/shortenUrl/{shortenUrlKey}", method = RequestMethod.GET)
    public ResponseEntity<ShortenUrlInformationDto> getShortenUrlInformation(
            @Parameter(description = "단축 URL 키", example = "abc123", required = true)
            @PathVariable String shortenUrlKey
    ) {
        ShortenUrlInformationDto shortenUrlInformationDto = simpleShortenUrlService.getShortenUrlInformationByShortenUrlKey(shortenUrlKey);
        return ResponseEntity.ok(shortenUrlInformationDto);
    }
}
