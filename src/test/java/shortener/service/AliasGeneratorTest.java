package shortener.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shortener.exception.AliasInUseException;
import shortener.exception.InvalidUrlException;
import shortener.model.Shortened;
import shortener.repository.ShortenedRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class AliasGeneratorTest {
    @Autowired
    private ShortenedRepository shortenedRepository;
    @Autowired
    private AliasGenerator aliasGenerator;

    @Test
    public void shouldReturnValidAlias() {
        assertThat(aliasGenerator.retrieveAlias("somealias")).isEqualTo("somealias");
    }

    @Test
    public void shouldGenerateNewAliasForEmpty() {
        String alias = aliasGenerator.retrieveAlias("");

        assertThat(alias).isNotEqualTo("");
        assertThat(alias.length()).isEqualTo(4);
    }

    @Test
    public void shouldGenerateNewAliasForBlank() {
        String alias = aliasGenerator.retrieveAlias("     ");

        assertThat(alias).isNotEqualTo("     ");
        assertThat(alias.length()).isEqualTo(4);
    }

    @Test
    public void shouldThrowExceptionForAliasInUse() {
        shortenedRepository.save(new Shortened("htt://google.com", "analias"));

        try {
            aliasGenerator.retrieveAlias("analias");
        } catch (AliasInUseException e) {
            assertThat(e.getMessage()).isEqualTo("The provided alias \"analias\" is already in use.");
            return;
        }

        assertThat(true).isEqualTo(false);
    }
}
