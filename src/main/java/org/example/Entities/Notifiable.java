package org.example.Entities;

public interface Notifiable {
    void notifyFull(int parkingLotId);
    void notifyAvailable(int parkingLotId);
}
