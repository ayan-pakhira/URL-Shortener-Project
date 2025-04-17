package com.example.URLShortener.URL.Shortener.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "urls")
@NoArgsConstructor
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
