package com.example.URLShortener.URL.Shortener.Entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Document(collection = "urls")
public class ShortUrl {

    @Id
    private ObjectId id;

    @NonNull
    private String originalUrl;

    @Indexed(unique = true)
    private String shortCode;

    @Indexed(expireAfter = "604800")
    private LocalDateTime createdAt = LocalDateTime.now();

    private int clickCount = 0;

}
