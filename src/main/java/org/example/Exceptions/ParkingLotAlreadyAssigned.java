package org.example.Exceptions;

public class ParkingLotAlreadyAssigned extends RuntimeException {
    public ParkingLotAlreadyAssigned(String message) {
        super(message);
    }
}
