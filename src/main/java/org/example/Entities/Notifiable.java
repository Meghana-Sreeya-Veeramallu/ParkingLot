package org.example.Entities;

public interface Notifiable {
    void notifyFull(String parkingLotId);
    void notifyAvailable(String parkingLotId);
}
