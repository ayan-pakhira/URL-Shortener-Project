package com.example.URLShortener.URL.Shortener.Repository;

import com.example.URLShortener.URL.Shortener.Entity.ShortUrl;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends MongoRepository<ShortUrl, ObjectId> {

    ShortUrl findByShortCode(String shortCode);
    boolean existsByShortCode(String shortCode);
}
