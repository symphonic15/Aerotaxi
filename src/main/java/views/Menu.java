package views;
import controllers.Core;
import controllers.Response;
import models.users.User;
import models.users.UserType;
import java.util.Scanner;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private Core core;
    private AdminMenu admin;
    private UserMenu user;

    public Menu(Core core){
        this.core = core;
        this.user = new UserMenu(core, this);
        this.admin = new AdminMenu(core, this);
    }


    public void home(){ //Muestra las opciones de inicio de sesion
        System.out.println("Bienvenidos a AeroTaxi!!!");
        System.out.println("Inserte la opcion que desee realizar");
        System.out.println("1) Ingresar");
        System.out.println("2) Registrarse");
        System.out.println("3) Salir");
        int option = scanner.nextInt();
        scanner.nextLine();
        switch(option){
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            default:
                System.exit(0);
        }
    }

    public void login(){ //Login de usuario
        System.out.println("Ingrese Dni");
        String dni = scanner.nextLine();
        System.out.println("Ingrese Contraseña");
        String password = scanner.nextLine();
        Response response = this.core.getUsersController().login(dni, password); //Chequea los datos ingresados
        if(response.isSuccess()){
            if(this.core.getUsersController().getSession().getUserType() == UserType.ADMIN){ //Si es admin muestra menu admin
                admin.menuAdmin();
            }
            else { //Sino menu user
                user.menuUsuario();
            }
        }
        else{ //En caso de que no haya podido registrar al usuario vuelve a empezar la funcion
            System.out.println(response.getMessage());
            login();
        }
    }

    public void register(){ //Registro de usuario
        User user = new User();
        boolean flag = false;
        do {
            System.out.println("Ingrese su DNI:"); //Ingresa datos
            user.setDni(scanner.nextLine());
            System.out.println("Ingrese su Nombre:");
            user.setName(scanner.nextLine());
            System.out.println("Ingrese su Apellido:");
            user.setLastName(scanner.nextLine());
            System.out.println("Ingrese su Edad:");
            user.setEdad(scanner.nextInt());
            scanner.nextLine();
            System.out.println("Ingrese su Contraseña:");
            user.setPassword(scanner.nextLine());
            user.setUserType(UserType.USER);
            Response response = this.core.getUsersController().signUp(user); //Registra al usuario
            if(response.isSuccess()){
                flag = true;
                System.out.println("Usuario ingresado correctamente...");
            }
            else{
                System.out.println(response.getMessage());
            }
        }while(!flag);
        home(); //Vuelve a la funcion anterior
    }

}
