package shortener.exception;

public class AliasInUseException extends RuntimeException {
    private String message;

    public AliasInUseException(String alias) {
        this.message = "The provided alias \"" + alias + "\" is already in use.";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
