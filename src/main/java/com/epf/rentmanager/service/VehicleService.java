package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {
	private VehicleDao vehicleDao;
	private VehicleService(VehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}

	public int count() throws ServiceException {
		try {
			return vehicleDao.countVehicles();
		} catch (DaoException e) {
			throw new ServiceException("Échec de la récupération du nombre de véhicules", e);
		}
	}

	public long create(Vehicle vehicle) {
		if (vehicle.getConstructeur().isEmpty()) {
			throw new IllegalArgumentException("Le constructeur du véhicule ne peut pas être vide.");
		}
		if (vehicle.getModele().isEmpty()) {
			throw new IllegalArgumentException("Le modèle du véhicule ne peut pas être vide.");
		}
		if (vehicle.getNb_places() < 2 || vehicle.getNb_places() > 9) {
			throw new IllegalArgumentException("Le nombre de places doit être entre 2 et 9.");
		}
		try {
			return this.vehicleDao.create(vehicle);
		} catch (DaoException e) {
			throw new IllegalArgumentException("Échec de la création du véhicule.", e);
		}
	}

	public Vehicle findById(int id) throws ServiceException {
		try{
			return vehicleDao.findById(id);
		} catch (DaoException e) {
			throw new ServiceException("Échec de la recherche du véhicule avec l'ID : " + id, e);
		}
	}

	public List<Vehicle> findAll() throws ServiceException {
		try{
			return vehicleDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException("Échec de la récupération de la liste des véhicules.", e);
		}
	}

	public boolean delete(int vehicleId) throws ServiceException {
		try {
			Vehicle vehicle = vehicleDao.findById(vehicleId);
			int affectedRows = vehicleDao.delete(vehicle);
			return affectedRows > 0;

		} catch (DaoException e) {
			throw new ServiceException("Échec de la suppression du véhicule avec l'ID : " + vehicleId, e);
		}
	}

	public List<Client> findAllClientsOfVehicleReservation(Vehicle vehicle) throws ServiceException {
		try {
			return vehicleDao.findAllClientsOfThisVehicleReservation(vehicle);
		} catch (DaoException e) {
			throw new ServiceException("Échec de la récupération des clients ayant réservé le véhicule " + vehicle.getId(), e);
		}
	}

	public boolean updateVehicle(Vehicle vehicle) throws ServiceException {
		try {
			return vehicleDao.updateVehicle(vehicle);
		} catch (DaoException e) {
			throw new ServiceException("Échec de la mise à jour du véhicule avec l'id " + vehicle.getId(), e);
		}
	}
}
