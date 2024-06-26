package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import org.springframework.stereotype.Service;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class ReservationService {
    private ReservationDao reservationDao;

    private ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public void create(Reservation reservation) throws ServiceException {
        try {
            List<Reservation> listeReservation = reservationDao.findResaByVehicleId(reservation.getVehicule_id());
            if(reservation.verifDisponibiliteVehicle(reservation, listeReservation)){
                    throw new ServiceException("La voiture n'est pas disponible pour cette période");

            }
            if (reservation.verifDureeReservation(reservation.getDebut(), reservation.getFin())){
                throw new ServiceException("La voiture ne peut pas être réservée plus de 7 jours");
            }

            if (reservation.calculDureeLocationVoitureDepasse30Jours(listeReservation)){
                throw new ServiceException("La réservation d'une voiture ne peux pas dépasser 30 jours");
            }
            reservationDao.create(reservation);

        } catch (DaoException e) {
            throw new ServiceException("Échec de la création de  la reservation : " + reservation.toString(), e);
        }
    }

    public Reservation findReservationById(int reservationId) throws ServiceException {
        try {
            return reservationDao.findReservationById(reservationId);
        } catch (Exception e) {
            throw new ServiceException("Échec de la recherche des reservations avec l'ID client : " + reservationId, e);
        }
    }

    public List<Reservation> findByClientId(int clientId) throws ServiceException {
        try {
            List<Reservation> listeReservation = reservationDao.findResaByClientId(clientId);
            return listeReservation ;
        } catch (Exception e) {
            throw new ServiceException("Échec de la recherche des reservations avec l'ID client : " + clientId, e);
        }
    }

    public List<Reservation> findByVehicleId(int vehicleId) throws ServiceException {
        try {
            List<Reservation> listeReservation  = reservationDao.findResaByVehicleId(vehicleId);
            return listeReservation ;
        } catch (Exception e) {
            throw new ServiceException("Échec de la recherche des reservations avec l'ID véhicule: " + vehicleId, e);
        }
    }

    public List<Reservation> findAll() throws ServiceException {
        try {
            List<Reservation> listeReservation = reservationDao.findAll();
            return listeReservation ;
        } catch (Exception e) {
            throw new ServiceException("Échec de la récupération des reservations", e);
        }
    }

    public boolean delete(int reservationId) throws ServiceException {
        try {
            Reservation reservation = reservationDao.findReservationById(reservationId);

            int affectedRows = reservationDao.delete(reservation);
            return affectedRows > 0;

        } catch (Exception e) {
            throw new ServiceException("Échec de la suppression", e);
        }
    }

    public int count() throws ServiceException {
        try {
            return reservationDao.countReservations();
        } catch (DaoException e) {
            throw new ServiceException("Échec de la récupération du nombre de véhicules", e);
        }
    }

    public boolean updateReservation(Reservation reservation) throws ServiceException {
        try {
            List<Reservation> listeReservation = reservationDao.findResaByVehicleId(reservation.getVehicule_id());
            listeReservation.removeIf(r -> r.getId() == reservation.getId());

            if(reservation.verifDisponibiliteVehicle(reservation, listeReservation)) {
                throw new ServiceException("La voiture n'est pas disponible pour cette période");
            }

            if (reservation.verifDureeReservation(reservation.getDebut(), reservation.getFin())) {
                throw new ServiceException("La voiture ne peut pas être réservée plus de 7 jours");
            }

            if (reservation.calculDureeLocationVoitureDepasse30Jours(listeReservation)) {
                throw new ServiceException("La réservation d'une voiture ne peux pas dépasser 30 jours");
            }

            return reservationDao.updateReservation(reservation);
        } catch (DaoException e) {
            throw new ServiceException("Échec de la mise à jour de la réservation avec l'id " + reservation.getId(), e);
        }
    }

}
