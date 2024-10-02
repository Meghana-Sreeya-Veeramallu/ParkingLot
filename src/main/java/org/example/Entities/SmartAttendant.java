package org.example.Entities;

import org.example.Exceptions.NoParkingLotAssigned;
import org.example.Exceptions.ParkingLotFullException;

import java.util.ArrayList;

public class SmartAttendant implements Attendant {

    protected ArrayList<ParkingLot> parkingLots;

    public SmartAttendant(){
        this.parkingLots = new ArrayList<>();
    }

    @Override
    public void assign(ParkingLot parkingLot) {
        assignLot(parkingLot, this.parkingLots);
    }

    @Override
    public void checkIfCarIsParked(Car car) {
        checkIfCarIsAlreadyParked(car, this.parkingLots);
    }

    public Ticket park(Car car) {
        if (parkingLots.isEmpty()) {
            throw new NoParkingLotAssigned("No parking lot is assigned");
        }
        checkIfCarIsParked(car);

        ParkingLot selectedLot = null;
        int maxCapacityLeft = -1;

        for (ParkingLot parkingLot : parkingLots) {
            int availableSlots = parkingLot.countAvailableSlots();
            if (availableSlots > maxCapacityLeft) {
                maxCapacityLeft = availableSlots;
                selectedLot = parkingLot;
            }
        }

        if (selectedLot == null || selectedLot.isFull()) {
            throw new ParkingLotFullException("All parking lots are full");
        }

        Ticket ticket = selectedLot.park(car);
        return ticket;
    }

    @Override
    public Car unpark(Ticket ticket) {
        return unparkCar(ticket, this.parkingLots);
    }
}