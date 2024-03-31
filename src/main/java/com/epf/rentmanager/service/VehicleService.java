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
	//public static VehicleService instance;
	
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

	public long create(Vehicle vehicle) throws ServiceException {
		try {
			if (!vehicle.getConstructeur().isEmpty() && vehicle.getNb_places() > 1) {
				return this.vehicleDao.create(vehicle);
			} else {
				throw new ServiceException("Le constructeur ne peut pas être vide et le nombre de places doit être supérieur à 1.");
			}
		} catch (DaoException e) {
			throw new ServiceException("Échec de la création du véhicule : " + vehicle.toString(), e);
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

	public List<Client> findAllClientsOfVehicleReservation(Vehicle vehicle) throws ServiceException {
		try {
			return vehicleDao.findAllClientsOfThisVehicleReservation(vehicle);
		} catch (DaoException e) {
			throw new ServiceException("Échec de la récupération des clients ayant réservé le véhicule " + vehicle.getId(), e);
		}
	}

}
