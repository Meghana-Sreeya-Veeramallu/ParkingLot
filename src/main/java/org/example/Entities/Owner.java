package org.example.Entities;

import org.example.Exceptions.ParkingLotAlreadyAssigned;

public class Owner extends Attendant implements Notifiable{

    public Owner() {
        super();
    }

    public void assign(Attendant attendant, ParkingLot parkingLot) {
        if (attendant.parkingLots.contains(parkingLot)) {
            throw new ParkingLotAlreadyAssigned("Parking lot is already assigned");
        }
        attendant.parkingLots.add(parkingLot);
    }

    public void notifyWhenFull(ParkingLot parkingLot) {
        System.out.println("Notifying to owner: Parking lot " + parkingLot + " is full");
    }

    public void notifyWhenAvailable(ParkingLot parkingLot) {
        System.out.println("Notifying to owner: Parking lot " + parkingLot + " is available");
    }
}