package by.haardd.cclog.exception.types.extended;

import by.haardd.cclog.exception.types.CclogException;
import by.haardd.cclog.exception.types.ErrorCode;

public class WrongRegistrationCodeException extends CclogException {

    public WrongRegistrationCodeException(String message) {
        super(message, ErrorCode.AUTH_SIGNUP_CODE_NOT_FOUND);
    }

}
