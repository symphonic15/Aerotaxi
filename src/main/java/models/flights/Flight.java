package models.flights;

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;
import models.airplanes.Airplane;
import models.users.User;

import java.util.Date;

@Document(collection = "flights", schemaVersion= "1.0")
public class Flight {
    @Id
    private int id;
    private Date date;
    private Airplane airplane;
    private User user;
    private Travel travel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
}
