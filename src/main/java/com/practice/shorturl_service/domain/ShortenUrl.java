package com.practice.shorturl_service.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class ShortenUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originalUrl;
    private String shortenUrlKey;
    private Long redirectCount;

    @Builder
    public ShortenUrl(String originalUrl, String shortenUrlKey) {
        this.originalUrl = originalUrl;
        this.shortenUrlKey = shortenUrlKey;
        this.redirectCount = 0L;
    }

    public static String generateShortenUrlKey(Long id) {
        String base62Characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder shortenUrlKey = new StringBuilder();
        
        if (id == 0) {
            return "0";
        }
        
        while (id > 0) {
            int remainder = (int) (id % 62);
            shortenUrlKey.insert(0, base62Characters.charAt(remainder));
            id = id / 62;
        }
        
        return shortenUrlKey.toString();
    }

    public void increaseRedirectCount() {
        this.redirectCount = this.redirectCount + 1;
    }
}
