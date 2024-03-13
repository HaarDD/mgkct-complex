package by.haardd.cclog.exception.types;

public class TokenInvalidException extends RuntimeException {

    private final static String code = "TOKEN_INVALID";

    public TokenInvalidException(String message) {
        super(message);
    }

}
