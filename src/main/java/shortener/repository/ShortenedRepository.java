package shortener.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import shortener.model.Shortened;

public interface ShortenedRepository extends MongoRepository<Shortened, String> {
    public Shortened findByAlias(String alias);
}
