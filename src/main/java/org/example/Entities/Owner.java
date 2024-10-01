package org.example.Entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Owner extends Attendant {
    private final Map<ParkingLot, ArrayList<Attendant>> parkingLotAssignments;

    public Owner() {
        super();
        this.parkingLotAssignments = new HashMap<>();
    }

    public void assign(Attendant attendant, ParkingLot parkingLot) {
        attendant.assign(parkingLot);
        parkingLotAssignments.computeIfAbsent(parkingLot, k -> new ArrayList<>()).add(attendant);
    }
}