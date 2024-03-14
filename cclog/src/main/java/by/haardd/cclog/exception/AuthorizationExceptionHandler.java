package by.haardd.cclog.exception;

import by.haardd.cclog.dto.ErrorResponseDto;
import by.haardd.cclog.exception.types.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AuthorizationExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ErrorResponseDto handleUnauthorizedException(AuthenticationException e) {
        return new ErrorResponseDto(ErrorCode.AUTH_NOT_UNAUTHORIZED , e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ErrorResponseDto handleNotAccessException(AccessDeniedException e){
        return new ErrorResponseDto(ErrorCode.AUTH_ACCESS_DENIED, "Access denied to this resource!");
    }

}
