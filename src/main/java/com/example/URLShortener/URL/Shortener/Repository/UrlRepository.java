package com.example.URLShortener.URL.Shortener.Repository;

import com.example.URLShortener.URL.Shortener.Entity.ShortUrl;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlRepository extends MongoRepository<ShortUrl, ObjectId> {

    ShortUrl findByShortCode(String shortCode);
    boolean existsByShortCode(String shortCode);
    ShortUrl countByShortCode(String shortCode);
    List<ShortUrl> findByUserName(String userName);
}
