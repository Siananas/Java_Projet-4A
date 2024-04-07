package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDao {
	
	private static VehicleDao instance = null;
	private VehicleDao() {}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_RESERVATIONS_FOR_VEHICLE_QUERY = "DELETE FROM Reservation WHERE vehicle_id = ?;";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
	private static final String COUNT_VEHICLES_QUERY = "SELECT COUNT(*) FROM Vehicle;" ;
	private static final String FIND_CLIENTS_QUERY = "SELECT c.id, c.nom, c.prenom, c.email, c.naissance FROM Client c JOIN Reservation r ON c.id = r.client_id JOIN Vehicle v ON r.vehicle_id = v.id WHERE v.id = ?;" ;
	private static final String UPDATE_VEHICLE_QUERY = "UPDATE Vehicle SET constructeur = ?, modele = ?, nb_places = ? WHERE id = ?;";


	public long create(Vehicle vehicle) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(CREATE_VEHICLE_QUERY);

			ps.setString(1, vehicle.getConstructeur());
			ps.setString(2, vehicle.getModele());
			ps.setInt(3, vehicle.getNb_places());

			ps.execute();

			ResultSet resultSet = ps.getGeneratedKeys();

			if (resultSet.next()) {
				int id = resultSet.getInt(1) ;
				vehicle.setId(id);
				return id ;
			}

			ps.close();
			connection.close();

		} catch (SQLException e) {
			throw new DaoException("Échec de la création du véhicule : " + vehicle.toString(), e);
		}
		return -1 ;
	}

	public int delete(Vehicle vehicle) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps_reservation = connection.prepareStatement(DELETE_RESERVATIONS_FOR_VEHICLE_QUERY);
			ps_reservation.setInt(1,vehicle.getId());

			ps_reservation.executeUpdate();

			PreparedStatement ps_client = connection.prepareStatement(DELETE_VEHICLE_QUERY);
			ps_client.setInt(1,vehicle.getId());
			int affectedRows = ps_client.executeUpdate();

			ps_reservation.close();
			ps_client.close();
			connection.close();
			return affectedRows;

		} catch (SQLException e) {
			throw new DaoException("Échec de la suppression du véhicule avec l'ID : " + vehicle.getId(), e);
		}
	}

	public Vehicle findById(int id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_VEHICLE_QUERY);

			ps.setInt(1, id);

			ResultSet resultSet = ps.executeQuery();

			if(resultSet.next()){
				String constructeur = resultSet.getString("constructeur");
				String modele = resultSet.getString("modele");
				Integer nb_places = resultSet.getInt("nb_places");
				return new Vehicle(id,constructeur,modele,nb_places);
			}

			ps.close();
			connection.close();

		} catch (SQLException e) {
			throw new DaoException();
		}
		return null;
	}

	public List<Vehicle> findAll() throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_VEHICLES_QUERY);

			ResultSet resultSet = ps.executeQuery();

			List<Vehicle> listeVehicles = new ArrayList<>();
			while(resultSet.next()) {
				Integer id = resultSet.getInt("id");
				String constructeur = resultSet.getString("constructeur");
				String modele = resultSet.getString("modele");
				Integer nb_places = resultSet.getInt("nb_places");
				listeVehicles.add(new Vehicle(id,constructeur,modele,nb_places));
			}

			ps.close();
			connection.close();

			return listeVehicles ;

		} catch (SQLException e) {
			throw new DaoException("Échec de la récupération de la liste des véhicules", e);
		}
	}
	public int countVehicles() throws DaoException {
		int count = 0;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(COUNT_VEHICLES_QUERY);

			ResultSet resultSet = ps.executeQuery();

			if (resultSet.next()) {
				count = resultSet.getInt(1);
			}

			ps.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException("Échec de la récupération du nombre de véhicules", e);
		}
		return count;
	}

	public List<Client> findAllClientsOfThisVehicleReservation(Vehicle vehicle) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_CLIENTS_QUERY);
			ps.setInt(1, vehicle.getId());
			ResultSet resultSet = ps.executeQuery();
			List<Client> listeClients = new ArrayList<>();

			while(resultSet.next()) {
				Integer id = resultSet.getInt("id");
				String nom = resultSet.getString("nom");
				String prenom = resultSet.getString("prenom");
				String email = resultSet.getString("email");
				LocalDate naissance = resultSet.getDate("naissance").toLocalDate(); // Assurez-vous que la conversion de java.sql.Date à LocalDate est correcte
				listeClients.add(new Client(id, nom, prenom, email, naissance));
			}

			ps.close();
			connection.close();
			return listeClients;
		} catch (Exception e) {
			throw new DaoException("Échec de la récupération des clients ayant réservé le véhicule " + vehicle.getId(), e);
		}
	}

	public boolean updateVehicle(Vehicle vehicle) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(UPDATE_VEHICLE_QUERY)) {

			ps.setString(1, vehicle.getConstructeur());
			ps.setString(2, vehicle.getModele());
			ps.setInt(3, vehicle.getNb_places());
			ps.setInt(4, vehicle.getId());

			int updatedRows = ps.executeUpdate();

			ps.close();
			connection.close();

			return updatedRows > 0;
		} catch (SQLException e) {
			throw new DaoException("Échec de la mise à jour du véhicule " + vehicle.getId(), e);
		}
	}
}
