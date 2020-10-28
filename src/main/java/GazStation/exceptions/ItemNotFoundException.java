package GazStation.exceptions;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(String fieldName) {
        super(fieldName);
    }
}
