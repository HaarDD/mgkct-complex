package by.haardd.cclog.exception.types.extended;

import by.haardd.cclog.exception.types.CclogException;
import by.haardd.cclog.exception.types.ErrorCode;

public class UserAlreadyExistsException extends CclogException {

    public UserAlreadyExistsException(String message) {
        super(message, ErrorCode.AUTH_SIGNUP_USER_ALREADY_EXIST);
    }
}
