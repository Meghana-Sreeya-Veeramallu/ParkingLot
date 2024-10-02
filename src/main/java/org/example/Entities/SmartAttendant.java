package org.example.Entities;

import org.example.Exceptions.NoParkingLotAssigned;
import org.example.Exceptions.ParkingLotFullException;

public class SmartAttendant extends Attendant {
    private int lastUsedLotIndex;

    public SmartAttendant() {
        super();
        this.lastUsedLotIndex = -1;
    }

    @Override
    public Ticket park(Car car) {
        if (parkingLots.isEmpty()) {
            throw new NoParkingLotAssigned("No parking lot is assigned");
        }
        checkifCarIsParked(car);

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
}