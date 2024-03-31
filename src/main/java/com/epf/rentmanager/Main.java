package com.epf.rentmanager;

import com.epf.rentmanager.config.AppConfiguration;
import com.epf.rentmanager.exception.UiException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.ui.cli.InterfaceMethodes;
import com.epf.rentmanager.utils.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static com.epf.rentmanager.utils.IOUtils.readInt;

public class Main {

    public static void main(String[] args) throws UiException {

        //VehicleService vehicleService = context.getBean(VehicleService.class);


        System.out.println("Menu:");
        System.out.println("1. Créer un client");
        System.out.println("2. Trouver un client par ID");
        System.out.println("3. Trouver tous les clients");
        System.out.println("4. Supprimer un client");
        System.out.println("5. Créer un véhicule");
        System.out.println("6. Trouver un véhicule par ID");
        System.out.println("7. Trouver tous les véhicules");
        System.out.println("8. Supprimer un véhicule");
        System.out.println("9. Créer une réservation");
        System.out.println("10. Trouver une réservation par ID");
        System.out.println("11. Trouver les réservations par ID de client");
        System.out.println("12. Trouver les réservations par ID de véhicule");
        System.out.println("13. Trouver toutes les réservations");
        System.out.println("14. Supprimer une réservation");
        System.out.println("15. Compter le nombre de véhicules");
        System.out.println("16. Afficher tous les véhicules réservé par un client");
        System.out.println("0. Quitter");
        System.out.print("Choisissez une option : ");


        int choice = readInt("Choisissez une option : ");
        InterfaceMethodes interfaceMethodes = new InterfaceMethodes();
        switch (choice) {
            case 1:
                interfaceMethodes.CreateClient();
                break;
            case 2:
                interfaceMethodes.findClientById();
                break;
            case 3:
                interfaceMethodes.findAllClients();
                break;
            case 4:
                // InterfaceMethodes.deleteClient();
                break;
            case 5:
                interfaceMethodes.CreateVehicle();
                break;
            case 6:
                interfaceMethodes.findVehicleById();
                break;
            case 7:
                interfaceMethodes.findAllVehicles();
                break;
            case 8:
                // InterfaceMethodes.deleteVehicule();
                break;
            case 9:
                interfaceMethodes.CreateReservation();
                break;
            case 10:
                interfaceMethodes.findReservationById();
                break;
            case 11:
                interfaceMethodes.findReservationsByClientId();
                break;
            case 12:
                interfaceMethodes.findReservationsByVehicleId();
                break;
            case 13:
                interfaceMethodes.findAllReservations();
                break;
            case 14:
                interfaceMethodes.deleteReservation();
                break;
            case 15:
                interfaceMethodes.countVehicle();
                break;
            case 16:
                interfaceMethodes.findAllVehiclesOfClientReservations();
                break;
            case 0:
                System.out.println("Merci d'avoir utilisé le programme. Au revoir !");
                return; // Pour sortir de la méthode et potentiellement terminer l'exécution du programme.
            default:
                System.out.println("Choix invalide, veuillez réessayer.");
        }













        //Utiliser doa, servelet, spring, faire tests, utiliser des exceptions, logique de back (message d'erreur, barrières, git, qualité du code, nouveauté de java, front)
    }
}
