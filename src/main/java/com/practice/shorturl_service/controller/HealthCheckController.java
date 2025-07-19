package com.practice.shorturl_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
@Tag(name = "헬스 체크", description = "서비스 상태 확인 API")
public class HealthCheckController {
    
    @Operation(summary = "서비스 상태 확인", description = "서비스가 정상적으로 동작하는지 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "서비스 정상 동작"),
            @ApiResponse(responseCode = "500", description = "서비스 오류")
    })
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("OK");
    }
} 