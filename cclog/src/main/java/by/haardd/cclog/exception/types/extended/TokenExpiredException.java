package by.haardd.cclog.exception.types.extended;

public class TokenExpiredException extends RuntimeException {

    private final static String code = "TOKEN_EXPIRED";

    public TokenExpiredException(String message) {
        super(message);
    }

}
