package org.example.Entities;

public class Policeman implements Notifiable {

    public Policeman() {}

    public void notifyWhenFull(ParkingLot parkingLot) {
        System.out.println("Notifying to policeman: Parking lot " + parkingLot + " is full");
    }

    public void notifyWhenAvailable(ParkingLot parkingLot) {
        System.out.println("Notifying to policeman: Parking lot " + parkingLot + " is available");
    }
}
