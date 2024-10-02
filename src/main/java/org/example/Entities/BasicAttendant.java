package org.example.Entities;

import org.example.Exceptions.InvalidTicketException;
import org.example.Exceptions.NoParkingLotAssigned;
import org.example.Exceptions.ParkingLotAlreadyAssigned;
import org.example.Exceptions.ParkingLotFullException;

import java.util.ArrayList;

public class BasicAttendant implements Attendant {
    protected ArrayList<ParkingLot> parkingLots;

    public BasicAttendant(){
        this.parkingLots = new ArrayList<>();
    }

    public void assign(ParkingLot parkingLot){
        if(this.parkingLots.contains(parkingLot)){
            throw new ParkingLotAlreadyAssigned("Parking lot is already assigned");
        }
        this.parkingLots.add(parkingLot);
    }

    public void checkIfCarIsParked(Car car){
        for(ParkingLot parkingLot : parkingLots){
            parkingLot.checkIfCarIsParked(car);
        }
    }

    public Ticket park (Car car){
        if (parkingLots.isEmpty()){
            throw new NoParkingLotAssigned("No parking lot is assigned");
        }
        checkIfCarIsParked(car);
        for(ParkingLot parkingLot : parkingLots){
            if(!parkingLot.isFull()){
                return parkingLot.park(car);
            }
        }
        throw new ParkingLotFullException("All parking lots are full");
    }

    public Car unpark(Ticket ticket){
        for(ParkingLot parkingLot : parkingLots){
            try{
                Car car = parkingLot.unpark(ticket);
                return car;
            } catch (InvalidTicketException ignored){
            }
        }
        throw new InvalidTicketException("Ticket is invalid");
    }
}
