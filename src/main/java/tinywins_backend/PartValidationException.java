package tinywins_backend;

public class PartValidationException extends RuntimeException {
    private final String errorCode;

    public PartValidationException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
