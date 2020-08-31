package shortener.service;

import org.springframework.stereotype.Service;
import shortener.exception.InvalidUrlException;

import java.net.URL;

@Service
public class UrlValidator {
    public void validateUrl(String url) {
        try {
            URL u = new URL(url);
            u.toURI();
        } catch (Exception e) {
            throw new InvalidUrlException(url);
        }
    }
}
