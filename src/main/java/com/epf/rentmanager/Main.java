package com.epf.rentmanager;

import com.epf.rentmanager.exception.UiException;
import com.epf.rentmanager.ui.cli.InterfaceMethodes;
import com.epf.rentmanager.utils.IOUtils;

import static com.epf.rentmanager.utils.IOUtils.readInt;

public class Main {

    public static void main(String[] args) throws UiException {


        System.out.println("Menu:");
        System.out.println("1. Créer un client");
        System.out.println("2. Trouver tous les clients");
        System.out.println("3. Supprimer un client");
        System.out.println("4. Créer un véhicule");
        System.out.println("5. Trouver tous les véhicules");
        System.out.println("6. Supprimer un véhicule");
        System.out.println("7. Créer une réservation");
        System.out.println("8. Trouver toutes les réservations");
        System.out.println("9. Trouver les réservations par ID de client");
        System.out.println("10. Trouver les réservations par ID de véhicule");
        System.out.println("11. Trouver une réservation par ID");
        System.out.println("12. Supprimer une réservation");
        System.out.println("13. Compter le nombre de vehicule");
        System.out.println("0. Quitter");
        System.out.print("Choisissez une option : ");

        int choice = readInt("Choisissez une option : ");
        switch (choice) {
            case 1:
                InterfaceMethodes.CreateClient();
                break;
            case 2:
                InterfaceMethodes.findAllClients();
                break;
            case 3:
                //InterfaceMethodes.deleteClient();
                break;
            case 4:
                InterfaceMethodes.CreateVehicle();
                break;
            case 5:
                InterfaceMethodes.findAllVehicles();
                break;
            case 6:
                //InterfaceMethodes.deleteVehicule();
                break;
            case 7:
                InterfaceMethodes.CreateReservation();
                break;
            case 8:
                InterfaceMethodes.findAllReservations();
                break;
            case 9:
                InterfaceMethodes.findReservationsByClientId();
                break;
            case 10:
                InterfaceMethodes.findReservationsByVehicleId();
                break;
            case 11:
                InterfaceMethodes.findReservationById();
                break;
            case 12:
                InterfaceMethodes.deleteReservation();
                break;
            case 13:
                InterfaceMethodes.countVehicle();
                break;

            default:
                System.out.println("Choix invalide, veuillez réessayer.");

        }
        System.out.println("Merci d'avoir utilisé le programme. Au revoir !");






        //String nom = IOUtils.readString("Votre demande concerne : 1=client, 2= : ", true);

        //InterfaceMethodes.CreateClient();
        //InterfaceMethodes.findAllClients() ;
        //InterfaceMethodes.deleteClient() ;
        //InterfaceMethodes.CreateVehicle();
        //InterfaceMethodes.findAllVehicles();
        //InterfaceMethodes.deleteVehicule() ;
        //InterfaceMethodes.CreateReservation();
        //InterfaceMethodes.findAllReservations();
        //InterfaceMethodes.findReservationsByClientId();
        //InterfaceMethodes.findReservationsByVehicleId();
        //InterfaceMethodes.findReservationById();
        //InterfaceMethodes.deleteReservation() ;
        //Utiliser doa, servelet, spring, faire tests, utiliser des exceptions, logique de back (message d'erreur, barrières, git, qualité du code, nouveauté de java, front)
    }
}
