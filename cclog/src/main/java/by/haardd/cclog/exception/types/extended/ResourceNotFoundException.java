package by.haardd.cclog.exception.types.extended;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    private final static String code = "RESOURCE_NOT_FOUND"; //TODO перейти на использовние этого

    private final String findValue;

    public ResourceNotFoundException(String message, String findValue) {
        super(message);
        this.findValue = findValue;
    }
}
