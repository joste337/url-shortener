package shortener.exception;

public class InvalidUrlException extends RuntimeException {
    private String message;

    public InvalidUrlException(String url) {
        this.message = "The provided URL \"" + url + "\" is invalid.";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
