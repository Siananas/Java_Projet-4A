package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	private ClientDao clientDao;
	//public static ClientService instance;

	private ClientService(ClientDao clientDao){
		this.clientDao = clientDao;
	}

//	public static ClientService getInstance() {
//		if (instance == null) {
//			instance = new ClientService();
//		}
//		return instance;
//	}
	
	
	public long create(Client client) throws ServiceException {
		try {
			if (!client.getNom().isEmpty() && !client.getPrenom().isEmpty()) {
				client.setNom(client.getNom().toUpperCase());
				return clientDao.create(client);
			}
		} catch(Exception e) {
			throw new ServiceException() ;
		}
		return 0;
	}

	public Client findById(int id) throws ServiceException {
		try{
			return clientDao.findById(id);
		} catch (Exception e){
			throw new ServiceException();
		}
	}

	public List<Client> findAll() throws ServiceException {
		try{
			return clientDao.findAll();
		} catch (Exception e){
			throw new ServiceException();
		}
	}

	public long delete(Client client) throws ServiceException {
		try {
			clientDao.delete(client) ;
		} catch(Exception e) {
			throw new ServiceException() ;
		}
		return 0;
	}
	public int count() throws ServiceException {
		try {
			return clientDao.countClients();
		} catch (DaoException e) {
			throw new ServiceException("Échec de la récupération du nombre de véhicules", e);
		}
	}

	public List<Vehicle> findAllVehiclesOfClientReservations(Client client) throws ServiceException {
		try {
			return clientDao.findAllVehicleOfThisClientReservation(client);
		} catch (DaoException e) {
			throw new ServiceException("Échec de la récupération des véhicules réservés par le client " + client.getId(), e);
		}
	}
}
