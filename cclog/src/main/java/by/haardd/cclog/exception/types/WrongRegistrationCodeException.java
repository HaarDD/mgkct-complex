package by.haardd.cclog.exception.types;

public class WrongRegistrationCodeException extends RuntimeException {

    private final static String code = "CODE_NOT_FOUND";

    public WrongRegistrationCodeException(String message) {
        super(message);
    }

}
