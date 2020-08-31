package shortener.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shortener.exception.AliasInUseException;
import shortener.model.Shortened;
import shortener.repository.ShortenedRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class ShortenerControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ShortenedRepository shortenedRepository;

    @Test
    public void shouldGetIndex() {
        assertThat(restTemplate.getForEntity("/", String.class).getBody()).startsWith("<!DOCTYPE html>");
    }

    @Test
    public void shouldRedirectToIndexForNonexisting() {
        assertThat(restTemplate.getForEntity("/nonexisting", String.class).getBody()).startsWith("<!DOCTYPE html>");
    }

    @Test
    public void shouldRedirectToUrlForExisting() {
        shortenedRepository.save(new Shortened("http://github.com", "githubalias"));

        assertThat(restTemplate.getForEntity("/githubalias", String.class).getStatusCodeValue()).isEqualTo(301);
    }

    @Test
    public void shouldCreateAlias() {
        Shortened shortened = new Shortened("http://somewebsite.com", "newalias");
        HttpEntity<Shortened> request = new HttpEntity<>(shortened);
        restTemplate.postForObject("/", request, AliasInUseException.class);

        Shortened shortenedResult = shortenedRepository.findByAlias("newalias");

        assertThat(shortenedResult.getUrl()).isEqualTo("http://somewebsite.com");
    }

    @Test
    public void shouldNotCreateExistingAlias() {
        shortenedRepository.save(new Shortened("http://somewebsite.com", "newalias"));

        Shortened shortened = new Shortened("http://somewebsite.com", "newalias");
        HttpEntity<Shortened> request = new HttpEntity<>(shortened);

        assertThat(restTemplate.postForObject("/", request, AliasInUseException.class).getMessage())
                .isEqualTo("The provided alias \"newalias\" is already in use.");
    }
}
