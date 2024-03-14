package by.haardd.cclog.exception.types;

public class NotFoundException extends CclogException {

    private final String targetValue;

    public NotFoundException(String message, String targetValue) {
        super(message, ErrorCode.RESOURCE_NOT_FOUND);
        this.targetValue = targetValue;
    }
}
