package view.menu;

import controllers.JsonDB;
import models.airplanes.Airplane;
import models.airplanes.Bronze;
import models.flights.Flights;
import models.users.User;

import java.util.ArrayList;
import java.util.List;

public class Core {

    private List<User> listaUsuarios;   ///Contiene el registro de todos los usuarios de la empresa
    private List<Flights> listaVuelos;        ///Contiene el registro de todos los vuelos contratados
    private List<Airplane> listaAviones;      ///Contiene la flota de aviones con los que cuenta la empresa
    private JsonDB jsonDB;

    public Core(){
        this.listaUsuarios = new ArrayList<>();
        this.listaAviones = new ArrayList<>();
        this.listaVuelos = new ArrayList<>();
        this.jsonDB = new JsonDB();
        getAirplanesFromFile();
        getFlightsFromFile();
        getUsersFromFile();
    }

    public void getAirplanesFromFile(){
        for(Object x : jsonDB.findAll(Bronze.class)){
            listaAviones.add((Airplane) x);
        }
    }

    public void getFlightsFromFile(){
        for(Object x : jsonDB.findAll(Bronze.class)){
            listaVuelos.add((Flights) x);
        }
    }

    public void getUsersFromFile(){
        for(Object x : jsonDB.findAll(Bronze.class)){
            listaUsuarios.add((User) x);
        }
    }


}
