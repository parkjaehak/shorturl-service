package com.practice.shorturl_service.repository;

import com.practice.shorturl_service.domain.ShortenUrl;

import java.util.Optional;

public interface ShortenUrlRepositoryInterface {
    ShortenUrl save(ShortenUrl shortenUrl);
    Optional<ShortenUrl> findByShortenUrlKey(String shortenUrlKey);
    Optional<ShortenUrl> findByOriginalUrl(String originalUrl);
} 