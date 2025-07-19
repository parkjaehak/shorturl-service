package com.practice.shorturl_service.service;

import com.practice.shorturl_service.domain.ShortenUrl;
import com.practice.shorturl_service.dto.ShortenUrlCreateRequestDto;
import com.practice.shorturl_service.dto.ShortenUrlCreateResponseDto;
import com.practice.shorturl_service.dto.ShortenUrlInformationDto;
import com.practice.shorturl_service.repository.ShortenUrlRepositoryInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SimpleShortenUrlService {

    private final ShortenUrlRepositoryInterface shortenUrlRepository;

    SimpleShortenUrlService(@Qualifier("jpaRepository") ShortenUrlRepositoryInterface shortenUrlRepository) {
        this.shortenUrlRepository = shortenUrlRepository;
    }

    public ShortenUrlCreateResponseDto generateShortenUrl(
            ShortenUrlCreateRequestDto shortenUrlCreateRequestDto
    ) {
        String originalUrl = shortenUrlCreateRequestDto.getOriginalUrl();
        String shortenUrlKey = ShortenUrl.generateShortenUrlKey();

        ShortenUrl shortenUrl = ShortenUrl.builder()
                .originalUrl(originalUrl)
                .shortenUrlKey(shortenUrlKey)
                .build();
        
        ShortenUrl savedShortenUrl = shortenUrlRepository.save(shortenUrl);

        ShortenUrlCreateResponseDto shortenUrlCreateResponseDto = new ShortenUrlCreateResponseDto(savedShortenUrl);
        return shortenUrlCreateResponseDto;
    }

    public ShortenUrlInformationDto getShortenUrlInformationByShortenUrlKey(String shortenUrlKey) {
        ShortenUrl shortenUrl = shortenUrlRepository.findByShortenUrlKey(shortenUrlKey)
                .orElseThrow(() -> new RuntimeException("단축 URL을 찾을 수 없습니다: " + shortenUrlKey));

        ShortenUrlInformationDto shortenUrlInformationDto = new ShortenUrlInformationDto(shortenUrl);
        return shortenUrlInformationDto;
    }

    public String getOriginalUrlByShortenUrlKey(String shortenUrlKey) {
        ShortenUrl shortenUrl = shortenUrlRepository.findByShortenUrlKey(shortenUrlKey)
                .orElseThrow(() -> new RuntimeException("단축 URL을 찾을 수 없습니다: " + shortenUrlKey));

        shortenUrl.increaseRedirectCount();
        shortenUrlRepository.save(shortenUrl);

        String originalUrl = shortenUrl.getOriginalUrl();

        return originalUrl;
    }
}
