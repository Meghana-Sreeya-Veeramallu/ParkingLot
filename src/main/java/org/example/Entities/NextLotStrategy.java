package org.example.Entities;

import java.util.ArrayList;

public abstract class NextLotStrategy {
    abstract ParkingLot getNextLot(ArrayList<ParkingLot> parkingLots);
}
