package org.example.Entities;

public interface Notifiable {
    void notifyWhenFull(ParkingLot parkingLot);
    void notifyWhenAvailable(ParkingLot parkingLot);
}
