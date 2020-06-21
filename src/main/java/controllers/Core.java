package controllers;

public class Core {
    private JsonDB jsonDB;
    private UsersController usersController;
    private AirplanesController airplanesController;
    private FlightsController flightsController;

    public Core() {
        this.jsonDB = new JsonDB();
        this.usersController = new UsersController(this);
        this.airplanesController = new AirplanesController(this);
        this.flightsController = new FlightsController(this);
    }

    public JsonDB getJsonDB() {
        return jsonDB;
    }

    public void setJsonDB(JsonDB jsonDB) {
        this.jsonDB = jsonDB;
    }

    public UsersController getUsersController() {
        return usersController;
    }

    public void setUsersController(UsersController usersController) {
        this.usersController = usersController;
    }

    public AirplanesController getAirplanesController() {
        return airplanesController;
    }

    public void setAirplanesController(AirplanesController airplanesController) {
        this.airplanesController = airplanesController;
    }

    public FlightsController getFlightsController() {
        return flightsController;
    }

    public void setFlightsController(FlightsController flightsController) {
        this.flightsController = flightsController;
    }
}
