package by.haardd.cclog.exception.types.extended;

public class TokenInvalidException extends RuntimeException {

    private final static String code = "TOKEN_INVALID";

    public TokenInvalidException(String message) {
        super(message);
    }

}
