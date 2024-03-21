package by.haardd.cclog.exception;

import by.haardd.cclog.dto.ErrorResponseDto;
import by.haardd.cclog.exception.types.ErrorCode;
import by.haardd.cclog.exception.types.extended.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ResourceExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResponseDto handleNotFoundException(ResourceNotFoundException e) {
        String message = e.getMessage().replace("{}", e.getFindValue());
        return new ErrorResponseDto(ErrorCode.RESOURCE_NOT_FOUND, message);
    }

}
