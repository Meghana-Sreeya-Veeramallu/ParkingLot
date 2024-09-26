package org.example.Exceptions;

public class NoParkingLotAssigned extends RuntimeException {
    public NoParkingLotAssigned(String message) {
        super(message);
    }
}
