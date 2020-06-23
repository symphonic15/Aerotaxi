package views;

import controllers.Core;
import controllers.Response;
import models.airplanes.*;
import models.airplanes.services.Catering;
import models.flights.Flight;
import models.users.User;

import java.util.ArrayList;
import java.util.Scanner;

public class AdminMenu {

    private Scanner scanner = new Scanner(System.in);
    private Core core;
    private Menu menu;

    public AdminMenu(Core core, Menu menu){
        this.core = core;
        this.menu = menu;
    }

    public void menuAdmin(){
        User user = this.core.getUsersController().getSession(); //Obtengo la sesion

        System.out.println("Bienvenido "+ user.getName()+ " " + user.getLastName() );
        System.out.println("Inserte la opcion que desee realizar");
        System.out.println("1) Agregar un avion");
        System.out.println("2) Listar vuelos por fecha");
        System.out.println("3) Listar usuarios");
        System.out.println("4) Cerrar sesion");
        System.out.println("5) Salir");
        int option = scanner.nextInt();
        scanner.nextLine();
        switch(option){ //Le hago elegir a que función desea saltar
            case 1:
                addAirplane();
                break;
            case 2:
                listFlightsByDate();
                break;
            case 3:
                listUsers();
                break;
            case 4:
                this.core.getUsersController().signOut();
                menu.home();
            default:
                System.exit(0);
        }
    }

    public void addAirplane(){
        boolean flag = false;
        int option;
        Airplane airplane = selectClass(); //Selecciona la clase del avion segun las preferencias
        System.out.println("Ingrese un nombre para el avión: ");
        airplane.setName(scanner.nextLine());
        Response response;
        int rta;
        do{
            System.out.println("Ingrese los costos por km:");
            rta = scanner.nextInt();
            scanner.nextLine();
            response = this.core.getAirplanesController().validateKmCost(rta); //Valido que el costo de los km sean entre 150 y 300
            if(response.isSuccess()){
                flag = true;
                airplane.setKmCost(rta);
            }
            else{
                System.out.println(response.getMessage());
            }
        }while(!flag);
        System.out.println("Ingrese capacidad máxima de pasajeros:"); //Ingreso capacidad max
        airplane.setMaxPassengers(scanner.nextInt());
        scanner.nextLine();
        System.out.println("Ingrese la velocidad máxima del avión:"); //Velocidad max
        airplane.setKmSpeed(scanner.nextInt());
        scanner.nextLine();
        flag = false;
        do{
            System.out.println("Ingrese el tipo de propulsión del avión:"); //Elijo el tipo de propulsión
            System.out.println("1) Motor a reaccion");
            System.out.println("2) Motor a hélice");
            System.out.println("3) Motor de pistones");
            option = scanner.nextInt();
            scanner.nextLine();
            if(option >= 1 && option <= 3){
                flag = true;
            }
            else{
                System.out.println("La opcion ingresada no es válida");
            }
        }while(!flag);
            switch(option){
            case 1:
                airplane.setPropulsionType(PropulsionType.MOTOR_A_REACCION);
                break;
            case 2:
                airplane.setPropulsionType(PropulsionType.MOTOR_A_HELICE);
                break;
            case 3:
                airplane.setPropulsionType(PropulsionType.MOTOR_DE_PISTONES);
                break;
            default:
                System.out.println("Ha ocurrido un error al seleccionar el tipo de propulsión del avión");
                break;
        }
        if(airplane instanceof Gold || airplane instanceof Silver){ //Si el avion es gold o silver, que elija su catering
            airplane = addCatering(airplane);
        }
        if(airplane instanceof Gold){ //Si el avion es gold, elige tener wifi o no
            System.out.println("¿El avión tiene wifi?");
            System.out.println("1) Si");
            System.out.println("2) No");
            option = scanner.nextInt();
            scanner.nextLine();
            if(option == 1){
                ((Gold) airplane).setWifi(true);
            }
            else{
                ((Gold) airplane).setWifi(false);
            }
        }
        this.core.getAirplanesController().addAirplane(airplane); //Agrego el avion
        System.out.println("El avión se registró exitosamente");
        menuAdmin();
    }

    public Airplane selectClass(){
        Airplane airplane;
        int option;
        boolean flag = false;
        do{
            System.out.println("Ingrese el tipo de avion:");
            System.out.println("1) Bronze");
            System.out.println("2) Silver");
            System.out.println("3) Gold");
            option = scanner.nextInt();
            scanner.nextLine();
            if(option >= 1 && option <= 3){
                flag = true;
            }
            else{
                System.out.println("La opcion ingresada no es válida");
            }
        }while(!flag);
        switch(option){
            case 1:
                airplane = new Bronze();
                break;
            case 2:
                airplane = new Silver();
                break;
            case 3:
                airplane = new Gold();
                break;
            default:
                airplane = null;
                break;
        }
        return airplane;
    }

    public Airplane addCatering(Airplane airplane){ //Da las opciones para agregar catering a los aviones silver y gold
        int option;
        System.out.println("Agregue el catering: ");
        Catering catering = new Catering();
        ArrayList<String> foods = new ArrayList();
        do {
            System.out.println("Ingrese comidas que el avion ofrecerá como menú.");
            foods.add(scanner.nextLine());
            System.out.println("Desea agregar otra comida?");
            System.out.println("1) Si");
            System.out.println("2) No");
            option = scanner.nextInt();
            scanner.nextLine();
        }while(option == 1);
        catering.setFoods(foods);

        ArrayList<String> drinks = new ArrayList();
        do {
            System.out.println("Ingrese bebidas que el avion ofrecerá como menú.");
            drinks.add(scanner.nextLine());
            System.out.println("Desea agregar otra bebida?");
            System.out.println("1) Si");
            System.out.println("2) No");
            option = scanner.nextInt();
            scanner.nextLine();
        }while(option == 1);
        catering.setDrinks(drinks);

        if(airplane instanceof Gold) {
            ((Gold) airplane).setCatering(catering);
        }
        else if(airplane instanceof Silver){
            ((Silver) airplane).setCatering(catering);
        }
        return airplane;
    }

    public void listFlightsByDate(){ //Lista aviones por fecha
        System.out.println("Ingrese la fecha a buscar (dd-mm-yyyy): ");
        boolean flag = false;
        String date = scanner.nextLine();
        Response response = this.core.getFlightsController().validateDate(date); //Valido que la fecha ingresada tenga el formato correcto
        do {
            if (response.isSuccess()) {
                flag = true;
                ArrayList<Flight> flights = (ArrayList) this.core.getFlightsController().getFlightsByDate(date); //Obtengo los vuelos de esa fecha
                if(!flights.isEmpty()) {
                    for (int i = 0; i < flights.size(); i++) { //uso for comun para tener el index y mostrarlo
                        System.out.println(i + ") " + flights.get(i).getAirplane().getName() + " " + flights.get(i).getCategory() + " " +
                                flights.get(i).getUser().getDni() + " " + flights.get(i).getUser().getLastName() + " " + flights.get(i).getUser().getName());
                    }
                }
                else{
                    System.out.println("No hay vuelos registrados para esa fecha.");
                }
            }
            else{
                System.out.println(response.getMessage());
            }
        }while(!flag);
        menuAdmin();
    }

    public void listUsers(){
        ArrayList<User> users = (ArrayList) this.core.getUsersController().getAllUsers(); //obtengo todos los usuarios
        if(!users.isEmpty()) {
            for (User user : users) {//uso for comun para obtener el index
                System.out.println("DNI: " + user.getDni());
                System.out.println("Apellido: " + user.getLastName());
                System.out.println("Nombre: " + user.getName());
                System.out.println("Edad: " + user.getEdad());
                System.out.println("Tipo de usuario: " + user.getUserType());
                System.out.println("Mejor avión utilizado: " + this.core.getUsersController().getTopAirplane(user)); //Obtengo el mejor avion
                System.out.println("Total gastado en todos sus vuelos: $" + this.core.getUsersController().getTotalSpent(user)); //Obtengo el costo total de sus vuelos
                System.out.println();
            }
        }
        else{
            System.out.println("No se encontraron usuarios...");
        }
        menuAdmin();
    }
}
