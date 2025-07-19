package com.practice.shorturl_service.repository;

import com.practice.shorturl_service.domain.ShortenUrl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Qualifier("jpaRepository")
public interface ShortenUrlRepository extends JpaRepository<ShortenUrl, Long>, ShortenUrlRepositoryInterface {
    Optional<ShortenUrl> findByShortenUrlKey(String shortenUrlKey);
}
