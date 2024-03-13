package by.haardd.cclog.exception.types;

public class TokenNotFoundException extends RuntimeException {

    private final static String code = "TOKEN_NOT_FOUND";

    public TokenNotFoundException(String message) {
        super(message);
    }

}
