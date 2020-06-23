package models.flights;

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;
import models.airplanes.Airplane;
import models.users.User;

@Document(collection = "flights", schemaVersion= "1.0")
public class Flight {
    @Id
    private int id;
    private String date;
    private Airplane airplane;
    private User user;
    private Travel travel;
    private int passengers;
    private int price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Travel getTravel() {
        return travel;
    }

    public void setTravel(Travel travel) {
        this.travel = travel;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
