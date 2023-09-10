package main.entities;

import java.awt.*;

public interface Vehicles {
    String getVehicleId();

    void setVehicleId(String vehicleId);

    String getName();

    void setName(String name);

    double getCurrentFuel();

    void setCurrentFuel(double currentFuel);

    double getCarryingCapacity();

    void setCarryingCapacity(double carryingCapacity);

    double getFuelCapacity();

    void setFuelCapacity(double fuelCapacity);

    Port getCurrentPort();

    void setCurrentPort(Port currentPort);

    int getTotalContainers();

    void setTotalContainers(int totalContainers);

    VehicleType getVehicleType();

    void setVehicleType(VehicleType vehicleType);

    // Enum to represent vehicle types
    enum VehicleType {
        SHIP,
        BASIC_TRUCK, REEFER_TRUCK, TANKER_TRUCK, TRUCK
    }

    // Method to calculate required fuel for a trip
    double calculateRequiredFuel(double distance);

    // Method to load containers onto the vehicle
    void loadContainer(Container container);

    // Method to unload containers from the vehicle
    void unloadContainer(Container container);
}
