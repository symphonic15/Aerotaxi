package models.flights;

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;
import models.airplanes.Airplane;
import models.users.User;

import java.util.Date;

@Document(collection = "flights", schemaVersion= "1.0")
public class Flights {
    @Id
    private int id;
    private Date fecha;
    private Airplane airplane;
    private User user;
    private Cities origen;
    private Cities destino;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public Cities getOrigen() {
        return origen;
    }

    public void setOrigen(Cities origen) {
        this.origen = origen;
    }

    public Cities getDestino() {
        return destino;
    }

    public void setDestino(Cities destino) {
        this.destino = destino;
    }
}
