package org.example.Entities;

import org.example.Exceptions.InvalidTicketException;
import org.example.Exceptions.NoParkingLotAssigned;
import org.example.Exceptions.ParkingLotAlreadyAssigned;
import org.example.Exceptions.ParkingLotFullException;

import java.util.ArrayList;

public interface Attendant {
    void assign(ParkingLot parkingLot);
    void checkIfCarIsParked(Car car);
    Ticket park(Car car);
    Car unpark(Ticket ticket);

    default void assignLot(ParkingLot parkingLot, ArrayList<ParkingLot> parkingLots) {
        if (parkingLots.contains(parkingLot)) {
            throw new ParkingLotAlreadyAssigned("Parking lot is already assigned");
        }
        parkingLots.add(parkingLot);
    }

    default void checkIfCarIsAlreadyParked(Car car, ArrayList<ParkingLot> parkingLots) {
        for (ParkingLot parkingLot : parkingLots) {
            parkingLot.checkIfCarIsParked(car);
        }
    }

    default Ticket parkCar(Car car, ArrayList<ParkingLot> parkingLots) {
        if (parkingLots.isEmpty()) {
            throw new NoParkingLotAssigned("No parking lot is assigned");
        }
        checkIfCarIsAlreadyParked(car, parkingLots);

        for (ParkingLot parkingLot : parkingLots) {
            if (!parkingLot.isFull()) {
                return parkingLot.park(car);
            }
        }
        throw new ParkingLotFullException("All parking lots are full");
    }

    default Car unparkCar(Ticket ticket, ArrayList<ParkingLot> parkingLots) {
        for (ParkingLot parkingLot : parkingLots) {
            try {
                Car car = parkingLot.unpark(ticket);
                return car;
            } catch (InvalidTicketException ignored) {
            }
        }
        throw new InvalidTicketException("Ticket is invalid");
    }
}
