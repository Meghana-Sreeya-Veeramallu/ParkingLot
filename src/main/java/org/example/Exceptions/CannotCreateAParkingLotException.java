package org.example.Exceptions;

public class CannotCreateAParkingLotException extends RuntimeException {
    public CannotCreateAParkingLotException(String message) {
        super(message);
    }
}
