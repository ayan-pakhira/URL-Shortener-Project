package com.example.URLShortener.URL.Shortener.Repository;

import com.example.URLShortener.URL.Shortener.Entity.ClickAnalytics;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClickAnalyticsRepository extends MongoRepository<ClickAnalytics, ObjectId> {
    //
}
