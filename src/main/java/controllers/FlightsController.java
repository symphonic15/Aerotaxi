package controllers;

import models.airplanes.Airplane;
import models.flights.City;
import models.flights.Flight;
import models.flights.Travel;
import models.users.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FlightsController {
    private Core core;

    public FlightsController(Core core) {
        this.core = core;
    }

    public Response newFlight(Flight flight) {
        this.core.getJsonDB().insert(flight);
        return new Response(true, null);
    }

    public Response deleteFlight(Flight flight) {
        Response response = new Response(true, null);
        long daysDifference = 0;

        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Date flightDate = format.parse(flight.getDate());
            Date today = new Date();
            daysDifference = TimeUnit.DAYS.convert((flightDate.getTime() - today.getTime()), TimeUnit.MILLISECONDS);
        } catch (ParseException exception) {
            response.setSuccess(false);
            response.setMessage("Error al comprobar la fecha del vuelo.");
        }

        if(daysDifference > 0) {
            this.core.getJsonDB().remove(flight);
        } else {
            response.setSuccess(false);
            response.setMessage("Solo se pueden cancelar vuelos con uno o más dias de antelación.");
        }

        return response;
    }

    public int getFlightPrice(Travel travel, Airplane airplane, int passengers) {
        int distancePrice = travel.getDistance() * airplane.getKmCost();
        int passengersPrice = passengers * 3500;
        int airplaneFixedPrice = 0;

        switch(airplane.getClass().getSimpleName()) {
            case "Gold":
                airplaneFixedPrice = 6000;
                break;
            case "Silver":
                airplaneFixedPrice = 4000;
                break;
            case "Bronze":
                airplaneFixedPrice = 3000;
                break;
        }

        return distancePrice + passengersPrice + airplaneFixedPrice;
    }

    public Travel getTravel(City cityFrom, City cityTo) {
        Travel travel = null;

        if(cityFrom != cityTo) {
            List<Travel> query = (List<Travel>)(List<?>) this.core.getJsonDB().findWithQuery(String.format("/.[cityFrom='%s' and cityTo='%s']", cityFrom, cityTo), Travel.class);

            try {
                travel = query.get(0);
            } catch(Exception exception) {
                travel = null;
            }
        } else {
            travel = null;
        }

        return travel;
    }

    public List<Flight> getFlightsByDate(String date) {
        return (List<Flight>)(List<?>) this.core.getJsonDB().findWithQuery(String.format("/.[date='%s']", date), Flight.class);
    }

    public List<Flight> getFlightsByUser(User user) {
        return (List<Flight>)(List<?>) this.core.getJsonDB().findWithQuery(String.format("/.[user/dni='%s']", user.getDni()), Flight.class);
    }

    public Response validateDate(String string_date) {
        Response response = new Response(true, null);
        long daysDifference;

        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Date date = format.parse(string_date);
            Date today = new Date();
            daysDifference = TimeUnit.DAYS.convert((date.getTime() - today.getTime()), TimeUnit.MILLISECONDS);

            if(daysDifference <= 0) {
                response = new Response(false, "La fecha del vuelo debe ser por lo menos un dia mayor a la actual.");
            }
        } catch (ParseException exception) {
            response = new Response(false, "El formato de la fecha debe ser: dd-mm-aaaa");
        }

        return response;
    }
}
