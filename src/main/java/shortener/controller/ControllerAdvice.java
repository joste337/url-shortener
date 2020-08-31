package shortener.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shortener.exception.AliasInUseException;
import shortener.exception.InvalidUrlException;
import shortener.model.Response;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler({ InvalidUrlException.class })
    public ResponseEntity<Response> invalidUrlHandler(InvalidUrlException exception) {
        Response errorResponse = new Response(exception.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler({ AliasInUseException.class })
    public ResponseEntity<Response> aliasInUseHandler(AliasInUseException exception) {
        Response errorResponse = new Response(exception.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}
