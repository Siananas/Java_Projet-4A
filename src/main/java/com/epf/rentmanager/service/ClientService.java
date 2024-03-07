package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;

public class ClientService {

	private ClientDao clientDao;
	public static ClientService instance;
	
	private ClientService() {
		this.clientDao = ClientDao.getInstance();
	}
	
	public static ClientService getInstance() {
		if (instance == null) {
			instance = new ClientService();
		}
		return instance;
	}
	
	
	public long create(Client client) throws ServiceException {
		try {
			if (!client.getNom().isEmpty() && !client.getPrenom().isEmpty()) {
				client.setNom(client.getNom().toUpperCase());
				return ClientDao.getInstance().create(client);
			}
		} catch(Exception e) {
			throw new ServiceException() ;
		}
		return 0;
	}

	public Client findById(int id) throws ServiceException {
		try{
			return ClientDao.getInstance().findById(id);
		} catch (Exception e){
			throw new ServiceException();
		}
	}

	public List<Client> findAll() throws ServiceException {
		try{
			return ClientDao.getInstance().findAll();
		} catch (Exception e){
			throw new ServiceException();
		}
	}

	public long delete(Client client) throws ServiceException {
		try {
			ClientDao.getInstance().delete(client) ;
		} catch(Exception e) {
			throw new ServiceException() ;
		}
		return 0;
	}
	public int count() throws ServiceException {
		try {
			return ClientDao.getInstance().countClients();
		} catch (DaoException e) {
			throw new ServiceException("Échec de la récupération du nombre de véhicules", e);
		}
	}
}
