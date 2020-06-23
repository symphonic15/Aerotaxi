package models.airplanes;

import io.jsondb.annotation.Id;

public class Airplane {
    @Id
    private int id;
    private int fuel;
    private int kmCost;
    private int maxPassengers;
    private int kmSpeed;
    private PropulsionType propulsionType;
    private String name;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public int getKmCost() {
        return kmCost;
    }

    public void setKmCost(int kmCost) {
        this.kmCost = kmCost;
    }

    public int getMaxPassengers() {
        return maxPassengers;
    }

    public void setMaxPassengers(int maxPassengers) {
        this.maxPassengers = maxPassengers;
    }

    public int getKmSpeed() {
        return kmSpeed;
    }

    public void setKmSpeed(int kmSpeed) {
        this.kmSpeed = kmSpeed;
    }

    public PropulsionType getPropulsionType() {
        return propulsionType;
    }

    public void setPropulsionType(PropulsionType propulsionType) {
        this.propulsionType = propulsionType;
    }
}
