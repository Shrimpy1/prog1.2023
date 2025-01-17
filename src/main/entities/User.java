package main.entities;

import java.time.ZoneId;
import java.util.*;
import java.time.LocalDate;

import main.entities.Containers.ContainerType;

public abstract class User {
    private String username;
    private String password;
    private String userRole = null;
    private List<Vehicles> vehiclesUnderControl;
    private List<Ship> shipsUnderControl;
    private List<Trip> trips;
    private List<Trip> tripsUnderControl;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.vehiclesUnderControl = new ArrayList<>();
        this.shipsUnderControl = new ArrayList<>();
        this.trips = new ArrayList<>();
        this.tripsUnderControl = new ArrayList<>();
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getRole(){
        return this.userRole;
    }

    public List<Vehicles> getVehiclesUnderControl() {
        return vehiclesUnderControl;
    }

    public List<Ship> getShipsUnderControl() {
        return shipsUnderControl;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public List<Trip> getTripsUnderControl() {
        return tripsUnderControl;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public abstract boolean hasPermission(Operation operation);

    public abstract double calculateDailyFuelConsumption();

    public abstract List<Trip> listTripsInDay(Date date);

    public List<Ship> listAllShipsInPort(Port port) {
        List<Ship> shipsInPort = new ArrayList<>();

        for (Ship ship : this.getShipsUnderControl()) {
            if (ship.getCurrentPort() != null && ship.getCurrentPort().equals(port)) {
                shipsInPort.add(ship);
            }
        }

        return shipsInPort;
    }

    public List<Trip> listTripsBetweenDays(LocalDate dayA, LocalDate dayB) {
        List<Trip> tripsBetweenDays = new ArrayList<>();

        for (Trip trip : this.getTripsUnderControl()) {
            Date departureDate = trip.getDepartureDate();
            LocalDate tripDate = departureDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (!tripDate.isBefore(dayA) && !tripDate.isAfter(dayB)) {
                tripsBetweenDays.add(trip);
            }
        }

        return tripsBetweenDays;
    }

    public List<Trip> listTripsBetweenUserInputDates() {
        List<Trip> result = new ArrayList<>();

        // Create a Scanner to input the desired departure dates from the user
        Scanner scanner = new Scanner(System.in);

        try (scanner) {
            System.out.print("Enter the first date (yyyy-MM-dd): ");
            String inputDateStrA = scanner.nextLine();
            System.out.print("Enter the second date (yyyy-MM-dd): ");
            String inputDateStrB = scanner.nextLine();
            LocalDate dateA = LocalDate.parse(inputDateStrA);
            LocalDate dateB = LocalDate.parse(inputDateStrB);

            scanner.close();

            result = listTripsBetweenDays(dateA, dateB);
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd format.");
        }
        // Close the scanner when done

        return result;
    }

    // Method to calculate how much weight of each type of all containers
    public Map<ContainerType, Double> calculateTotalWeightByContainerType() {
        Map<ContainerType, Double> totalWeightByContainerType = new HashMap<>();

        for (Vehicles vehicle : this.getVehiclesUnderControl()) {
            List<Containers> loadedContainers = vehicle.getLoadedContainers();
            for (Containers container : loadedContainers) {
                ContainerType containerType = container.getType();
                double containerWeight = container.getWeight();

                totalWeightByContainerType.put(
                        containerType,
                        totalWeightByContainerType.getOrDefault(containerType, 0.0) + containerWeight);
            }
        }

        return totalWeightByContainerType;
    }
}

enum Operation {
    MANAGE_VEHICLES,
    MANAGE_CONTAINERS,
    MANAGE_PORTS,
    VIEW_HISTORY
}