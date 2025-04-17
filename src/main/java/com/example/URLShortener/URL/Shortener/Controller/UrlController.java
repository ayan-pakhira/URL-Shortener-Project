package com.example.URLShortener.URL.Shortener.Controller;

import com.example.URLShortener.URL.Shortener.Entity.ShortUrl;
import com.example.URLShortener.URL.Shortener.Service.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/api/url")
public class UrlController {


    @Autowired
    private UrlService urlService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody String originalUrl){
        String shortCode = urlService.shortenUrl(originalUrl);

        return ResponseEntity.ok("http://localhost:8080/" + shortCode);
    }

//    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/shorten-url/{shortCode}")
    public ResponseEntity<?> redirectOriginalUrl(@PathVariable String shortCode, HttpServletRequest request){
        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        String originalUrl = urlService.getOriginalUrl(shortCode, ip, userAgent);
        return ResponseEntity.status(302).location(URI.create(originalUrl)).build();
    }


    @GetMapping("/analytics/{shortCode}")
    public ResponseEntity<?> getClickCount(@PathVariable String shortCode){
        ShortUrl count = urlService.getClickCount(shortCode);
        return ResponseEntity.ok(count);
    }

}
