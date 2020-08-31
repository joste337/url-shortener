package shortener.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shortener.exception.InvalidUrlException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class UrlValidatorTest {
    @Autowired
    private UrlValidator urlValidator;

    @Test
    public void shouldBeValidForCorretUrl() {
        urlValidator.validateUrl("http://google.com");
        urlValidator.validateUrl("http://www.google.com");

        assertThat(true).isEqualTo(true);
    }

    @Test
    public void shouldThrowExceptionForIncorrectProtocol() {
        try {
            urlValidator.validateUrl("htt://google.com");
        } catch (InvalidUrlException e) {
            assertThat(e.getMessage()).isEqualTo("The provided URL \"htt://google.com\" is invalid.");
            return;
        }

        assertThat(true).isEqualTo(false);
    }

    @Test
    public void shouldThrowExceptionForMissingProtocol() {
        try {
            urlValidator.validateUrl("google.com");
        } catch (InvalidUrlException e) {
            assertThat(e.getMessage()).isEqualTo("The provided URL \"google.com\" is invalid.");
            return;
        }

        assertThat(true).isEqualTo(false);
    }
}
