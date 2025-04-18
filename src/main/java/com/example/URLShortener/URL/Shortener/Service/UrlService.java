package com.example.URLShortener.URL.Shortener.Service;

import com.example.URLShortener.URL.Shortener.Entity.ClickAnalytics;
import com.example.URLShortener.URL.Shortener.Entity.ShortUrl;
import com.example.URLShortener.URL.Shortener.Entity.User;
import com.example.URLShortener.URL.Shortener.Repository.ClickAnalyticsRepository;
import com.example.URLShortener.URL.Shortener.Repository.UrlRepository;
import com.example.URLShortener.URL.Shortener.Repository.UserRepository;
import com.example.URLShortener.URL.Shortener.Util.Base62Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.example.URLShortener.URL.Shortener.Util.Base62Encoder.generateShortCode;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private ClickAnalyticsRepository clickAnalyticsRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;



    //creating the shortUrl from an originalUrl
    public ShortUrl shortenUrl(String originalUrl){
        String shortCode = generateShortCode(); //generating the short code
        while(urlRepository.existsByShortCode(shortCode)){ // in this line we are checking that if any short code available by the same name.
            shortCode = generateShortCode(); //then regenerating the code until we get the unique one.
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userRepository.findByUserName(userName);
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setOriginalUrl(originalUrl);
        shortUrl.setShortCode(shortCode);
        shortUrl.setUserName(userName);
        urlRepository.save(shortUrl);
        user.getAllUrls().add(shortUrl);
        userRepository.save(user);

        return shortUrl;

    }


    //redirect the user to original url
    public String getOriginalUrl(String shortCode, String ipAddress, String userAgent){
        ShortUrl url = urlRepository.findByShortCode(shortCode);

        if(url == null){
            throw new RuntimeException("url not found");
        }

        ClickAnalytics analytics = new ClickAnalytics();
        analytics.setShortCode(shortCode);
        analytics.setIpAddress(ipAddress);
        analytics.setUserAgent(userAgent);
        clickAnalyticsRepository.save(analytics);

        return url.getOriginalUrl();
    }


    //to get the click count on url.
    public ShortUrl getClickCount(String shortCode){
        return urlRepository.countByShortCode(shortCode);
    }

    public List<ShortUrl> getAllUrlByUser(String userName){
        return urlRepository.findByUserName(userName);
    }




}
