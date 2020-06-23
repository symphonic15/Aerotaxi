package views;

import controllers.Core;
import controllers.Response;
import models.airplanes.Airplane;
import models.flights.City;
import models.flights.Flight;
import models.flights.Travel;
import models.users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserMenu {

    private Scanner scanner = new Scanner(System.in);
    private Core core;
    private Menu menu;

    public UserMenu(Core core, Menu menu){
        this.core = core;
        this.menu = menu;
    }

    public void menuUsuario(){
        User user = this.core.getUsersController().getSession(); //Obtengo al usuario
        System.out.println("Bienvenido "+ user.getName()+ " " + user.getLastName() );
        System.out.println("Inserte la opcion que desee realizar");
        System.out.println("1) Programar un vuelo");
        System.out.println("2) Cancelar un vuelo");
        System.out.println("3) Cerrar sesion");
        System.out.println("4) Salir");
        int option = scanner.nextInt();
        scanner.nextLine();
        switch(option){  //Paso a la siguiente funcion segun lo que elija el usuario
            case 1:
                flightScheduler();
                break;
            case 2:
                deleteFlight();
                break;
            case 3:
                this.core.getUsersController().signOut();
                menu.home();
                break;
            default:
                System.exit(0);
                break;
        }
    }

    public void flightScheduler() {
        String date;
        boolean flag = false;
        int rta;
        do {    //Solicito la fecha
            System.out.println("Ingrese la fecha en la que planea viajar (dd-mm-yyyy)");
            date = scanner.nextLine();
            Response response = this.core.getFlightsController().validateDate(date);
            if (response.isSuccess()) {
                flag = true;
            } else {
                System.out.println(response.getMessage());
            }
        } while (!flag);
        Travel travel = travelMenu(); //Pido origen y Destino
        System.out.println("Ingrese la cantidad de acompañantes");
        int passengers = scanner.nextInt(); //Solicito cant acompañantes
        scanner.nextLine();
        List<Airplane> aviones = this.core.getAirplanesController().getAvailableAirplanes(date, passengers);
        if(!aviones.isEmpty()){
            System.out.println("Ingrese el avion que desea tomar: "); //Doy a elegir el avion
            for(int i = 0; i<aviones.size(); i++){ //use el for común porque necesitaba la ubicacion en el arreglo
                Airplane a = (Airplane) aviones.get(i);
                System.out.println(i+") "+a.getName() + " " +a.getClass().getSimpleName());
            }
            rta = scanner.nextInt();
            scanner.nextLine();
            System.out.println(rta);
            Airplane airplane = aviones.get(rta);

            int price = this.core.getFlightsController().getFlightPrice(travel, airplane, passengers); //Calculo precio

            System.out.println("El precio de su vuelo es de: $"+price);
            System.out.println("Desea confirmar su vuelo?");
            System.out.println("1) Si");
            System.out.println("2) No");
            rta = scanner.nextInt();
            scanner.nextLine();
            if(rta == 1){ //Si dio sí, creo el vuelo
                Flight flight = new Flight();
                flight.setAirplane(airplane);
                flight.setDate(date);
                flight.setCategory(airplane.getClass().getSimpleName());
                flight.setPassengers(passengers);
                flight.setTravel(travel);
                flight.setUser(this.core.getUsersController().getSession());
                flight.setPrice(price);
                this.core.getFlightsController().newFlight(flight); //Lo guardo
                System.out.println("El vuelo se guardó con éxito");
            }
            menuUsuario(); //
        }
        else{
            System.out.println("No hay aviones disponibles para esa fecha, intente nuevamente");
            flightScheduler(); //Vuelvo a empezar la funcion
        }

    }

    public Travel travelMenu(){ //Da las opciones a elegir de origen y destino
        Travel travel;
        int rta;
        do{
            do {
                System.out.println("Ingrese el origen");
                System.out.println("1) Buenos Aires");
                System.out.println("2) Cordoba");
                System.out.println("3) Montevideo");
                System.out.println("4) Santiago");
                rta = scanner.nextInt();
                scanner.nextLine();
            } while (rta <= 1 && rta >= 4);
            City origin = null;
            switch (rta) {
                case 1:
                    origin = City.BUENOS_AIRES;
                    break;
                case 2:
                    origin = City.CORDOBA;
                    break;
                case 3:
                    origin = City.MONTEVIDEO;
                    break;
                case 4:
                    origin = City.SANTIAGO;
                    break;
                default:
                    origin = null;
                    break;
            }

            do {
                System.out.println("Ingrese el destino");
                System.out.println("1) Buenos Aires");
                System.out.println("2) Cordoba");
                System.out.println("3) Montevideo");
                System.out.println("4) Santiago");
                rta = scanner.nextInt();
                scanner.nextLine();
            } while (rta <= 1 && rta >= 4);
            City destiny = null;
            switch (rta) {
                case 1:
                    destiny = City.BUENOS_AIRES;
                    break;
                case 2:
                    destiny = City.CORDOBA;
                    break;
                case 3:
                    destiny = City.MONTEVIDEO;
                    break;
                case 4:
                    destiny = City.SANTIAGO;
                    break;
                default:
                    destiny = null;
                    break;
            }
            travel = this.core.getFlightsController().getTravel(origin, destiny);
            if(travel == null){
                System.out.println("No puedes ingresar la misma ciudad como origen y destino");
            }
        }while(travel == null);
        return travel;
    }

    public void deleteFlight(){ //Borra un vuelo a elegir

        ArrayList<Flight> flights = (ArrayList<Flight>) this.core.getFlightsController().getFlightsByUser(this.core.getUsersController().getSession());

        if(!flights.isEmpty()) { //Enseño todos los vuelos del usuario
            System.out.println("Seleccione el vuelo que desea borrar");
            for (int i = 0; i < flights.size(); i++) { //uso el for común para usar el contador
                Flight flight = flights.get(i);
                System.out.println(i + ") " + flight.getAirplane().getName() + " " + flight.getDate());
            }
            System.out.println(flights.size()+") Volver atrás");

            int rta = scanner.nextInt();
            scanner.nextLine();

            if(rta == flights.size()){ //Vuelvo atrás
                menuUsuario();
            }
            else {

                Flight flight = flights.get(rta);
                System.out.println("¿Realmente desea volar el vuelo " + flight.getAirplane().getName() + " " + flight.getDate() + "?");
                System.out.println("1) Si");
                System.out.println("2) No");
                rta = scanner.nextInt();
                scanner.nextLine();

                if (rta == 1) {
                    Response response = this.core.getFlightsController().deleteFlight(flight);
                    if (response.isSuccess()) {
                        System.out.println("La operación se completó con éxito");
                    } else {
                        System.out.println(response.getMessage());
                    }
                } else {
                    System.out.println("Se canceló la operación");
                }
            }
        }
        else{
            System.out.println("Usted no reservó ningún vuelo");
        }
        menuUsuario(); //Vuelvo al menu anterior
    }
}
