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

        int numParkingLots = parkingLots.size();

        for (int i = 0; i < numParkingLots; i++) {
            int currentLotIndex = (lastUsedLotIndex + i + 1) % numParkingLots;
            ParkingLot parkingLot = parkingLots.get(currentLotIndex);

            try {
                Ticket ticket = parkingLot.park(car);
                lastUsedLotIndex = currentLotIndex;
                return ticket;
            } catch (ParkingLotFullException ignored) {
            }
        }
        throw new ParkingLotFullException("All parking lots are full");
    }
}