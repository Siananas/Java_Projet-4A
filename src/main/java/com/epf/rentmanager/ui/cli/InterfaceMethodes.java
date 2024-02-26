package com.epf.rentmanager.ui.cli;

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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InterfaceMethodes {
    public static void CreateClient() throws UiException {
        //String nom = IOUtils.readString("Entrer le nom du client : ", true);
        //String prenom = IOUtils.readString("Entrer le prénom du client : ", true);
        //String email = IOUtils.readString("Entrer l'email du client : ", true);
        //LocalDate naissance = IOUtils.readDate("Entrer la date de naissance du client : ", true);

        String nom = "Bob";
        String prenom = "Bricoleur";
        String email = "bob.bricoleur@gmail.com";
        LocalDate naissance = LocalDate.of(2014, 4, 28);

        Client client = new Client(nom, prenom, email, naissance) ;
        try {
            ClientService.getInstance().create(client);
        } catch (ServiceException e) {
            throw new UiException("Erreur lors de la création du client.", e);
        }
    }

    public  static void findAllClients() throws UiException {
        try{
            List <Client> listClients = new ArrayList<>() ;
            listClients = ClientDao.getInstance().findAll();
            for (Client client : listClients){
                System.out.println(client.toString());
            }
            if (listClients.size()==0) System.out.println("Aucun client enregistré dans la base de données");
        } catch (Exception e) {
            throw new UiException("Erreur lors de la récupération de la liste des clients.", e);
        }
    }

    public static void findClientById() throws UiException {
        try{
            Integer nb_places = IOUtils.readInt("Entrer le nombre de place : ");
        } catch (Exception e){
            throw new UiException("Erreur lors de la récupération du client.", e);
        }
    }

    public static void CreateVehicle() throws UiException {
        //String nom = IOUtils.readString("Entrer le nom du client : ", true);
        //String prenom = IOUtils.readString("Entrer le prénom du client : ", true);
        //String email = IOUtils.readString("Entrer l'email du client : ", true);
        //LocalDate naissance = IOUtils.readDate("Entrer la date de naissance du client : ", true);

        //String constructeur = IOUtils.readString("Entrer le constructeur du véhicule : ", true);
        //String modele = IOUtils.readString("Entrer le modèle du véhicule : ", true);
        //Integer nb_places = IOUtils.readInt("Entrer le nombre de place : ");

        String constructeur = "Opel";
        String modele = "4008k";
        Integer nb_places = 4;

        Vehicle vehicle = new Vehicle(constructeur, modele, nb_places);

        try {
            VehicleService.getInstance().create(vehicle);
        } catch (ServiceException e) {
            throw new UiException("Erreur lors de la création du véhicule.", e);
        }
    }
    public  static void findAllVehicles() throws UiException {
        try{
            List <Vehicle> listVehicles = new ArrayList<>() ;
            listVehicles = VehicleDao.getInstance().findAll();
            for (Vehicle vehicle : listVehicles){
                System.out.println(vehicle.toString());
            }
            if (listVehicles.size()==0) System.out.println("Aucun véhicule enregistré dans la base de données");
        } catch (Exception e) {
            throw new UiException("Erreur lors de la récupération de la liste des véhicules.", e);
        }
    }

    public static void DeleteClient() throws UiException {
        try{

        } catch (Exception e){

        }
    }

    public  static void findAllReservations() throws UiException {
        try{
            List <Reservation> listReservations = new ArrayList<>() ;
            listReservations = ReservationDao.getInstance().findAll();
            for (Reservation reservation : listReservations){
                System.out.println(reservation.toString());
            }
            if (listReservations.size()==0) System.out.println("Aucune reservation enregistrée dans la base de données");
        } catch (Exception e) {
            throw new UiException("Erreur lors de la récupération de la liste des clients.", e);
        }
    }

    public static void CreateReservation() throws UiException {
        //String nom = IOUtils.readString("Entrer le nom du client : ", true);
        //String prenom = IOUtils.readString("Entrer le prénom du client : ", true);
        //String email = IOUtils.readString("Entrer l'email du client : ", true);
        //LocalDate naissance = IOUtils.readDate("Entrer la date de naissance du client : ", true);

        Integer idClient = 1;
        Integer idVehicle = 1;
        LocalDate debut = LocalDate.of(2014, 4, 28);
        LocalDate fin = LocalDate.of(2014, 4, 29);

        Reservation reservation = new Reservation(idClient, idVehicle, debut, fin) ;
        try {
            ReservationService.getInstance().create(reservation);
        } catch (ServiceException e) {
            throw new UiException("Erreur lors de la création du client.", e);
        }
    }

    public  static void findReservationsByClientId() throws UiException {
        Integer clientId = IOUtils.readInt("Entrer l'identifiant du client' : ");
        try{
            List <Reservation> listReservations = new ArrayList<>() ;
            listReservations = ReservationDao.getInstance().findResaByClientId(clientId);
            for (Reservation reservation : listReservations){
                System.out.println(reservation.toString());
            }
            if (listReservations.size()==0) System.out.println("Aucune reservation enregistrée dans la base de données");
        } catch (Exception e) {
            throw new UiException("Erreur lors de la récupération de la liste des reservations.", e);
        }
    }

    public  static void findReservationsByVehicleId() throws UiException {
        Integer vehicleId = IOUtils.readInt("Entrer l'identifiant du véhicule' : ");
        try{
            List <Reservation> listReservations = new ArrayList<>() ;
            listReservations = ReservationDao.getInstance().findResaByVehicleId(vehicleId);
            for (Reservation reservation : listReservations){
                System.out.println(reservation.toString());
            }
            if (listReservations.size()==0) System.out.println("Aucune reservation enregistrée dans la base de données");
        } catch (Exception e) {
            throw new UiException("Erreur lors de la récupération de la liste des reservations.", e);
        }
    }

    public  static void deleteReservation() throws UiException {
        try{
            Reservation reservation = new Reservation() ;
            reservation = ReservationService.findReservationById(1);
            ReservationService.delete(reservation);
        } catch (Exception e) {
            throw new UiException("Erreur lors de la récupération de la liste des reservations.", e);
        }
    }

    public  static void findReservationById() throws UiException {
        try{
            Reservation reservation = new Reservation() ;
            reservation = ReservationService.findReservationById(1);
            System.out.println(reservation.toString());
        } catch (Exception e) {
            throw new UiException("Erreur lors de la récupération de la liste des reservations.", e);
        }
    }
};
