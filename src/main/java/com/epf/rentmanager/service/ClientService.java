package com.epf.rentmanager.service;

import java.util.List;
import java.util.Objects;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	private ClientDao clientDao;

	public ClientService(ClientDao clientDao) {
		this.clientDao = clientDao;
	}

	public long create(Client client) throws ServiceException {
		if (client.getNom().length()<3) {
			throw new ServiceException("Le nom du client ne peut pas être vide et doit compter au minimum 3 caractères.");
		}
		if (client.getPrenom().length()<3) {
			throw new ServiceException("Le prénom du client ne peut pas être vide et doit compter au minimum 3 caractères.");
		}
		if (!client.verifAge(client.getNaissance())) {
			throw new ServiceException("L'âge du client est invalide.");
		}
		client.setNom(client.getNom().toUpperCase());
		try {
			List <Client> listeClients = clientDao.findAll();
			for (Client item : clientDao.findAll()){
				if (Objects.equals(item.getEmail(), client.getEmail())){
					throw new ServiceException("L'adresse email est déjà utilisée");
				}
			}
			return clientDao.create(client);
		} catch (DaoException e) {
			throw new ServiceException("Échec de la création du client dans la base de données.", e);
		} catch (Exception e) {
			throw new ServiceException("Une erreur inattendue s'est produite lors de la création du client.", e);
		}
	}


	public Client findById(int id) throws ServiceException {
		try {
			return clientDao.findById(id);
		} catch (Exception e) {
			throw new ServiceException("Échec de la recherche du client avec l'id " + id, e);
		}
	}

	public List<Client> findAll() throws ServiceException {
		try {
			return clientDao.findAll();
		} catch (Exception e) {
			throw new ServiceException("Échec de la récupération de la liste des clients.", e);
		}
	}

	public void delete(int id) throws ServiceException {
		try {
			Client client = clientDao.findById(id);
			clientDao.delete(client);
		} catch (Exception e) {
			throw new ServiceException("Échec de la suppression du client avec l'id " + id, e);
		}
	}

	public int count() throws ServiceException {
		try {
			return clientDao.countClients();
		} catch (DaoException e) {
			throw new ServiceException("Échec de la récupération du nombre de clients.", e);
		}
	}

	public List<Vehicle> findAllVehiclesOfClientReservations(Client client) throws ServiceException {
		try {
			return clientDao.findAllVehicleOfThisClientReservation(client);
		} catch (DaoException e) {
			throw new ServiceException("Échec de la récupération des véhicules réservés par le client " + client.getId(), e);
		}
	}

	public boolean updatelient(Client client) throws ServiceException {
		try {
			return clientDao.updateClient(client);
		} catch (DaoException e) {
			throw new ServiceException("Échec de la mise à jour du client avec l'id " + client.getId(), e);
		}
	}

	public boolean updateClient(Client client) throws ServiceException {
		if (client.getNom().length() < 3) {
			throw new ServiceException("Le nom du client ne peut pas être vide et doit compter au minimum 3 caractères.");
		}
		if (client.getPrenom().length() < 3) {
			throw new ServiceException("Le prénom du client ne peut pas être vide et doit compter au minimum 3 caractères.");
		}
		if (!client.verifAge(client.getNaissance())) {
			throw new ServiceException("L'âge du client est invalide.");
		}
		client.setNom(client.getNom().toUpperCase());
		try {
			List<Client> listeClients = clientDao.findAll();
			for (Client item : listeClients) {
				// Vérifiez si l'email existe déjà pour un autre client (avec un ID différent)
				if (Objects.equals(item.getEmail(), client.getEmail()) && item.getId() != client.getId()) {
					throw new ServiceException("L'adresse email est déjà utilisée par un autre client.");
				}
			}
			return clientDao.updateClient(client);
		} catch (DaoException e) {
			throw new ServiceException("Échec de la mise à jour du client dans la base de données.", e);
		} catch (Exception e) {
			throw new ServiceException("Une erreur inattendue s'est produite lors de la mise à jour du client.", e);
		}
	}

}
