package by.haardd.cclog.exception.types;

public class CclogException extends RuntimeException{

    private final ErrorCode errorCode;

    public CclogException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
