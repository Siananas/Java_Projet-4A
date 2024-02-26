package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;

public class ReservationService {

    private ReservationDao reservationDao;
    public static ReservationService instance;

    private ReservationService() {
        this.reservationDao = ReservationDao.getInstance();
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }

    public static void create(Reservation reservation) throws ServiceException {
        try {
            ReservationDao.getInstance().create(reservation);
        } catch (DaoException e) {
            throw new ServiceException("Échec de la création de  la reservation : " + reservation.toString(), e);
        }
    }

    public static Reservation findReservationById(int reservationId) throws ServiceException {
        try {
            return ReservationDao.getInstance().findReservationById(reservationId);
        } catch (Exception e) {
            throw new ServiceException("Échec de la recherche des reservations avec l'ID client : " + reservationId, e);
        }
    }

    public static List<Reservation> findByClientId(int clientId) throws ServiceException {
        try {
            List<Reservation> listeReservation = new ArrayList<>();
            listeReservation = ReservationDao.getInstance().findResaByClientId(clientId);
            return listeReservation ;
        } catch (Exception e) {
            throw new ServiceException("Échec de la recherche des reservations avec l'ID client : " + clientId, e);
        }
    }

    public static List<Reservation> findByVehicleId(int vehicleId) throws ServiceException {
        try {
            List<Reservation> listeReservation = new ArrayList<>();
            listeReservation = ReservationDao.getInstance().findResaByVehicleId(vehicleId);
            return listeReservation ;
        } catch (Exception e) {
            throw new ServiceException("Échec de la recherche des reservations avec l'ID véhicule: " + vehicleId, e);
        }
    }

    public static List<Reservation> findAll() throws ServiceException {
        try {
            List<Reservation> listeReservation = new ArrayList<>();
            listeReservation = ReservationDao.getInstance().findAll();
            return listeReservation ;
        } catch (Exception e) {
            throw new ServiceException("Échec de la récupération des reservations", e);
        }
    }

    public static void delete(Reservation reservation) throws ServiceException {
        try {
            ReservationDao.getInstance().delete(reservation);
        } catch (Exception e) {
            throw new ServiceException("Échec de la suppression", e);
        }
    }

}
