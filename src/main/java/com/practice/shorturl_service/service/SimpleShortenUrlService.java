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
        
        // 2. 데이터베이스에 해당 URL이 있는지 검사
        // 3. 데이터베이스에 있다면 해당 URL에 대한 단축 URL을 가져와서 반환
        ShortenUrl existingShortenUrl = shortenUrlRepository.findByOriginalUrl(originalUrl)
                .orElse(null);
        
        if (existingShortenUrl != null) {
            return new ShortenUrlCreateResponseDto(existingShortenUrl);
        }
        
        // 4. 데이터베이스에 없는 경우 새로운 레코드 생성 (ID는 자동 생성됨)
        ShortenUrl shortenUrl = ShortenUrl.builder()
                .originalUrl(originalUrl)
                .shortenUrlKey("") // 임시값, ID 생성 후 업데이트
                .build();
        
        ShortenUrl savedShortenUrl = shortenUrlRepository.save(shortenUrl);
        
        // 5. 62진법 변환을 적용하여 ID를 단축 URL로 변환
        String shortenUrlKey = ShortenUrl.generateShortenUrlKey(savedShortenUrl.getId());
        
        // 6. 단축 URL 키를 업데이트
        savedShortenUrl = ShortenUrl.builder()
                .id(savedShortenUrl.getId())
                .originalUrl(savedShortenUrl.getOriginalUrl())
                .shortenUrlKey(shortenUrlKey)
                .redirectCount(savedShortenUrl.getRedirectCount())
                .build();
        
        savedShortenUrl = shortenUrlRepository.save(savedShortenUrl);

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
