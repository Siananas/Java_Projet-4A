package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.config.AppConfiguration;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.exception.UiException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class InterfaceMethodes {
    private ClientService clientService;
    private VehicleService vehicleService ;
    private ReservationService reservationService ;

    public InterfaceMethodes() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        this.clientService = context.getBean(ClientService.class);
        this.vehicleService = context.getBean(VehicleService.class);
        this.reservationService = context.getBean(ReservationService.class);
    }

    public void CreateClient() throws UiException {
        String nom = IOUtils.readString("Entrer le nom du client : ", true);
        String prenom = IOUtils.readString("Entrer le prénom du client : ", true);
        String email = IOUtils.readString("Entrer l'email du client : ", true);
        LocalDate naissance = IOUtils.readDate("Entrer la date de naissance du client : ", true);

        Client client = new Client(nom, prenom, email, naissance) ;
        try {
            clientService.create(client);
        } catch (ServiceException e) {
            throw new UiException("Erreur lors de la création du client.", e);
        }
    }

    public void findAllClients() throws UiException {
        try{
            List <Client> listClients = new ArrayList<>() ;
            listClients = clientService.findAll();
            for (Client client : listClients){
                System.out.println(client.toString());
            }
            if (listClients.size()==0) System.out.println("Aucun client enregistré dans la base de données");
        } catch (Exception e) {
            throw new UiException("Erreur lors de la récupération de la liste des clients.", e);
        }
    }

    public void findClientById() throws UiException {
        Integer clientId = IOUtils.readInt("Entrer l'identifiant du client : ");
        try {
            Client client = clientService.findById(clientId);
            if (client != null) {
                System.out.println(client.toString());
            } else {
                System.out.println("Aucun client trouvé avec l'identifiant spécifié.");
            }
        } catch (Exception e) {
            throw new UiException("Erreur lors de la récupération des informations du client.", e);
        }
    }

    public void CreateVehicle() throws UiException {
        String constructeur = IOUtils.readString("Entrer le constructeur du véhicule : ", true);
        String modele = IOUtils.readString("Entrer le modèle du véhicule : ", true);
        Integer nb_places = IOUtils.readInt("Entrer le nombre de place : ");

        Vehicle vehicle = new Vehicle(constructeur, modele, nb_places);

        vehicleService.create(vehicle);

    }

    public void findVehicleById() throws UiException {
        Integer vehicleId = IOUtils.readInt("Entrer l'identifiant du véhicule : ");
        try {
            Vehicle vehicle = vehicleService.findById(vehicleId);
            if (vehicle != null) {
                System.out.println(vehicle.toString());
            } else {
                System.out.println("Aucun véhicule trouvé avec l'identifiant spécifié.");
            }
        } catch (Exception e) {
            throw new UiException("Erreur lors de la récupération des informations du véhicule.", e);
        }
    }

    public void findAllVehicles() throws UiException {
        try{
            List <Vehicle> listVehicles = vehicleService.findAll();
            for (Vehicle vehicle : listVehicles){
                System.out.println(vehicle.toString());
            }
            if (listVehicles.size()==0) System.out.println("Aucun véhicule enregistré dans la base de données");
        } catch (Exception e) {
            throw new UiException("Erreur lors de la récupération de la liste des véhicules.", e);
        }
    }

    public void countVehicle() throws UiException {
        try{
            int nombreVoiture = vehicleService.count();
            System.out.println("Il y a " + nombreVoiture);
        } catch (Exception e) {
            throw new UiException("Erreur lors de la récupération de la liste des véhicules.", e);
        }
    }

    public static void DeleteClient() throws UiException {
        try{

        } catch (Exception e){

        }
    }

    public void findAllReservations() throws UiException {
        try{
            List <Reservation> listReservations = reservationService.findAll();
            for (Reservation reservation : listReservations){
                System.out.println(reservation.toString());
            }
            if (listReservations.size()==0) System.out.println("Aucune reservation enregistrée dans la base de données");
        } catch (Exception e) {
            throw new UiException("Erreur lors de la récupération de la liste des clients.", e);
        }
    }

    public void CreateReservation() throws UiException {
        //String nom = IOUtils.readString("Entrer le nom du client : ", true);
        //String prenom = IOUtils.readString("Entrer le prénom du client : ", true);
        //String email = IOUtils.readString("Entrer l'email du client : ", true);
        //LocalDate naissance = IOUtils.readDate("Entrer la date de naissance du client : ", true);

        Integer idClient = 2;
        Integer idVehicle = 2;
        LocalDate debut = LocalDate.of(2014, 4, 28);
        LocalDate fin = LocalDate.of(2014, 4, 29);

        Reservation reservation = new Reservation(idClient, idVehicle, debut, fin) ;
        try {
            reservationService.create(reservation);
        } catch (ServiceException e) {
            throw new UiException("Erreur lors de la création du client.", e);
        }
    }

    public void findReservationsByClientId() throws UiException {
        Integer clientId = IOUtils.readInt("Entrer l'identifiant du client' : ");
        try{
            List <Reservation> listReservations = new ArrayList<>() ;
            listReservations = reservationService.findByClientId(clientId);
            for (Reservation reservation : listReservations){
                System.out.println(reservation.toString());
            }
            if (listReservations.size()==0) System.out.println("Aucune reservation enregistrée dans la base de données");
        } catch (Exception e) {
            throw new UiException("Erreur lors de la récupération de la liste des reservations.", e);
        }
    }

    public void findReservationsByVehicleId() throws UiException {
        Integer vehicleId = IOUtils.readInt("Entrer l'identifiant du véhicule' : ");
        try{
            List <Reservation> listReservations = new ArrayList<>() ;
            listReservations = reservationService.findByVehicleId(vehicleId);
            for (Reservation reservation : listReservations){
                System.out.println(reservation.toString());
            }
            if (listReservations.size()==0) System.out.println("Aucune reservation enregistrée dans la base de données");
        } catch (Exception e) {
            throw new UiException("Erreur lors de la récupération de la liste des reservations.", e);
        }
    }

    public void deleteReservation() throws UiException {
        try{
            reservationService.delete(1);
        } catch (Exception e) {
            throw new UiException("Erreur lors de la récupération de la liste des reservations.", e);
        }
    }

    public void findReservationById() throws UiException {
        try{
            Reservation reservation = new Reservation() ;
            reservation = reservationService.findReservationById(1);
            System.out.println(reservation.toString());
        } catch (Exception e) {
            throw new UiException("Erreur lors de la récupération de la liste des reservations.", e);
        }
    }

    public void findAllVehiclesOfClientReservations() throws UiException {
        Integer clientId = IOUtils.readInt("Entrer l'identifiant du client : ");
        try {
//            Client client = new Client(); // Supposons que Client a un constructeur qui accepte l'id
//            client.setId(clientId);

            List<Vehicle> vehicles = clientService.findAllVehiclesOfClientReservations(clientService.findById(clientId));

            if (vehicles != null && !vehicles.isEmpty()) {
                for (Vehicle vehicle : vehicles) {
                    System.out.println(vehicle.toString());
                }
            } else {
                System.out.println("Aucun véhicule trouvé pour les réservations de ce client.");
            }
        } catch (ServiceException e) {
            throw new UiException("Erreur lors de la récupération des véhicules réservés par le client.", e);
        }
    }
};
