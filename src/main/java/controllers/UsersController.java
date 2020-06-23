package controllers;

import models.flights.Flight;
import models.users.User;

import java.util.List;

public class UsersController {
    private Core core;
    private User session;

    public UsersController(Core core) {
        this.core = core;
    }

    // Agrega el usuario ingresado a la db
    public Response signUp(User user) {
        if(this.getUserByDni(user.getDni()) == null) {
            this.core.getJsonDB().insert(user);
            return new Response(true, null);
        } else {
            return new Response(false, "El DNI ingresado ya se encuentra en la base de datos.");
        }
    }

    // Valida el usuario y contraseña y setea la session
    public Response login(String dni, String password) {
        User user = this.getUserByDni(dni);

        if(user != null && validatePassword(user, password)) {
            this.session = user;
            return new Response(true, null);
        } else {
            return new Response(false, "Usuario y/o contraseña incorrectos.");
        }
    }

    // Cierra la session actual
    public Response signOut() {
        this.session = new User();
        return new Response(true, null);
    }

    // Retorna todos los usuarios de la db
    public List<User> getAllUsers() {
        return (List<User>)(List<?>) this.core.getJsonDB().findAll(User.class);
    }

    // Retorna un usuario por dni
    public User getUserByDni(String dni) {
        return (User) this.core.getJsonDB().find(dni, User.class);
    }

    // Valida que la contraseña ingresada coincida con la del usuario
    public boolean validatePassword(User user, String password) {
        if(user.getPassword().equals(password))
            return true;
        return false;
    }

    // Retorna el costo total de los vuelos realizados por el usuario
    public int getTotalSpent(User user) {
        int totalSpent = 0;

        for(Flight flight : core.getFlightsController().getFlightsByUser(user)) {
            totalSpent += flight.getPrice();
        }

        return totalSpent;
    }

    // Retorna el avion de mayor categoria en el que viajo el usuario
    public String getTopAirplane(User user) {
        String airplane = null;

        for(Flight flight : core.getFlightsController().getFlightsByUser(user)) {
            switch(flight.getCategory()) {
                case "Bronze":
                    if(airplane == null) {
                        airplane = "Bronze";
                    }
                    break;
                case "Silver":
                    if(airplane == null || airplane.equals("Bronze")) {
                        airplane = "Silver";
                    }
                    break;
                case "Gold":
                    airplane = "Gold";
                    break;
            }
        }

        if(airplane == null) {
            airplane = "Sin vuelos";
        }

        return airplane;
    }

    // Retorna la session actual
    public User getSession() {
        return session;
    }
}
