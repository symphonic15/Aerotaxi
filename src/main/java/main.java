import controllers.JsonDB;
import controllers.UsersController;
import models.airplanes.Bronze;
import models.airplanes.Gold;
import models.users.User;

public class main {
    public static void main(String[] args) {
        UsersController usersController = new UsersController();

        User user = new User();
        user.setDni("42116588");
        user.setPassword("123456789");

        usersController.signUp(user);

        usersController.login("42116588", "123456789");

        System.out.println("Sesion actual: "+ usersController.getSession().getDni());
    }
}