package org.example.Entities;

import org.example.Exceptions.ParkingLotFullException;

import java.util.ArrayList;

public class BasicNextLotStrategy extends NextLotStrategy {

    public ParkingLot getNextLot(ArrayList<ParkingLot> parkingLots) {
        ParkingLot selectedLot = null;

        for (ParkingLot parkingLot : parkingLots) {
            if (!parkingLot.isFull()) {
                selectedLot =  parkingLot;
                break;
            }
        }
        if (selectedLot == null) {
            throw new ParkingLotFullException("All parking lots are full");
        }
        return selectedLot;
    }
}
