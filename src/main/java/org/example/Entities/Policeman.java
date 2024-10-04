package org.example.Entities;

public class Policeman implements Notifiable {

    public Policeman() {}

    public void notifyFull(int parkingLotId) {
        System.out.println("Notifying to policeman: Parking lot " + parkingLotId + " is full");
    }

    public void notifyAvailable(int parkingLotId) {
        System.out.println("Notifying to policeman: Parking lot " + parkingLotId + " is available");
    }
}
