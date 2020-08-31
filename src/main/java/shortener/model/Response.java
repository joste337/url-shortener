package shortener.model;

import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

public class Response {
    private String message;
    private HttpStatus status;
    private LocalDateTime timestamp;

    public Response(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
