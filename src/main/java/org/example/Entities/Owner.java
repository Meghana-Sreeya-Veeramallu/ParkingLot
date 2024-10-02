package org.example.Entities;

public class Owner extends BasicAttendant implements Notifiable{

    public Owner() {
        super();
    }

    public void assign(Attendant attendant, ParkingLot parkingLot) {
        attendant.assign(parkingLot);
        parkingLot.registerNotifiable(this);
    }

    public void notifyWhenFull(ParkingLot parkingLot) {
        System.out.println("Notifying to owner: Parking lot " + parkingLot + " is full");
    }

    public void notifyWhenAvailable(ParkingLot parkingLot) {
        System.out.println("Notifying to owner: Parking lot " + parkingLot + " is available");
    }
}