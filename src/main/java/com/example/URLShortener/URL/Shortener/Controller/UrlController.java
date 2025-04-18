package com.example.URLShortener.URL.Shortener.Controller;

import com.example.URLShortener.URL.Shortener.Entity.ShortUrl;
import com.example.URLShortener.URL.Shortener.Entity.User;
import com.example.URLShortener.URL.Shortener.Repository.UserRepository;
import com.example.URLShortener.URL.Shortener.Service.UrlService;
import com.example.URLShortener.URL.Shortener.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/url")
public class UrlController {


    @Autowired
    private UrlService urlService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/shorten")
    public ResponseEntity<?> shortenUrl(@RequestBody String originalUrl){
        ShortUrl shortCode = urlService.shortenUrl(originalUrl);

        return ResponseEntity.ok("http://localhost:8080/" + shortCode); //here I need to see that how will this work with
        //actual live link
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
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


    //get all urls by the login users
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/allUrls")
    public ResponseEntity<?> getAllUrls(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userRepository.findByUserName(userName);

        if(user == null){
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        List<ShortUrl> all = user.getAllUrls();

        if(all != null){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
