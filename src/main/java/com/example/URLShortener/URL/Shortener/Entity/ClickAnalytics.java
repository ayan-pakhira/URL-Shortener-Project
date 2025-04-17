package com.example.URLShortener.URL.Shortener.Entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Document(collection = "clicks")
public class ClickAnalytics {

    @Id
    private ObjectId id;

    private String shortCode;

    private LocalDateTime clickedAt = LocalDateTime.now();
    private String ipAddress;
    private String userAgent;


}
