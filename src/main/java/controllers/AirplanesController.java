package controllers;

import models.airplanes.Airplane;
import models.airplanes.Bronze;
import models.airplanes.Gold;
import models.airplanes.Silver;
import models.flights.Flight;

import java.util.ArrayList;
import java.util.List;

public class AirplanesController {
    private Core core;

    public AirplanesController(Core core) {
        this.core = core;
    }

    // Agrega un avion a la db
    public Response addAirplane(Airplane airplane) {
        Response response = new Response(true, null);
        try {
            this.core.getJsonDB().insert(airplane);
        } catch(Exception exception) {
            response = new Response(false, "Error al agregar avion a la base de datos");
        }

        return response;
    }

    // Obtengo todos los aviones cargados
    public List<Airplane> getAllAirplanes() {
        List<Airplane> airplanes = new ArrayList<Airplane>();

        List<Gold> goldAirplanes = (List<Gold>)(List<?>) this.core.getJsonDB().findAll(Gold.class);
        List<Silver> silverAirplanes = (List<Silver>)(List<?>) this.core.getJsonDB().findAll(Silver.class);
        List<Bronze> bronzeAirplanes = (List<Bronze>)(List<?>) this.core.getJsonDB().findAll(Bronze.class);

        airplanes.addAll(goldAirplanes);
        airplanes.addAll(silverAirplanes);
        airplanes.addAll(bronzeAirplanes);

        return airplanes;
    }

    // Obtengo todos los aviones disponibles en la fecha definida
    public List<Airplane> getAvailableAirplanes(String date, int passengers) {
        List<Airplane> freeAirplanes = this.getAllAirplanes();

        for(Flight flight : this.core.getFlightsController().getFlightsByDate(date)) {
            freeAirplanes.removeIf(airplane -> {
                return airplane.getId().equals(flight.getAirplane().getId()) || airplane.getMaxPassengers() < passengers;
            });
        }

        return freeAirplanes;
    }

    // Validador de atributo kmCost
    public Response validateKmCost(int km_cost) {
        if(km_cost >= 150 && km_cost <= 300)
            return new Response(true, null);
        else
            return new Response(false, "El costo por kilometro debe ser de entre $150 y $300");
    }
}
