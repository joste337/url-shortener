package shortener.model;

import org.springframework.data.annotation.Id;

public class Shortened {
    @Id
    public String id;
    private String url;
    private String alias;

    public Shortened(String url, String alias) {
        this.url = url;
        this.alias = alias;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getAlias() {
        return alias;
    }
}
