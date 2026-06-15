package tinywins_backend;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("invalid_credentials");
    }
}
