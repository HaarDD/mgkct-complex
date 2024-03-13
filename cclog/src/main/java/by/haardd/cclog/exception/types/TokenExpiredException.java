package by.haardd.cclog.exception.types;

public class TokenExpiredException extends RuntimeException {

    private final static String code = "TOKEN_EXPIRED";

    public TokenExpiredException(String message) {
        super(message);
    }

}
