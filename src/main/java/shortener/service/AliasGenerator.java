package shortener.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shortener.exception.AliasInUseException;
import shortener.repository.ShortenedRepository;

@Service
public class AliasGenerator {
    @Autowired
    private ShortenedRepository shortenedRepository;
    private static final int ALIAS_LENGTH = 4;

    public String retrieveAlias(String alias) {
        if (alias != null && !StringUtils.isBlank(alias)) {
            if (shortenedRepository.findByAlias(alias) == null) {
                return alias;
            } else {
                throw new AliasInUseException(alias);
            }
        } else {
            return generateAlias();
        }
    }

    private String generateAlias() {
        String alias = "";
        while (alias.isBlank()) {
            String generatedAlias = RandomStringUtils.randomAlphanumeric(ALIAS_LENGTH);
            if (shortenedRepository.findByAlias(generatedAlias) == null) {
                alias = generatedAlias;
            }
        }
        return alias;
    }
}
