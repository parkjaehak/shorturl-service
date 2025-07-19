package com.practice.shorturl_service.repository;

import com.practice.shorturl_service.domain.ShortenUrl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Qualifier("mapRepository")
public class MapShortenUrlRepository implements ShortenUrlRepositoryInterface {
    private Map<String, ShortenUrl> shortenUrls = new ConcurrentHashMap<>();

    @Override
    public ShortenUrl save(ShortenUrl shortenUrl) {
        shortenUrls.put(shortenUrl.getShortenUrlKey(), shortenUrl);
        return shortenUrl;
    }

    @Override
    public Optional<ShortenUrl> findByShortenUrlKey(String shortenUrlKey) {
        ShortenUrl shortenUrl = shortenUrls.get(shortenUrlKey);
        return Optional.ofNullable(shortenUrl);
    }
}