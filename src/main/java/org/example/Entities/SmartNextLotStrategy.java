package org.example.Entities;

import org.example.Exceptions.ParkingLotFullException;

import java.util.ArrayList;

public class SmartNextLotStrategy extends NextLotStrategy {

    public ParkingLot getNextLot(ArrayList<ParkingLot> parkingLots) {
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
        return selectedLot;
    }
}
