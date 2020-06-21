package controllers;

import models.airplanes.Airplane;
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

    public int newFlight(Flight flight) {
        int code = 200;
        this.core.getJsonDB().insert(flight);
        return code;
    }

    public int deleteFlight(Flight flight) {
        int code = 200;
        this.core.getJsonDB().remove(flight);
        return code;
    }

    public int getFlightPrice(Travel travel, Airplane airplane, int passengers) {
        int distancePrice = travel.getDistance() * airplane.getKmCost();
        int passengersPrice = passengers * 3500;
        int airplaneFixedPrice = airplane.getFixedPrice();

        return distancePrice + passengersPrice + airplaneFixedPrice;
    }

    public Travel getTravel(String cityFrom, String cityTo) {
        List<Travel> query = (List<Travel>)(List<?>) this.core.getJsonDB().findWithQuery(String.format("/.[cityFrom='%s' and cityTo='%s']", cityFrom, cityTo), Travel.class);
        return query.get(0);
    }

    public List<Flight> getFlightsByDate(String date) {
        return (List<Flight>)(List<?>) this.core.getJsonDB().findWithQuery(String.format("/.[date='%s']", date), Flight.class);
    }

    public List<Flight> getFlightsByUser(User user) {
        return (List<Flight>)(List<?>) this.core.getJsonDB().findWithQuery(String.format("/.[user.dni='%s']", user.getDni()), Flight.class);
    }
}
