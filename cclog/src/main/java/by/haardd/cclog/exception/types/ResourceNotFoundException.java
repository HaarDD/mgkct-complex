package by.haardd.cclog.exception.types;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    private final Long id;

    public ResourceNotFoundException(String message, Long id) {
        super(message);
        this.id = id;
    }
}
