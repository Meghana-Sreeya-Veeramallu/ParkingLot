package org.example.Entities;

import org.example.Exceptions.CannotAssignAttendantException;
import org.example.Exceptions.ParkingLotAlreadyAssigned;

public class Owner extends Attendant implements Notifiable{

    public Owner() {
        super();
    }

    public ParkingLot createParkingLot(int capacity){
        ParkingLot parkingLot = new ParkingLot(capacity, this);
        return parkingLot;
    }

    public void assign(Attendant attendant, ParkingLot parkingLot) {
        if (parkingLot.owner != this) {
            throw new CannotAssignAttendantException("Only the owner of the parking lot can assign it.");
        }
        if (attendant.parkingLots.contains(parkingLot)) {
            throw new ParkingLotAlreadyAssigned("Parking lot is already assigned");
        }
        attendant.parkingLots.add(parkingLot);
    }

    public void registerNotifiable(ParkingLot parkingLot, Notifiable notifiable) {
        parkingLot.registerNotifiable(notifiable);
    }

    public void notifyFull(int parkingLotId) {
        System.out.println("Notifying to owner: Parking lot " + parkingLotId + " is full");
    }

    public void notifyAvailable(int parkingLotId) {
        System.out.println("Notifying to owner: Parking lot " + parkingLotId + " is available");
    }
}