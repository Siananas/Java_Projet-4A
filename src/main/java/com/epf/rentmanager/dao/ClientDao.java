package com.epf.rentmanager.dao;

import java.time.LocalDate ;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ClientDao {

	private ClientDao() {}
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATIONS_FOR_CLIENT_QUERY = "DELETE FROM Reservation WHERE client_id = ?;";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String COUNT_CLIENTS_QUERY = "SELECT COUNT(*) FROM Client;" ;
	private static final String FIND_VEHICLE_QUERY = "SELECT v.id, v.constructeur, v.modele, v.nb_places FROM Vehicle v JOIN Reservation r ON v.id = r.vehicle_id JOIN Client c ON r.client_id = c.id WHERE c.id = ?;" ;
	private static final String UPDATE_CLIENT_QUERY = "UPDATE Client SET nom = ?, prenom = ?, email = ?, naissance = ? WHERE id = ?;" ;

	public long create(Client client) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(CREATE_CLIENT_QUERY);

			ps.setString(1, client.getNom());
			ps.setString(2, client.getPrenom());
			ps.setString(3, client.getEmail());
			ps.setDate(4, Date.valueOf(client.getNaissance()));

			ps.execute();

			ResultSet resultSet = ps.getGeneratedKeys();

			if (resultSet.next()) {
				int id = resultSet.getInt(1) ;
				client.setId(id);
				return id ;
			}

			ps.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException("Échec de la création du client : " + client.toString(), e);
		}
        return -1 ;
    }

	public Client findById(int id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(FIND_CLIENT_QUERY);

			ps.setInt(1, id);

			//ps.execute();

			ResultSet resultSet = ps.executeQuery();

			if(resultSet.next()){
				String nom = resultSet.getString("nom");
				String prenom = resultSet.getString("prenom");
				String email = resultSet.getString("email");
				LocalDate naissance = resultSet.getDate("naissance").toLocalDate();
				return new Client(id, nom, prenom, email, naissance);
			}

			ps.close();
			connection.close();

		} catch (SQLException e) {
			throw new DaoException("Échec de la recherche du client avec l'ID : " + id, e);
		}
		return null;
	}
	
	public int delete(Client client) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps_reservation = connection.prepareStatement(DELETE_RESERVATIONS_FOR_CLIENT_QUERY);
			ps_reservation.setInt(1,client.getId());
			ps_reservation.executeUpdate();

			PreparedStatement ps_client = connection.prepareStatement(DELETE_CLIENT_QUERY);
			ps_client.setInt(1,client.getId());
			int affectedRows = ps_client.executeUpdate();
			return affectedRows;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Échec de la suppression du client avec l'ID : " + client.getId(), e);
		}
	}

	public List<Client> findAll() throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_CLIENTS_QUERY);

			ResultSet resultSet = ps.executeQuery();

			List<Client> listeClients = new ArrayList<>();
			while(resultSet.next()) {
				Integer id = resultSet.getInt("id");
				String nom = resultSet.getString("nom");
				String prenom = resultSet.getString("prenom");
				String email = resultSet.getString("email");
				LocalDate naissance = resultSet.getDate("naissance").toLocalDate();
				listeClients.add(new Client(id, nom, prenom, email, naissance)) ;
			}
			return listeClients ;

		} catch (SQLException e) {
			throw new DaoException("Échec de la récupération de la liste des clients", e);
		}
	}

	public int countClients() throws DaoException {
		int count = 0;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(COUNT_CLIENTS_QUERY);

			ResultSet resultSet = ps.executeQuery();

			if (resultSet.next()) {
				count = resultSet.getInt(1);
			}

			resultSet.close();
			ps.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException("Échec de la récupération du nombre de clients", e);
		}
		return count;
	}

	public List<Vehicle> findAllVehicleOfThisClientReservation(Client client) throws DaoException {

		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_VEHICLE_QUERY);
			ps.setInt(1, client.getId());
			ResultSet resultSet = ps.executeQuery();
			List<Vehicle> listeVehicles = new ArrayList<>();

			while(resultSet.next()) {
				Integer id = resultSet.getInt("id");
				String constructeur = resultSet.getString("constructeur");
				String modele = resultSet.getString("modele");
				Integer nb_places = resultSet.getInt("nb_places");
				listeVehicles.add(new Vehicle(id,constructeur,modele,nb_places));
			}
			return listeVehicles ;

		} catch (Exception e){
			throw new DaoException("Échec de la récupération des véhicules réservés par le client " + client.getId(), e);
		}
	}

	public boolean updateClient(Client client) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(UPDATE_CLIENT_QUERY)) {

			ps.setString(1, client.getNom());
			ps.setString(2, client.getPrenom());
			ps.setString(3, client.getEmail());
			ps.setDate(4, Date.valueOf(client.getNaissance()));
			ps.setInt(5, client.getId());

			int updatedRows = ps.executeUpdate();
			return updatedRows > 0;
		} catch (SQLException e) {
			throw new DaoException("Échec de la mise à jour du client " + client.getId(), e);
		}
	}
}
