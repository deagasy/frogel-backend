package tinywins_backend;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException() {
        super("email_already_registered");
    }
}
