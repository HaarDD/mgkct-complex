package by.haardd.cclog.exception.types.extended;

import by.haardd.cclog.exception.types.CclogException;
import by.haardd.cclog.exception.types.ErrorCode;

public class TokenNotFoundException extends CclogException {

    public TokenNotFoundException(String message) {
        super(message, ErrorCode.TOKEN_NOT_FOUND);
    }

}
