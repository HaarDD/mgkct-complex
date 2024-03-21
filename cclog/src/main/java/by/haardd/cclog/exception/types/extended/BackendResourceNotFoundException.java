package by.haardd.cclog.exception.types.extended;

import by.haardd.cclog.exception.types.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BackendResourceNotFoundException extends NotFoundException {

    public BackendResourceNotFoundException(String message, String targetValue) {
        super(message, targetValue);
        log.error("Backend value not found! Message: {}, TargetValue: {}", message, targetValue);
    }
}
