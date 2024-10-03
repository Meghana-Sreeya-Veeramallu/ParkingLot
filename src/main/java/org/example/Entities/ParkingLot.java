package org.example.Entities;

import org.example.Enums.CarColor;
import org.example.Exceptions.CannotCreateAParkingLotException;
import org.example.Exceptions.CarNotFoundException;
import org.example.Exceptions.InvalidTicketException;
import org.example.Exceptions.ParkingLotFullException;

import java.util.ArrayList;
import java.util.UUID;

public class ParkingLot {/**/
    private final ArrayList<Slot> slots;
    private final ArrayList<Notifiable> notifiables = new ArrayList<>();
    protected final String parkingLotId;

    public ParkingLot(int capacity, Owner owner) {
        if (capacity <= 0) {
            throw new CannotCreateAParkingLotException("Capacity should be greater than 0");
        }
        if (owner == null) {
            throw new CannotCreateAParkingLotException("Owner cannot be null");
        }
        this.slots = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            slots.add(new Slot());
        }
        this.registerNotifiable(owner);
        this.parkingLotId = UUID.randomUUID().toString();
    }

    public boolean isFull(){
        try {
            getNearestSlot();
            return false;
        }
        catch (ParkingLotFullException e) {
            return true;
        }
    }

    public void checkIfCarIsParked(Car car) {
        for (Slot slot : slots) {
            slot.isCarParked(car);
        }
    }

    public void registerNotifiable(Notifiable notifiable) {
        if (!notifiables.contains(notifiable)) {
            notifiables.add(notifiable);
        }
    }

    private void notifyFull() {
        for (Notifiable notifiable : notifiables) {
            notifiable.notifyFull(this.parkingLotId);
        }
    }

    private void notifyAvailable() {
        for (Notifiable notifiable : notifiables) {
            notifiable.notifyAvailable(this.parkingLotId);
        }
    }

    public Ticket park(Car car) {
        if (isFull()) {
            throw new ParkingLotFullException("Parking lot is full");
        }
        checkIfCarIsParked(car);
        Slot slot = getNearestSlot();
        Ticket ticket = slot.park(car);
        if (isFull()) {
            notifyFull();
        }
        return ticket;
    }

    public Car unpark(Ticket ticket) {
        boolean wasFull = isFull();
        for (Slot slot : slots) {
            try {
                Car car = slot.unpark(ticket);
                if (wasFull) {
                    notifyAvailable();
                }
                return car;
            } catch (InvalidTicketException ignored) {
            }
        }
        throw new InvalidTicketException("Ticket is invalid");
    }

    private Slot getNearestSlot() {
        for (Slot slot : slots) {
            if (slot.isEmpty()) {
                return slot;
            }
        }
        throw new ParkingLotFullException("Parking lot is full");
    }

    public int countCarsByColor(CarColor color) {
        int count = 0;
        for (Slot slot : slots) {
            if (slot.isCarOfColor(color)) {
                count++;
            }
        }
        return count;
    }

    public int getCarSlotNumberByRegistrationNumber(String registrationNumber) {
        for (int i = 0; i < slots.size(); i++) {
            Slot slot = slots.get(i);
            if (slot.hasSameRegistrationNumber(registrationNumber)) {
                return i + 1;
            }
        }
        throw new CarNotFoundException("Car with given registration number is not found");
    }

    public int countAvailableSlots() {
        int count = 0;
        for (Slot slot : slots) {
            if (slot.isEmpty()) {
                count++;
            }
        }
        return count;
    }
}
