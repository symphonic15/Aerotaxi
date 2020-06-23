import controllers.Core;
import controllers.JsonDB;
import controllers.UsersController;
import models.airplanes.Bronze;
import models.airplanes.Gold;
import models.airplanes.PropulsionType;
import models.flights.City;
import models.flights.Travel;
import models.users.User;
import models.users.UserType;
import views.Menu;

public class main {
    public static void main(String[] args) {
        Core core = new Core();
        Menu menu = new Menu(core);
        menu.home();
    }
}