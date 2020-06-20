package view.menu;
import controllers.UsersController;
import models.users.User;

import java.io.PrintStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * En proceso:
 *      registrar un usuario
 *
 * Cambios realizados:
 *      quitado el nickname del usuario, junto con las funciones que lo chekean
 *      el usuario puede ingresar directamente usando su DNI, asi hacemos una sola comprobacion
 */


public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private String titulo;
    private List<String> opciones;
    private UsersController usersController;

    public Menu(){

    }


    public void home(){
        System.out.println("Bienvenidos a AeroTaxi!!!");
        System.out.println("Inserte la opcion que desee realizar");
        System.out.println("1) Ingresar");
        System.out.println("2) Registrarse");
        System.out.println("3) Salir");
        int option = scanner.nextInt();
        switch(option){
            case 1:
                login();
                break;
            case 2:
            //    register();
                break;
            default:
                System.exit(0);
        }
    }

    public void login(){
        System.out.println("Ingrese Dni");
        String dni = scanner.nextLine();
        System.out.println("Ingrese Contraseña");
        String password = scanner.nextLine();

        User user = usersController.getUserByDni(dni);



    }


}
